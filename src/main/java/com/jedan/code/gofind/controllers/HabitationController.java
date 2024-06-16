/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.controllers;

import com.jedan.code.gofind.models.Account;
import com.jedan.code.gofind.models.Habitation;
import com.jedan.code.gofind.services.HabitationService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JD
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/habitations")
public class HabitationController {
    @Autowired
    private HabitationService habitationService;
    
    @GetMapping
    public List<Habitation> getHabitations(){
        return habitationService.getAll();
    }
    
    @GetMapping("/{id}")
    public Habitation getHabitation(@PathVariable Long id){
        Optional<Habitation> option = habitationService.getById(id);
        if(option.isPresent()){
            return option.get();
        }
        System.out.println("Habitation absente recherchée!!!");
        return null;
    }
    
    @PostMapping
    public ResponseEntity createHabitation(@RequestBody Habitation habitation)throws URISyntaxException
    {
        Habitation savedHabitation = habitationService.save(habitation);
        return ResponseEntity.created(new URI("/habitations/"+savedHabitation.getId())).body(savedHabitation);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity updateHabitation(@PathVariable Long id, @RequestBody Habitation habitation){
        Optional<Habitation> option = habitationService.getById(id);
        if(option.isPresent()){
            Habitation currentHabitation = option.get();
            
            currentHabitation = habitationService.save(habitation);
            
            return ResponseEntity.ok(currentHabitation);
        }
        return ResponseEntity.status(404).body(id);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteHabitation(@PathVariable Long id){
        habitationService.delete(habitationService.getById(id).get());
        
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/participer")
    public ResponseEntity participerHabitation(@PathVariable Long id, @RequestBody Account participant){
        Optional<Habitation> option = habitationService.getById(id);
        if(option.isPresent()){
            Habitation concernedHabitation = option.get();
            if(concernedHabitation.getDisponibles()>0){
                concernedHabitation.action();
                List<Account> participants = concernedHabitation.getParticipants();
                participants.add(participant);
                concernedHabitation.setParticipants(participants);
                habitationService.save(concernedHabitation);
                return ResponseEntity.ok(concernedHabitation);
            }
            return ResponseEntity.status(403).body(id);
        }
        return ResponseEntity.status(404).body(id);
    }
    
    @PostMapping("/{id}/quitter")
    public ResponseEntity quitterHabitation(@PathVariable Long id, @RequestBody Account participant){
        Optional<Habitation> option = habitationService.getById(id);
        if(option.isPresent()){
            Habitation concernedHabitation = option.get();
            if(concernedHabitation.getPlaces()>concernedHabitation.getDisponibles()){
                concernedHabitation.reverse();
                List<Account> participants = concernedHabitation.getParticipants();
                participants.remove(participant);
                concernedHabitation.setParticipants(participants);
                habitationService.save(concernedHabitation);
                return ResponseEntity.ok(concernedHabitation);
            }
            return ResponseEntity.status(403).body(id);
        }
        return ResponseEntity.status(404).body(id);
    }
    
    @GetMapping("/recents")
    public List<Habitation> getRecents(){
        return habitationService.recents();
    }
    
    @PostMapping("/recherche")
    public List<Habitation> postRecherche(@RequestBody Map<String,String> param){
        return habitationService.recherche(param);
    }
}
