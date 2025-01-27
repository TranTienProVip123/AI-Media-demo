package com.bezkoder.spring.thymeleaf.image.upload;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.bezkoder.spring.thymeleaf.image.upload.service.FilesStorageService;

@SpringBootApplication
@ComponentScan({"com.bezkoder.spring.thymeleaf.image.upload"})
public class SpringBootUploadImageApplication implements CommandLineRunner {

  @Resource
  FilesStorageService storageService;

  public static void main(String[] args) {
    SpringApplication.run(SpringBootUploadImageApplication.class, args);
  }

  @Override
  public void run(String... arg) throws Exception {
//    storageService.deleteAll();
    storageService.init();
  }
}
