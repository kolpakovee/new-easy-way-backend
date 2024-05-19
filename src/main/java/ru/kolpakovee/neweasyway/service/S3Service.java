package ru.kolpakovee.neweasyway.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;

    public List<Bucket> getListBuckets() {
        return s3Client.listBuckets();
    }

    public String uploadFile(String keyName, MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());

        String bucketName = getListBuckets().getFirst().getName();

        // Устанавливаем права доступа на чтение для всех пользователей
        PutObjectRequest putRequest = new PutObjectRequest(bucketName,
                keyName,
                file.getInputStream(),
                metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        s3Client.putObject(putRequest);

        return "https://" + bucketName + ".hb.ru-msk.vkcs.cloud/" + keyName;
    }
}
