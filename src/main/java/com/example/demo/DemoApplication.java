package com.example.demo;

import java.nio.charset.StandardCharsets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

@SpringBootApplication
@RestController
public class DemoApplication {

    private static final MediaType MEDIA_TYPE_CSV = new MediaType("text", "csv");
    private static final String CONTENT_DISPOSITION_FORMAT = "attachment; filename*=UTF-8''%s";

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("contentDisposition")
    ResponseEntity<?> contentDisposition() {
        System.out.println("csv request");
        return createCsvDownloadResponse("foo.csv", "contentDisposition!!!");
    }

    @GetMapping("plain")
    String plain() {
        System.out.println("plain request");
        return "plain!!!!!!!!";
    }

    @GetMapping("err")
    ResponseEntity<String> error() {
        System.out.println("error request");
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("errror!!!");
    }


    public static ResponseEntity<?> createCsvDownloadResponse(String filename, String body) {
        HttpHeaders headers = new HttpHeaders();
        addContentDisposition(headers, filename);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MEDIA_TYPE_CSV)
            .headers(headers)
            .body(body);
    }

    private static void addContentDisposition(
        HttpHeaders headers, String fileName) {
        String headerValue = String.format(CONTENT_DISPOSITION_FORMAT,
            UriUtils.encode(fileName, StandardCharsets.UTF_8.name()));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);
    }
}
