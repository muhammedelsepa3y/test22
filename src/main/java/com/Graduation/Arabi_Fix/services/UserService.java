package com.Graduation.Arabi_Fix.services;

import com.Graduation.Arabi_Fix.entitie.User;
import com.Graduation.Arabi_Fix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    public User getUser(String email)
    {

        Optional<User> oUser = userRepository.findByEmail(email);

        if(oUser.isEmpty())throw new RuntimeException("User does not exist");

       return oUser.get();
    }

}
