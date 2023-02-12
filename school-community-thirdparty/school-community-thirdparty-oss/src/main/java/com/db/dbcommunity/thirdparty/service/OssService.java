package com.db.dbcommunity.thirdparty.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.db.dbcommunity.common.constant.ThirdpartyConstant;
import com.db.dbcommunity.common.exception.ApiAsserts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

import static com.db.dbcommunity.common.constant.ThirdpartyConstant.BUCKET_NAME;

@Service
public class OssService {

    private OSS client;
    @Value("${oss.ak}")
    private String ALIYUN_AK;
    @Value("${oss.as}")
    private String ALIYUN_AS;

    private OSS getClient(){
        try {
            if(client == null) {
                client = new OSSClientBuilder().build(ThirdpartyConstant.OSS_ENDPOINT, ALIYUN_AK, ALIYUN_AS);
            }
            return client;
        }
        catch (Exception e){
            e.printStackTrace();
            ApiAsserts.fail("文件服务异常");
            return null;
        }
    }

    public boolean uploadObject(String objectName, MultipartFile file) {
        try {
            OSS client = getClient();
            assert client != null;
            client.putObject(BUCKET_NAME, objectName, new ByteArrayInputStream(file.getBytes()));
            return true;
        }
        catch (Exception e) {
            ApiAsserts.fail("文件服务异常");
            return false;
        }
    }
}
