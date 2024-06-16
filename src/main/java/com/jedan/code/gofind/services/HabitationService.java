/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.services;

import com.jedan.code.gofind.models.Habitation;
import com.jedan.code.gofind.repositories.HabitationRepository;
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
public class HabitationService {
    @Autowired
    private HabitationRepository habitationRepository;
    
    public Optional<Habitation> getById(Long id){
        return habitationRepository.findById(id);
    }
    
    public List<Habitation> getAll(){
        return habitationRepository.findAll();
    }
    
    public Habitation save(Habitation habitation){
        if(habitation.getId() == null){
            habitation.setDatePublication(LocalDateTime.now());
            habitation.setDisponibles(habitation.getPlaces());
        }
        return habitationRepository.save(habitation);
    }
    
    public void delete(Habitation habitation){
        habitationRepository.delete(habitation);
    }
    
    public List<Habitation> recherche(Map<String,String> param){
        Set<String> keys =  param.keySet().stream()
                .filter(p -> !param.get(p).equals(""))
                .collect(Collectors.toSet());
        List<Habitation> habitation = habitationRepository.findAll();
        if(keys.contains("type"))
            habitation = habitation.stream().filter(p -> keys.contains("type")&&p.getType().equals(param.get("type")))
                .collect(Collectors.toList());
        if(keys.contains("location"))
            habitation = habitation.stream().filter(p -> p.getLocation().equals(param.get("location")))
                .collect(Collectors.toList());
        
        
        return habitation;
    }
    
    public List<Habitation> recents(){
       List<Habitation> habitation = habitationRepository.findAll();
       habitation.sort(Comparator.comparing(Habitation::getDatePublication).reversed());
       return habitation.subList(0, 5);
    }
}
