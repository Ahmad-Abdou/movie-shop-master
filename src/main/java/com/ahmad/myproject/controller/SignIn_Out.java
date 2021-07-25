package com.ahmad.myproject.controller;

import com.ahmad.myproject.registeration.appuser.AppUser;
import com.ahmad.myproject.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class SignIn_Out {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AppUserRepository appUserRepository;
    public static AppUser user;



    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }
    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody AppUser user){
      Optional<AppUser> user2 = appUserRepository.findAppUserByEmail(user.getEmail());

        if(!user2.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(),user2.get().getPassword())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong password");
        }

        else
            SignedInUser(user2.get());
            return ResponseEntity.status(HttpStatus.OK).body("Login Successful");

    }
    @GetMapping("/signin")
    public  ResponseEntity<AppUser> myUser(){
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/signout")
    public  ResponseEntity<AppUser> signOut(){
        user = null;
        SignedInUser(null);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    public static AppUser SignedInUser (AppUser appUser){
        user= appUser;
        return  appUser;
    }

}
