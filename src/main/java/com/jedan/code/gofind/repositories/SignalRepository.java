/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.repositories;

import com.jedan.code.gofind.models.Signal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author JD
 */
public interface SignalRepository extends JpaRepository<Signal, Long>{
    
}
