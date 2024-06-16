/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jedan.code.gofind.repositories;
import com.jedan.code.gofind.models.Stolen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author JD
 */
@Repository
public interface StolenRepository extends JpaRepository<Stolen, Long> {
    
}
