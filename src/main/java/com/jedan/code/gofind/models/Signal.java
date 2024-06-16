/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author JD
 */
@Entity
@Data
@Table(name = "signaux")
public class Signal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
            
    private String message;
    private int estLu = 0;
    
    @ManyToOne
    @JoinColumn(name = "account_signaler", referencedColumnName = "id", nullable = false)
    private Account accountSignaler;
    
    @ManyToOne
    @JoinColumn(name = "stolen_id", referencedColumnName = "id", nullable = false)
    private Stolen stolen;
}
