/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.controllers;

import com.jedan.code.gofind.models.Image;
import com.jedan.code.gofind.services.ImageService;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author JD
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/images")
public class ImageController {
    @Autowired
    ImageService imageService;
    
    @PostMapping("/upload")
    public ResponseEntity uploadImage(@RequestBody MultipartFile file) throws IOException{
        System.out.println("Original Image Byte Size - "+file.getBytes().length);
        Image img = new Image(file.getOriginalFilename(),file.getContentType(),ImageService.compressBytes(file.getBytes()));
        imageService.save(img);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/get/{id}")
    public Image getImage(@PathVariable Long id) throws IOException{
        final Optional<Image> retrievedImage = imageService.getById(id);
        Image img = new Image(retrievedImage.get().getNom(),retrievedImage.get().getType(),
            ImageService.decompressBytes(retrievedImage.get().getPicByte()));
        return img;
    }
}
