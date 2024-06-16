/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.controllers;

import com.jedan.code.gofind.models.Account;
import com.jedan.code.gofind.models.Trajet;
import com.jedan.code.gofind.services.TrajetService;
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
@RequestMapping("/trajets")
public class TrajetController {
    @Autowired
    private TrajetService trajetService;
    
    @GetMapping
    public List<Trajet> getTrajets(){
        return trajetService.getAll();
    }
    
    @GetMapping("/{id}")
    public Trajet getTrajet(@PathVariable Long id){
        Optional<Trajet> option = trajetService.getById(id);
        if(option.isPresent()){
            return option.get();
        }
        System.out.println(" Objet Volé inexistant!!!");
        return null;
    }
    
    @PostMapping
    public ResponseEntity createTrajet(@RequestBody Trajet trajet)throws URISyntaxException
    {
        Trajet savedTrajet = trajetService.save(trajet);
        return ResponseEntity.created(new URI("/trajets/"+savedTrajet.getId())).body(savedTrajet);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity updateTrajet(@PathVariable Long id, @RequestBody Trajet  trajet){
        Optional<Trajet> option = trajetService.getById(id);
        if(option.isPresent()){
            Trajet currentTrajet = option.get();
            
            currentTrajet = trajetService.save(trajet);
            
            return ResponseEntity.ok(currentTrajet);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTrajet(@PathVariable Long id){
        trajetService.delete(trajetService.getById(id).get());
        
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/participer")
    public ResponseEntity participerTrajet(@PathVariable Long id, @RequestBody Account participant){
        Optional<Trajet> option = trajetService.getById(id);
        if(option.isPresent()){
            Trajet concernedTrajet = option.get();
            if(concernedTrajet.getDisponibles()>0){
                concernedTrajet.action();
                List<Account> participants = concernedTrajet.getParticipants();
                participants.add(participant);
                concernedTrajet.setParticipants(participants);
                trajetService.save(concernedTrajet);
                return ResponseEntity.ok(concernedTrajet);
            }
            return ResponseEntity.status(403).body(id);
        }
        return ResponseEntity.status(404).body(id);
    }
    
    @PostMapping("/{id}/quitter")
    public ResponseEntity quitterTrajet(@PathVariable Long id, @RequestBody Account participant){
        Optional<Trajet> option = trajetService.getById(id);
        if(option.isPresent()){
            Trajet concernedTrajet = option.get();
            if(concernedTrajet.getPlaces()>concernedTrajet.getDisponibles()){
                concernedTrajet.reverse();
                List<Account> participants = concernedTrajet.getParticipants();
                participants.remove(participant);
                concernedTrajet.setParticipants(participants);
                trajetService.save(concernedTrajet);
                return ResponseEntity.ok(concernedTrajet);
            }
            return ResponseEntity.status(403).body(id);
        }
        return ResponseEntity.status(404).body(id);
    }
    
    @GetMapping("/recents")
    public List<Trajet> getRecents(){
        return trajetService.recents();
    }
    
    @PostMapping("/recherche")
    public List<Trajet> postRecherche(@RequestBody Map<String,String> param){
        return trajetService.recherche(param);
    }
}
