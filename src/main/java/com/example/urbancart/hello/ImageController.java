package com.example.urbancart.hello;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ImageController {
  @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
    Resource resource = new ClassPathResource(String.format("images/%s", imageName));
    try {
      InputStream inputStream = resource.getInputStream();
      byte[] imageBytes = StreamUtils.copyToByteArray(inputStream);
      return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
    }
  }

  @PostMapping(value = "/images/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
    try {
      String originalFilename = file.getOriginalFilename();

      String imagePath = "images/" + originalFilename;

      byte[] imageBytes = StreamUtils.copyToByteArray(file.getInputStream());

      Path path = Paths.get(imagePath);
      Files.write(path, imageBytes);
      String imageUrl = "/images/" + originalFilename;
      return ResponseEntity.ok().body("Image uploaded successfully. URL: " + imageUrl);
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload image");
    }
  }
}
