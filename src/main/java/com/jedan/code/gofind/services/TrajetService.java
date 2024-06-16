/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.services;

import com.jedan.code.gofind.models.Trajet;
import com.jedan.code.gofind.repositories.TrajetRepository;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JD
 */
@Service
public class TrajetService {
    @Autowired
    private TrajetRepository trajetRepository;
    
    public Optional<Trajet> getById(Long id){
        return trajetRepository.findById(id);
    }
    
    public List<Trajet> getAll(){
        return trajetRepository.findAll();
    }
    
    public Trajet save(Trajet trajet){
        if(trajet.getId() == null){
            trajet.setDatePublication(LocalDateTime.now());
            trajet.setDisponibles(trajet.getPlaces());
        }
        return trajetRepository.save(trajet);
    }
    
    public void delete(Trajet trajet){
        trajetRepository.delete(trajet);
    }
    
    public List<Trajet> recherche(Map<String,String> param){
        Set<String> keys =  param.keySet().stream()
                .filter(p -> !param.get(p).equals(""))
                .collect(Collectors.toSet());
        List<Trajet> trajet = trajetRepository.findAll();
        if(keys.contains("depart"))
            trajet = trajet.stream().filter(p -> keys.contains("depart")&&p.getDepart().equals(param.get("depart")))
                .collect(Collectors.toList());
        if(keys.contains("arrivee"))
            trajet = trajet.stream().filter(p -> p.getArrivee().equals(param.get("arrivee")))
                .collect(Collectors.toList());
        
        return trajet;
    }
    
    public List<Trajet> recents(){
       List<Trajet> trajet = trajetRepository.findAll();
       trajet.sort(Comparator.comparing(Trajet::getDatePublication).reversed());
       return trajet.subList(0, 5);
    }
}
