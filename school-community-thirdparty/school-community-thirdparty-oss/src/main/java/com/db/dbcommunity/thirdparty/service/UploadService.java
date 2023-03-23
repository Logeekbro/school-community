package com.db.dbcommunity.thirdparty.service;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.UUID;
import com.db.dbcommunity.common.constant.GlobalConstant;
import com.db.dbcommunity.common.constant.ThirdpartyConstant;
import com.db.dbcommunity.common.exception.ApiAsserts;
import com.db.dbcommunity.common.util.ImageUtil;
import com.db.dbcommunity.thirdparty.feign.UserFeignClient;
import com.db.dbcommunity.thirdparty.vo.ArticleImgVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UploadService {

    @Resource
    private OssService ossService;

    @Resource
    private UserFeignClient userFeignClient;

    public String uploadArticleMainPic(MultipartFile file) {
        String fileType = ImageUtil.getImgType(file);
        String objectName =
                ThirdpartyConstant.ARTICLE_MAIN_PIC_PREFIX + UUID.fastUUID().toString(true) + "." + fileType;
        if(ossService.uploadObject(objectName, file)) {
            return ThirdpartyConstant.OSS_FILE_PREFIX + objectName;
        }
        return null;
    }

    public boolean uploadAvatar(Long userId, MultipartFile file, boolean haveAvatar) {
        try {
            String type = FileTypeUtil.getType(file.getInputStream());
            if(!GlobalConstant.ACCEPT_IMG_TYPE.contains(type)){
                ApiAsserts.fail("只能上传jpg/png格式的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ApiAsserts.fail("上传失败");
        }
        String objectName = ThirdpartyConstant.AVATAR_URL_PREFIX + userId + ".jpg";
        ossService.uploadObject(objectName, file);
        if(!haveAvatar){
            String avatar = ThirdpartyConstant.OSS_FILE_PREFIX + objectName;
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            map.put("avatar", avatar);
            return userFeignClient.updateUserDetailInfo(map).getCode().equals(200);
        }
        return true;
    }

    public ArticleImgVO uploadArticleImg(MultipartFile[] files) {
        ArticleImgVO data = new ArticleImgVO();
        Map<String, String> succMap = new HashMap<>();
        List<String> errFiles = new ArrayList<>();
        for(MultipartFile file : files){
            try {
                String type = ImageUtil.getImgType(file);
                String objectName = ThirdpartyConstant.ARTICLE_IMG_URL_PREFIX + UUID.fastUUID().toString(true) + "." + type;
                if(ossService.uploadObject(objectName, file)){
                    String url = ThirdpartyConstant.OSS_FILE_PREFIX + objectName;
                    succMap.put(file.getOriginalFilename(), url);
                }
                else{
                    errFiles.add(file.getOriginalFilename());
                }
            } catch (Exception e) {
                e.printStackTrace();
                errFiles.add(file.getOriginalFilename());
            }

        }
        data.setErrFiles(errFiles);
        data.setSuccMap(succMap);
        return data;
    }
}
