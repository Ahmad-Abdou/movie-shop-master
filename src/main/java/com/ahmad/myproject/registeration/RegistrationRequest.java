package com.ahmad.myproject.registeration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationRequest {

    private final String userName;
    private final String email;
    private final String password;


}
