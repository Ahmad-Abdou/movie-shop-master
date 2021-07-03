package com.ahmad.myproject.service;


import com.ahmad.myproject.appuser.AppUser;
import com.ahmad.myproject.registeration.token.ConfirmationToken;
import com.ahmad.myproject.registeration.token.ConfirmationTokenService;
import com.ahmad.myproject.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    public void setUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return appUserRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Not Found : "+email));
    }

    public String signUp(AppUser user){
        Optional<AppUser> email =  appUserRepository.findByEmail(user.getEmail());
        if(email.isPresent()){
            throw new IllegalStateException("Email already exist");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        appUserRepository.save(user);
        String token =  UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }
    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

}
