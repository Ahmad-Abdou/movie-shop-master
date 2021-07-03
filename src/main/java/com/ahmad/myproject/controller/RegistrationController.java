package com.ahmad.myproject.controller;

import com.ahmad.myproject.appuser.AppUser;
import com.ahmad.myproject.registeration.RegistrationRequest;
import com.ahmad.myproject.registeration.RegistrationService;
import com.ahmad.myproject.repository.AppUserRepository;
import lombok.AllArgsConstructor;
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
}
