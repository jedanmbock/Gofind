/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.services;

import com.jedan.code.gofind.models.Stolen;
import com.jedan.code.gofind.repositories.StolenRepository;
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
public class StolenService {
    @Autowired
    private StolenRepository stolenRepository;
    
    public Optional<Stolen> getById(Long id){
        return stolenRepository.findById(id);
    }
    
    public List<Stolen> getAll(){
        return stolenRepository.findAll();
    }
    
    public Stolen save(Stolen stolen){
        if(stolen.getId() == null){
            stolen.setDatePublication(LocalDateTime.now());
        }
        return stolenRepository.save(stolen);
    }
    
    public void delete(Stolen stolen){
        stolenRepository.delete(stolen);
    }
    
    public List<Stolen> recherche(Map<String,String> param){
        Set<String> keys =  param.keySet().stream()
                .filter(p -> !param.get(p).equals(""))
                .collect(Collectors.toSet());
        List<Stolen> stolen = stolenRepository.findAll();
        if(keys.contains("couleur"))
            stolen = stolen.stream().filter(p -> keys.contains("couleur")&&p.getCouleur().equals(param.get("couleur")))
                .collect(Collectors.toList());
        if(keys.contains("marque"))
            stolen = stolen.stream().filter(p -> p.getMarque().equals(param.get("marque")))
                .collect(Collectors.toList());
        if(keys.contains("type"))
            stolen = stolen.stream().filter(p -> p.getType().equals(param.get("type")))
                .collect(Collectors.toList());
        
        return stolen;
    }
    
    public List<Stolen> recents(){
       List<Stolen> stolen = stolenRepository.findAll();
       stolen.sort(Comparator.comparing(Stolen::getDatePublication).reversed());
       return stolen.subList(0, 5);
    }
}
