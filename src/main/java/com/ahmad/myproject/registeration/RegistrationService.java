package com.ahmad.myproject.registeration;

import com.ahmad.myproject.registeration.appuser.AppUser;
import com.ahmad.myproject.registeration.appuser.AppUserRole;
import com.ahmad.myproject.registeration.token.ConfirmationToken;
import com.ahmad.myproject.registeration.token.ConfirmationTokenService;
import com.ahmad.myproject.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import static com.ahmad.myproject.registeration.PasswordValidator.validator;


@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request){
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail){
            throw  new IllegalStateException("Invalid email");
        }
        validator(request.getPassword().length());
        if(request.getEmail().contains("@gmail.com") || request.getEmail().contains("@hotmail.com")||request.getEmail().contains("@gmail.se")){

            String token =   appUserService.signUp(new AppUser(
                    request.getUserName(),
                    request.getEmail(),
                    request.getPassword(),
                    AppUserRole.USER));
            return token;
        }
        else throw new IllegalStateException("Email is not valid");

    }
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }


        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }


}