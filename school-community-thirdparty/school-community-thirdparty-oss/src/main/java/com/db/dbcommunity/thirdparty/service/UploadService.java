package com.db.dbcommunity.thirdparty.service;

import cn.hutool.core.lang.UUID;
import com.db.dbcommunity.common.constant.ThirdpartyConstant;
import com.db.dbcommunity.common.util.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class UploadService {

    @Resource
    private OssService ossService;

    public String uploadArticleMainPic(MultipartFile file) {
        String fileType = ImageUtil.getImgType(file);
        String objectName =
                ThirdpartyConstant.ARTICLE_MAIN_PIC_PREFIX + UUID.fastUUID().toString(true) + "." + fileType;
        if(ossService.uploadObject(objectName, file)) {
            return ThirdpartyConstant.OSS_FILE_PREFIX + objectName;
        }
        return null;
    }
}
