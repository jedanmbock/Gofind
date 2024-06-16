/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author JD
 */
@Entity
@Data
@Table(name = "image")
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "picByte", length = 1000)
    private byte[] picByte;
    
    public Image(){
        super();
    }
    public Image(String nom, String type, byte[] bytes){
        this.nom = nom;
        this.type = type;
        this.picByte = bytes;
    }
}
