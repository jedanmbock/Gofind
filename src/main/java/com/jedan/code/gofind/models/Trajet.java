/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author JD
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table( name = "Trajet")
public class Trajet extends Item {
    private String depart;
    private String arrivee;
    private LocalDateTime dateDepart;
    private int places;
    private int disponibles = places;
    
    public Trajet(){
        super();
    }

    public Trajet(String depart, String arrivee, LocalDateTime dateDepart, int places) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.dateDepart = dateDepart;
        this.places = places;
        disponibles = places;
    }
    
    
    @Override
    public String description(){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dddd, dd MM yyyy");
        DateTimeFormatter heureFormat = DateTimeFormatter.ofPattern("HH:mm");
        return "Trajet de "+depart+" à "+arrivee+"; départ le "+
                dateDepart.format(dateFormat)+" à "+dateDepart.format(heureFormat);
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
    @JoinTable(name = "trajet_participants",
            joinColumns = {@JoinColumn(name = "habitation_id" , referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "participants", referencedColumnName = "id")})
    private List<Account> participants = new ArrayList<>();
}
