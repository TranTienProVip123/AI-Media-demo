package com.bezkoder.spring.thymeleaf.image.upload.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface FilesStorageService {
    void init();
    void save(MultipartFile file, String directory);
    Resource load(String filename);
    boolean delete(String filename);
    void deleteAll();
    Stream<Path> loadAll();
    void createDirectory(String dirName);
    void deleteDirectory(String dirName);
    Stream<Path> loadAllFromDirectory(String directory);
    List<String> loadAllDirectories();
}
