package com.Graduation.Arabi_Fix.controllers;

import com.Graduation.Arabi_Fix.dto.AuthenticationRequest;
import com.Graduation.Arabi_Fix.dto.AuthenticationResponse;
import com.Graduation.Arabi_Fix.dto.RegisterRequest;
import com.Graduation.Arabi_Fix.entitie.User;
import com.Graduation.Arabi_Fix.repositories.UserRepository;
import com.Graduation.Arabi_Fix.services.AuthenticationService;
import com.Graduation.Arabi_Fix.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Optional;

@RestController
@RequestMapping("/Arabi-Fix/authentication")
@RequiredArgsConstructor
public class AuthenticationController
{

    private final AuthenticationService service;





    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder;




    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
    {
        return ResponseEntity.ok(service.authenticate(request));
    }




}
