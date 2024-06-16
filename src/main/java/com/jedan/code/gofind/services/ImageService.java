/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.services;

import com.jedan.code.gofind.models.Image;
import com.jedan.code.gofind.repositories.ImageRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JD
 */
@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    
    public Optional<Image> getById(Long id){
        return imageRepository.findById(id);
    }
    
    public List<Image> getAll(){
        return imageRepository.findAll();
    }
    
    public Image save(Image image){
        return imageRepository.save(image);
    }
    
    public void delete(Image image){
        imageRepository.delete(image);
    }
    
    public static byte[] compressBytes(byte[] data){
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while(!deflater.finished()){
            int count = deflater.deflate(buffer);
            outputStream.write(buffer,0,count);
        }
        try{
            outputStream.close();
        }catch(IOException e){
            System.out.println("Erreur de fermeture du stream ouvert pour l'image.");
        }
        System.out.println("Compressed Image Byte Size - "+outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    
    public static byte[] decompressBytes(byte[] data){
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try{
            while(!inflater.finished()){
                int count = inflater.inflate(buffer);
                outputStream.write(buffer,0,count);
            }
            outputStream.close();
        }catch(IOException e){
            System.out.println("Erreur de fermeture du stream ouvert pour l'image.");
        }catch(DataFormatException e){
            System.out.println("Erreur due au format des données.");
        }
        return outputStream.toByteArray();
    }
}
