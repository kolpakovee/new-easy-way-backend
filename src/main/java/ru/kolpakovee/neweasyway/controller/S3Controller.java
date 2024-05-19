package ru.kolpakovee.neweasyway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kolpakovee.neweasyway.service.S3Service;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3ObjectService;

    @GetMapping("/buckets")
    public void getListOfBuckets() {
        s3ObjectService.getListBuckets();
    }

    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("fileName") String fileName,
                             @RequestParam("file") MultipartFile file) throws IOException {
        return s3ObjectService.uploadFile(fileName, file);
    }
}
