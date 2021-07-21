package com.ahmad.myproject.controller;


import com.ahmad.myproject.registeration.appuser.AppUser;
import com.ahmad.myproject.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/api/v1")
@AllArgsConstructor
public class AdminController {
    AppUserRepository appUserRepository;

    @GetMapping()
    public ResponseEntity<List<AppUser>> findAllEnabledUsers () {

        return ResponseEntity.status(HttpStatus.OK).body(appUserRepository.findAppUserByEnabled(true));
    }
    @PutMapping
    public ResponseEntity<AppUser> update(@RequestBody AppUser appUser){
        if(appUser == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(appUserRepository.save(appUser));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        Optional<AppUser> user =  appUserRepository.findById(id);
        if(user.isPresent()){
            appUserRepository.delete(user.get());
            return  ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/email")
    public ResponseEntity<Optional<AppUser>> findByFirstEmail(@RequestParam(value = "email") String email){
        Optional<AppUser> user = appUserRepository.findByEmail(email);
        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        throw new IllegalStateException("email not exist");
    }
}
