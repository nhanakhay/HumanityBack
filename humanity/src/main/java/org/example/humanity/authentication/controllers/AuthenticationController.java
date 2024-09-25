package org.example.humanity.authentication.controllers;

import lombok.RequiredArgsConstructor;
import org.example.humanity.authentication.pojo.login.GenerateLoginTokenRequest;
import org.example.humanity.authentication.pojo.login.GenerateLoginTokenResponse;
import org.example.humanity.authentication.pojo.login.LoginRequest;
import org.example.humanity.authentication.pojo.login.LoginResponse;
import org.example.humanity.authentication.pojo.registeration.RegistrationRequest;
import org.example.humanity.authentication.pojo.registeration.RegistrationResponse;
import org.example.humanity.authentication.services.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("register")
    public RegistrationResponse register(@RequestBody RegistrationRequest request) {

        return authenticationService.register(request);
    }

    @PostMapping("generate_login_token")
    public GenerateLoginTokenResponse generateLoginToken(@RequestBody GenerateLoginTokenRequest request) {

        return authenticationService.generateLoginToken(request);
    }

    @GetMapping("login_user")
    public LoginResponse LoginUser(@RequestBody LoginRequest request) {

        return authenticationService.loginUser(request);
    }
}
