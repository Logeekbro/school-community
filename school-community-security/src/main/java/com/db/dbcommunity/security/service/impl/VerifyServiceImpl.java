package com.db.dbcommunity.security.service.impl;

import cn.hutool.core.lang.UUID;
import com.db.dbcommunity.common.exception.ApiAsserts;
import com.db.dbcommunity.security.mapper.VerifyWordMapper;
import com.db.dbcommunity.security.service.VerifyService;
import com.db.dbcommunity.security.util.RedisCache;
import com.db.dbcommunity.common.util.RandomUtil;
import com.db.dbcommunity.security.model.dto.VerifyDTO;
import com.db.dbcommunity.security.model.vo.VerifyVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.db.dbcommunity.common.constant.RedisNameSpace.*;

@Service
public class VerifyServiceImpl implements VerifyService {

    // 用户需要点击的最大次数
    private static final Integer MAX_CLICK_COUNT = 5;
    // 每次用于验证的总汉字数
    private static final Integer WORD_COUNT = 12;
    // 图片每行显示几条信息
    private static final Integer COLUMN_COUNT = 4;
    // 登录时的验证码有效时间
    public static final Integer LOGIN_VERIFY_CODE_EXPIRE_TIME = 3 * 60;
    // 验证通过后生成的accessToken有效时间
    public static final Integer LOGIN_ACCESS_TOKEN_EXPIRE_TIME= 5 * 60;

    @Resource
    private RedisCache redisCache;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private VerifyWordMapper verifyWordMapper;

    @Override
    public VerifyVO startVerify() {
        // 一次验证中用到的汉字
        Set<String> words = getAllWord();
        if (words == null) ApiAsserts.fail("验证服务异常");
        // 33-126 为某些可见字符的ASCII码
        Queue<Integer> intQueue = RandomUtil.getDistinctRandomAsciiIntQueue(WORD_COUNT);
        // 正确的点击顺序
        List<Character> order = new LinkedList<>();
        // 正确的字符串
        StringBuilder correctValue = new StringBuilder(MAX_CLICK_COUNT);
        // 用于转成图片的字符串
        List<String> rules = new ArrayList<>(WORD_COUNT / COLUMN_COUNT);
        // 用于拼接转成图片的字符串
        StringBuilder ruleBuilder = new StringBuilder();
        // 计数器
        int count = 1;
        // 打乱后的words集合
        Set<String> userViewWords = new HashSet<>(WORD_COUNT);
        for (String word :  words) {
            userViewWords.add(word);
            int charInt = intQueue.poll();
            Character randomChar = (char) charInt;
            // 通过随机条件生成用户需要点击的字符，并限制最大点击个数
            if (count <= MAX_CLICK_COUNT && (charInt & 1) == 1) {
                // randomChar为等式左边的值
                order.add(randomChar);
                // word 为等式右边的值
                correctValue.append(word);
            }
            // 将等式进行拼接
            ruleBuilder.append(randomChar).append(" = ").append(word).append("  ");
            // 每行达到指定个数的等式后开始拼接新字符串
            if (count++ % COLUMN_COUNT == 0) {
                rules.add(ruleBuilder.toString());
                ruleBuilder.delete(0, ruleBuilder.length() - 1);
            }
        }
        // 有可能会出现生成的点击字符为空的情况，此时需要重新生成
        if(order.size() == 0) {
            return startVerify();
        }
        VerifyVO verifyVO = new VerifyVO(UUID.fastUUID().toString(true), userViewWords, order);
        // 存入Redis中的对象，用于验证
        VerifyDTO verifyDTO = new VerifyDTO(correctValue.toString(), rules.toArray(new String[0]));
        setLoginVerifyCode(verifyVO.getVerifyId(), verifyDTO);
        return verifyVO;
    }

    private void setLoginVerifyCode(String verifyId, VerifyDTO verifyDTO) {
        redisCache.setCacheObject(LOGIN_VERIFY_CODE_PREFIX + verifyId, verifyDTO, LOGIN_VERIFY_CODE_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    @Override
    public VerifyDTO getLoginVerifyCode(String verifyId) {
        return redisCache.getCacheObject(LOGIN_VERIFY_CODE_PREFIX + verifyId);
    }

    private Set<String> getAllWord() {
        return redisTemplate.opsForSet().distinctRandomMembers(VERIFY_WORD_SET, WORD_COUNT);
    }

    @Override
    public void loadVerifyWordFromDB() {
        Set<String> wordSet = verifyWordMapper.getWordSet();
        redisTemplate.opsForSet().add(VERIFY_WORD_SET, wordSet.toArray(new String[0]));
    }

    @Override
    public String handleVerify(String verifyId, String value) {
        VerifyDTO verifyDTO = getLoginVerifyCode(verifyId);
        if (verifyDTO == null) ApiAsserts.fail("会话不存在或已失效");
        else if (verifyDTO.getCorrectValue().equals(value)) {
            deleteLoginVerifyCode(verifyId);
            String accessToken = UUID.fastUUID().toString(true);
            setLoginAccessToken(accessToken, accessToken);
            return accessToken;
        } else {
            deleteLoginVerifyCode(verifyId);
            ApiAsserts.fail("验证失败");
        }
        return null;
    }

    private void deleteLoginVerifyCode(String verifyId) {
        redisCache.deleteObject(LOGIN_VERIFY_CODE_PREFIX + verifyId);
    }

    private void setLoginAccessToken(String username, String value) {
        redisCache.setCacheObject(LOGIN_ACCESS_TOKEN_PREFIX + username, value, LOGIN_ACCESS_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }
}
