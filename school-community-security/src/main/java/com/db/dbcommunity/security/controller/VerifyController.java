package com.db.dbcommunity.security.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.constant.SecurityConstant;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.security.model.dto.VerifyDTO;
import com.db.dbcommunity.security.service.VerifyService;
import com.db.dbcommunity.common.util.ImageUtil;
import com.db.dbcommunity.security.model.vo.VerifyVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping(SecurityConstant.URI_PREFIX + "/verify")
public class VerifyController {

    @Resource
    private VerifyService verifyService;

    /**
     * 开始验证
     * @return 本次验证的id
     */
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public R<VerifyVO> startVerify() {
        VerifyVO verifyVO = verifyService.startVerify();
        return R.success(verifyVO);
    }

    /**
     * 获取验证码图片
     * @param verifyId 验证的id
     * @param response 响应
     */
    @RequestMapping(value = "/img/{verifyId}", method = RequestMethod.GET)
    public void getRuleImg(@PathVariable String verifyId, HttpServletResponse response) throws IOException {
        VerifyDTO verifyDTO = verifyService.getLoginVerifyCode(verifyId);
        if (verifyDTO == null) return;
        //设置编码格式
        response.setCharacterEncoding("UTF-8");
        // 设置不缓存图片
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        // 指定生成的响应图片,一定不能缺少这句话,否则错误.
        response.setContentType("image/jpeg");
        BufferedImage bufferedImage = ImageUtil.strToImage(verifyDTO.getStrArr());
        ImageIO.write(bufferedImage, "png", response.getOutputStream());
    }


    /**
     * 验证是否正确
     * @param verifyId 验证的id
     * @param value 用于验证的验证码
     * @return 验证成功返回accessToken  验证失败返回null
     */
    @RequestMapping(value = "/verify/{verifyId}", method = RequestMethod.GET)
    public R<SingleKeyVO> handleVerify(@PathVariable String verifyId,
                                       @RequestParam String value) {
        String accessToken = verifyService.handleVerify(verifyId, value);
        SingleKeyVO vo = new SingleKeyVO(accessToken);
        return R.success(vo);
    }

}