/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author JD
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table( name = "Habitation")
public class Habitation extends Item {
    private String location;
    private String type;
    private int places;
    private int disponibles = places;
   
    @Column(columnDefinition = "TEXT")
    private String caracteristiques;
    
     @Override
    public String description(){
        
        return "Habitation de type "+type+" pour "+places+" à "+location+"\n"+caracteristiques;
    }
    
    @Override
    public void action(){
        if(disponibles >0){
            disponibles--;
            if(disponibles == 0)
                super.setOccupe(1);
        }
    }

    
    
    @Override
    public void reverse(){
        if(super.getOccupe() == 1)
                super.setOccupe(0);
        if(disponibles <0)
            disponibles++;
    }
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "habitation_participants",
            joinColumns = {@JoinColumn(name = "habitation_id" , referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "participants", referencedColumnName = "id")})
    private List<Account> participants = new ArrayList<>();
}
