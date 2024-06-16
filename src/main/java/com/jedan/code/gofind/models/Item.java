/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author JD
 */
@Data
@Entity
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private double prix;
    private LocalDateTime datePublication;
    private int occupe = 0;
    
    public abstract String description();
    public abstract void action();
    public abstract void reverse();
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "proprio_id", referencedColumnName = "id", nullable = false)
    private Account proprietaire;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    Image image;   
}
