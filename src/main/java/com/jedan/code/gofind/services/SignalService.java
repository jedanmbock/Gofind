/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.services;

import com.jedan.code.gofind.models.Signal;
import com.jedan.code.gofind.repositories.SignalRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JD
 */
@Service
public class SignalService {
    @Autowired
    private SignalRepository signalRepository;
    
    public Optional<Signal> getById(Long id){
        return signalRepository.findById(id);
    }
    
    public List<Signal> getAll(){
        return signalRepository.findAll();
    }
    
    public Signal save(Signal signal){
        return signalRepository.save(signal);
    }
    
    public void delete(Signal signal){
        signalRepository.delete(signal);
    }
}
