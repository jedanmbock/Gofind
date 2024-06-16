/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jedan.code.gofind.controllers;

import com.jedan.code.gofind.models.Account;
import com.jedan.code.gofind.models.Habitation;
import com.jedan.code.gofind.models.Signal;
import com.jedan.code.gofind.models.Stolen;
import com.jedan.code.gofind.models.Trajet;
import com.jedan.code.gofind.services.AccountService;
import com.jedan.code.gofind.services.HabitationService;
import com.jedan.code.gofind.services.SignalService;
import com.jedan.code.gofind.services.StolenService;
import com.jedan.code.gofind.services.TrajetService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JD
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/accounts")
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private StolenService stolenService;
    
    @Autowired
    private TrajetService trajetService;
    
    @Autowired
    private HabitationService habitationService;
    
    @Autowired
    private SignalService signalService;
    
    @GetMapping
    public List<Account> getAccounts(){
        return accountService.getAll();
    }
    
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id){
        Optional<Account> option = accountService.getById(id);
        if(option.isPresent()){
            return option.get();
        }
        System.out.println("Account absent recherché!!!");
        return null;
    }
    
    @PostMapping
    public ResponseEntity createAccount(@RequestBody Account account)throws URISyntaxException
    {        
        Account savedAccount = accountService.save(account);
        return ResponseEntity.created(new URI("/accounts/"+savedAccount.getId())).body(savedAccount);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity updateAccount(@PathVariable Long id, @RequestBody Account account){
        Optional<Account> option = accountService.getById(id);
        if(option.isPresent()){
            Account currentAccount = option.get();
            currentAccount.setEmail(account.getEmail());
            currentAccount.setMobile(account.getMobile());
            currentAccount.setNom(account.getNom());
            currentAccount.setPassword(account.getPassword());
            
            currentAccount = accountService.save(account);
            
            return ResponseEntity.ok(currentAccount);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable Long id){
        accountService.delete(accountService.getById(id).get());
        
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/recherche")
    public Account rechercheAccount(@RequestParam String email,@RequestParam String password){
        if(accountService.getAll().stream()
                .filter(e -> e.getEmail().equals(email)&&e.getPassword().equals(password))
                .toArray(Account[]::new).length!=0)
            return accountService.getAll().stream()
                .filter(e -> e.getEmail().equals(email)&&e.getPassword().equals(password))
                .toArray(Account[]::new)[0];
        return null;
    }
    
    @GetMapping("/{id}/objetsSignales")
    public List<Stolen> getStolen(@PathVariable Long id){
        Account account = this.getAccount(id);
        if(account!=null)
            return stolenService.getAll().stream()
                .filter(e -> e.getPlaignant().getId().equals(this.getAccount(id).getId()))
                .toList();
        return null;
    }
    
    @GetMapping("/{id}/trajetsProposes")
    public List<Trajet> getTrajets(@PathVariable Long id){
        Account account = this.getAccount(id);
        if(account!=null)
            return trajetService.getAll().stream()
                .filter(e -> e.getProprietaire().getId().equals(this.getAccount(id).getId()))
                .toList();
        return null;
    }
    
    @GetMapping("/{id}/habitationsProposes")
    public List<Habitation> getHabitations(@PathVariable Long id){
        Account account = this.getAccount(id);
        if(account!=null)
            return habitationService.getAll().stream()
                .filter(e -> e.getProprietaire().getId().equals(this.getAccount(id).getId()))
                .toList();
        return null;
    }
    
    @GetMapping("/{id}/signauxRecus")
    public List<Signal> getSignaux(@PathVariable Long id){
        Account account = this.getAccount(id);
        if(account!=null)
            return signalService.getAll().stream()
                .filter(e -> e.getStolen().getPlaignant().getId().equals(this.getAccount(id).getId()))
                .toList();
        return null;
    }
}
