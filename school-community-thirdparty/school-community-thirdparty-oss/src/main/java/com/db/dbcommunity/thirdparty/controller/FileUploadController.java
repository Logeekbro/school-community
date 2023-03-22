package com.db.dbcommunity.thirdparty.controller;

import com.db.dbcommunity.common.api.R;
import com.db.dbcommunity.common.model.vo.SingleKeyVO;
import com.db.dbcommunity.common.util.UserContext;
import com.db.dbcommunity.thirdparty.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Resource
    private UploadService uploadService;

    /**
     * 上传文章封面图片
     * @param file 封面图片
     * @return 图片url
     */
    @PostMapping("/mainPic")
    public R<SingleKeyVO> uploadMainPic(MultipartFile file) {
        String url = uploadService.uploadArticleMainPic(file);
        return R.success(new SingleKeyVO(url));
    }

    /**
     * 上传或修改头像
     */
    @PostMapping("/avatar")
    public R<Void> uploadAvatar(MultipartFile file, boolean haveAvatar) {
        return uploadService.uploadAvatar(UserContext.getCurrentUserId(), file, haveAvatar) ? R.success() : R.failed();
    }
}
