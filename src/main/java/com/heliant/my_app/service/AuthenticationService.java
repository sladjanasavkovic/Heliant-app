package com.heliant.my_app.service;

import com.heliant.my_app.dto.AuthenticationRequest;
import com.heliant.my_app.dto.AuthenticationResponse;
import com.heliant.my_app.model.User;
import com.heliant.my_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Value("${security.jwt.expiration-time}")
    private Long tokenExpirationTime;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenService tokenService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    private String createAccessToken(User user){
        return tokenService.generate(user, tokenExpirationTime);
    }

    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                                                authenticationRequest.getUsername(),
                                                authenticationRequest.getPassword()
                                            ));

        User user = userRepository.findByUsername(authenticationRequest.getUsername()).get();

        return new AuthenticationResponse(createAccessToken(user));
    }


}
