/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.controllers;

import com.jedan.code.gofind.models.Stolen;
import com.jedan.code.gofind.services.StolenService;
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
@RequestMapping("/Stolen")
public class StolenController {
    @Autowired
    private StolenService stolenService;
    
    @GetMapping
    public List<Stolen> getVoles(){
        return stolenService.getAll();
    }
    
    @GetMapping("/{id}")
    public Stolen getVole(@PathVariable Long id){
        Optional<Stolen> option = stolenService.getById(id);
        if(option.isPresent()){
            return option.get();
        }
        System.out.println(" Objet Volé inexistant!!!");
        return null;
    }
    
    @PostMapping
    public ResponseEntity createVole(@RequestBody Stolen stolen)throws URISyntaxException
    {
        Stolen savedStolen = stolenService.save(stolen);
        return ResponseEntity.created(new URI("/Stolen/"+savedStolen.getId())).body(savedStolen);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity updateVole(@PathVariable Long id, @RequestBody Stolen  stolen){
        Optional<Stolen> option = stolenService.getById(id);
        if(option.isPresent()){
            Stolen currentStolen = option.get();
            
            currentStolen = stolenService.save(stolen);
            
            return ResponseEntity.ok(currentStolen);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteVole(@PathVariable Long id){
        stolenService.delete(stolenService.getById(id).get());
        
        return ResponseEntity.ok().build();
    }
    
//    @PostMapping("/{id}/signaler")
//    public ResponseEntity signalerStolen(@PathVariable Long id, @RequestBody Account signaleur){
//        Optional<Stolen> option = stolenService.getById(id);
//        if(option.isPresent()){
//            Stolen concernedStolen = option.get();
//            Set<Account> signaleurs = concernedStolen.getSignaleurs();
//            signaleurs.add(signaleur);
//            concernedStolen.setSignaleurs(signaleurs);
//            stolenService.save(concernedStolen);
//            return ResponseEntity.ok(concernedStolen);
//        }
//        return ResponseEntity.status(404).body(id);
//    }
    
    @GetMapping("/recents")
    public List<Stolen> getRecents(){
        return stolenService.recents();
    }
    
    @PostMapping("/recherche")
    public List<Stolen> postRecherche(@RequestBody Map<String,String> param){
        return stolenService.recherche(param);
    }
}
