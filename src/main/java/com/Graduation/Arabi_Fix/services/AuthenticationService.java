package com.Graduation.Arabi_Fix.services;

import com.Graduation.Arabi_Fix.dto.AuthenticationRequest;
import com.Graduation.Arabi_Fix.dto.AuthenticationResponse;
import com.Graduation.Arabi_Fix.dto.RegisterRequest;
import com.Graduation.Arabi_Fix.entitie.Role;
import com.Graduation.Arabi_Fix.entitie.Token;
import com.Graduation.Arabi_Fix.entitie.TokenType;
import com.Graduation.Arabi_Fix.entitie.User;
import com.Graduation.Arabi_Fix.repositories.TokenRepository;
import com.Graduation.Arabi_Fix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;
    public AuthenticationResponse register(RegisterRequest request)
    {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(Role.USER)
                .password(encoder.encode(request.getPassword()))
                .build();

        var sUser = repository.save(user);

        String jwtToken = jwtService.generateToken(user);

        var token  = Token.builder().user(sUser).token(jwtToken)
                .tokenType(TokenType.BEARER).revoked(false).expired(false).build();

        tokenRepository.save(token);


        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        User user = repository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        revokeTokens(user);

        var token  = Token.builder().user(user).token(jwtToken)
                .tokenType(TokenType.BEARER).revoked(false).expired(false).build();

        tokenRepository.save(token);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public void revokeTokens(User user)
    {
        var tokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(tokens.isEmpty())return;

        tokens.forEach(t -> {t.setRevoked(true); t.setExpired(true);});

        tokenRepository.saveAll(tokens);
    }
}
