package com.matzipuniv.sinchon.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3UploaderProfile {
    private AmazonS3 s3Client;
    public static final String CLOUD_FRONT_DOMAIN_NAME = "d18omhl2ssqffk.cloudfront.net";

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(String currentFilePath, MultipartFile file) throws IOException {
        int isOk = 1;

        if(file!=null){
            String contentType = file.getContentType();
            if(ObjectUtils.isEmpty(contentType)){
                isOk = 0;
            } else {
                if(contentType.contains("image/jpeg")) {
                    isOk = 1;
                }
                else if(contentType.contains("image/png")) {
                    isOk = 1;
                }
                else{
                    isOk = 0;
                }
            }
        }


        if(isOk == 1) {
            SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
            String fileName = FilenameUtils.getBaseName(file.getOriginalFilename()) + "-" + date.format(new Date()) + "." + FilenameUtils.getExtension(file.getOriginalFilename());

            if ("".equals(currentFilePath) == false && currentFilePath != null) {
                boolean isExistObject = s3Client.doesObjectExist(bucket, currentFilePath);

                if (isExistObject == true) {
                    s3Client.deleteObject(bucket, currentFilePath);
                }
            }

            s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return fileName;
        } else {
            return null;
        }
    }

    public String delete(String currentFilePath) {
        int idx = currentFilePath.lastIndexOf("/");
        String fileName = currentFilePath.substring(idx+1);
        try {
            s3Client.deleteObject(bucket, fileName);
            return "deleted";
        }
        catch(Exception e) {
            return "error occured" + e.getMessage();
        }

    }
}
