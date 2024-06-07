package com.bezkoder.spring.thymeleaf.image.upload.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
    private final Path root = Paths.get("./uploads");

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file, String directory) {
        try {
            Path dir = root.resolve(directory);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            Path targetLocation = dir.resolve(file.getOriginalFilename());

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String filename) {
        try {
            Path file = root.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public void createDirectory(String dirName) {
        try {
            Files.createDirectory(this.root.resolve(dirName));
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException("Directory already exists.");
        } catch (IOException e) {
            throw new RuntimeException("Could not create directory. Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteDirectory(String dirName) {
    	try {
            Path dir = this.root.resolve(dirName);
            FileSystemUtils.deleteRecursively(dir.toFile());
    	}catch (Exception e) {
    		throw new RuntimeException("Could not delete directory. Error: " + e.getMessage());
			// TODO: handle exception
		}
    }

    @Override
    public List<String> loadAllDirectories() {
        try {
            return Files.walk(this.root, 1)
                    .filter(Files::isDirectory)
                    .filter(path -> !path.equals(this.root))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Could not load directories!");
        }
    }

    @Override
    public Stream<Path> loadAllFromDirectory(String dirName) {
        try {
            Path dir = this.root.resolve(dirName);
            return Files.walk(dir, 1).filter(path -> !path.equals(dir)).map(dir::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files from directory. Error: " + e.getMessage());
        }
    }
}
