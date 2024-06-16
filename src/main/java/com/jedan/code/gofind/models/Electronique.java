/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author JD
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table( name = "Electronique")
public class Electronique extends Item {
    private String marque;
    private String type;
    private String couleur;
    
    @Column(columnDefinition = "TEXT")
    private String caracteristiques;
    
    @Override
    public String description(){
        return type+" de marque "+marque+"de couleur "+couleur+"\n"+caracteristiques;
    }
    
    @Override
    public void action(){
        super.setOccupe(1);
    }
    
    @Override
    public void reverse(){
        super.setOccupe(0);
    }
}
