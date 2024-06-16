/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

/**
 *
 * @author JD
 */
@Entity
@Data
@Table( name = "Stolen")
public class Stolen {
    
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDateTime datePublication; 
    private String marque;
    private String type;
    private String couleur;
    private int occupe = 0;
    
    @Column(columnDefinition = "TEXT")
    private String caracteristiques;
    
    public String description(){
        return type+" de marque "+marque+"de couleur "+couleur+"\n"+caracteristiques;
    }
    
    public void action(){
        occupe = 1;
    }
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "plaignant_id", referencedColumnName = "id", nullable = false)
    private Account plaignant;
    
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "signaux",
//            joinColumns = {@JoinColumn(name = "stolen_id" , referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "account_signaler", referencedColumnName = "nom")})
//    private Set<Account> signaleurs = new HashSet<>();
}
