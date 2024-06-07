package com.bezkoder.spring.thymeleaf.image.upload.controller;

import java.awt.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.relation.Role;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bezkoder.spring.thymeleaf.image.upload.model.ImageInfo;
import com.bezkoder.spring.thymeleaf.image.upload.service.FilesStorageService;

@Controller
public class ImageController {

@Autowired
FilesStorageService storageService;

@GetMapping("/")
public String homepage() {
 return "redirect:/images";
}

@GetMapping("/images/new")
public String newFile(Model model) {
 List<String> directories = storageService.loadAllDirectories();
 model.addAttribute("directories", directories);
 return "upload_form";
}

@PostMapping("/images/upload")
public String uploadFile(Model model, @RequestParam("file") MultipartFile file, @RequestParam("directory") String directory) {
 String message = "";

 try {
   storageService.save(file, directory);

   message = "Uploaded the file successfully: " + file.getOriginalFilename();
   model.addAttribute("message", message);
 } catch (Exception e) {
   message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
   model.addAttribute("message", message);
 }

 return "upload_form";
}

@GetMapping("/images/login")
public String login() {
   return "images"; // Trả về trang đăng nhập
}

@GetMapping("/images")
public String getListFiles(Model model) {
    List<ImageInfo> fileInfos = storageService.loadAll().map(path -> {
        String filename = path.getFileName().toString();
        String url = MvcUriComponentsBuilder
            .fromMethodName(ImageController.class, "getFile", path.getFileName().toString()).build().toString();
        String type = getFileType(path);
        return new ImageInfo(filename, url, type);
    }).collect(Collectors.toList());

    model.addAttribute("files", fileInfos);
    model.addAttribute("showDeleteButton", true);

    return "images";
}


@GetMapping("/images/{filename:.+}")
public ResponseEntity<Resource> getFile(@PathVariable String filename) {
 Resource file = storageService.load(filename);

 return ResponseEntity.ok()
     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
}

@GetMapping("/images/delete/{filename:.+}")
public String deleteFile(@PathVariable String filename, Model model, RedirectAttributes redirectAttributes) {
 try {
   boolean existed = storageService.delete(filename);

   if (existed) {
     redirectAttributes.addFlashAttribute("message", "Deleted the file successfully: " + filename);
   } else {
     redirectAttributes.addFlashAttribute("message", "The file does not exist!");
   }
 } catch (Exception e) {
   redirectAttributes.addFlashAttribute("message",
       "Could not delete the file: " + filename + ". Error: " + e.getMessage());
 }

 return "redirect:/images";
}

@PostMapping("/directories/create")
public String createDirectory(@RequestParam("dirname") String dirname, RedirectAttributes redirectAttributes) {
 try {
   storageService.createDirectory(dirname);
   redirectAttributes.addFlashAttribute("message", "Created the directory successfully: " + dirname);
 } catch (Exception e) {
   redirectAttributes.addFlashAttribute("message", "Could not create the directory: " + dirname + ". Error: " + e.getMessage());
 }

 return "redirect:/images";
}

@PostMapping("/directories/delete")
public String deleteDirectory(@RequestParam("dirname") String dirname, RedirectAttributes redirectAttributes) {
 try {
   storageService.deleteDirectory(dirname);
   redirectAttributes.addFlashAttribute("message", "Deleted the directory successfully: " + dirname);
 } catch (Exception e) {
   redirectAttributes.addFlashAttribute("message", "Could not delete the directory: " + dirname + ". Error: " + e.getMessage());
 }

 return "redirect:/images";
}

@GetMapping("/directories/{dirname}/files")
public String getFilesFromDirectory(@PathVariable String dirname, Model model) {
 List<ImageInfo> fileInfos = storageService.loadAllFromDirectory(dirname).map(path -> {
   String filename = path.getFileName().toString();
   String url = MvcUriComponentsBuilder
       .fromMethodName(ImageController.class, "getFile", path.toString()).build().toString();
   String type = getFileType(path);
   return new ImageInfo(filename, url, type);
 }).collect(Collectors.toList());

 model.addAttribute("images", fileInfos);
 model.addAttribute("directory", dirname);

 return "images";
}

@GetMapping("/images/analyze/{filename:.+}")
public String analyzeFile(@PathVariable String filename, Model model, RedirectAttributes redirectAttributes) {
 // Placeholder for AI analysis logic
 String analysisResult = "Analysis result for file: " + filename; // This should call the actual AI service

 model.addAttribute("analysisResult", analysisResult);
 return "file_analysis";
}

@GetMapping("/api/images")
@ResponseBody
public List<ImageInfo> getImages() {
 List<ImageInfo> fileInfos = storageService.loadAll().map(path -> {
   String filename = path.getFileName().toString();
   String url = MvcUriComponentsBuilder
       .fromMethodName(ImageController.class, "getFile", path.getFileName().toString()).build().toString();
   String type = getFileType(path);
   return new ImageInfo(filename, url, type);
 }).collect(Collectors.toList());

 return fileInfos;
}

private String getFileType(Path path) {
 try {
   String contentType = Files.probeContentType(path);
   if (contentType != null) {
     if (contentType.startsWith("image")) {
       return "image";
     } else if (contentType.startsWith("video")) {
       return "video";
     }
   }
 } catch (IOException e) {
   e.printStackTrace();
 }
 return "unknown";
}
}

