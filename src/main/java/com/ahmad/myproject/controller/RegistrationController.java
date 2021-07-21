package com.ahmad.myproject.controller;

import com.ahmad.myproject.registeration.appuser.AppUser;
import com.ahmad.myproject.registeration.RegistrationRequest;
import com.ahmad.myproject.registeration.RegistrationService;
import com.ahmad.myproject.registeration.token.ConfirmationTokenRepository;
import com.ahmad.myproject.repository.AppUserRepository;
import com.ahmad.myproject.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Validated
@CrossOrigin("*")
public class RegistrationController {

    private final RegistrationService registrationService;
    AppUserRepository appUserRepository;
    AppUserService appUserService;
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }
    @Autowired
    public void setAppUserService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Autowired
    public void setConfirmationTokenRepository(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @PostMapping("/register")
    public String register (@Valid @RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
    @GetMapping
    public ResponseEntity<List<AppUser>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(appUserRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> findById(@PathVariable("id") Long id){
        if( appUserRepository.findById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(appUserRepository.findById(id).get());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/email")
    public ResponseEntity<AppUser> FindByEmail(@RequestParam String email){
      AppUser appUser =  appUserRepository.findByEmail(email).get();
        System.out.println(appUser);
        return ResponseEntity.status(HttpStatus.OK).body(appUser);
    }
}
