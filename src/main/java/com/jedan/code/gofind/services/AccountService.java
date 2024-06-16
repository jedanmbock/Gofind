/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.services;

import com.jedan.code.gofind.models.Account;
import com.jedan.code.gofind.repositories.AccountRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JD
 */
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    
    public Optional<Account> getById(Long id){
        return accountRepository.findById(id);
    }
    
    public List<Account> getAll(){
        return accountRepository.findAll();
    }
    
    public Account save(Account account){
        return accountRepository.save(account);
    }
    
    public void delete(Account account){
        accountRepository.delete(account);
    }
    
}
