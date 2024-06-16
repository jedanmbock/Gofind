/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.controllers;

import com.jedan.code.gofind.models.Signal;
import com.jedan.code.gofind.services.SignalService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
@RequestMapping("/signaux")
public class SignalController {
    
    @Autowired
    private SignalService signalService;
    
    @GetMapping
    public List<Signal> getSignaux(){
        return signalService.getAll();
    }
    
    @GetMapping("/{id}")
    public Signal getSignal(@PathVariable Long id){
        Optional<Signal> option = signalService.getById(id);
        if(option.isPresent()){
            return option.get();
        }
        System.out.println("Signal absent recherché!!!");
        return null;
    }
    
    @PostMapping
    public ResponseEntity createSignal(@RequestBody Signal signal)throws URISyntaxException
    {
        Signal savedSignal = signalService.save(signal);
        return ResponseEntity.created(new URI("/signaux/"+savedSignal.getId())).body(savedSignal);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity updateSignal(@PathVariable Long id, @RequestBody Signal signal){
        Optional<Signal> option = signalService.getById(id);
        if(option.isPresent()){
            Signal currentSignal = option.get();
            
            currentSignal = signalService.save(signal);
            
            return ResponseEntity.ok(currentSignal);
        }
        return ResponseEntity.status(404).body(id);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteSignal(@PathVariable Long id){
        signalService.delete(signalService.getById(id).get());
        return ResponseEntity.ok().build();
    }
}
