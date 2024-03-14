package com.heliant.my_app.controller;

import com.heliant.my_app.dto.AuthenticationRequest;
import com.heliant.my_app.dto.AuthenticationResponse;
import com.heliant.my_app.service.AuthenticationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @ApiResponse(responseCode = "200")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid final AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.authentication(authenticationRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
