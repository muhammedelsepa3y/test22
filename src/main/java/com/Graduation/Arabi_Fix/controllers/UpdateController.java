package com.Graduation.Arabi_Fix.controllers;

import com.Graduation.Arabi_Fix.dto.ChangePasswordRequest;
import com.Graduation.Arabi_Fix.dto.UpdateNameRequest;
import com.Graduation.Arabi_Fix.entitie.User;
import com.Graduation.Arabi_Fix.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/Arabi-Fix/update-profile")
@RequiredArgsConstructor
public class UpdateController
{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @PostMapping("/update-name")
    public String updateName(@RequestBody UpdateNameRequest updateNameRequest)
    {
        String email = updateNameRequest.getEmail();

        Optional<User> oUser = userRepository.findByEmail(email);

        if(oUser.isEmpty())return "User does not exist";

        User user = oUser.get();

        user.setFirstName(updateNameRequest.getFirstName());
        user.setLastName(updateNameRequest.getLastName());

        userRepository.save(user);

        return "Successfully update name of the user.";


    }


    @PostMapping("/change-password")
    public String changePassword(@RequestBody ChangePasswordRequest changePasswordRequest)
    {
        String email = changePasswordRequest.getEmail();

        Optional<User> oUser = userRepository.findByEmail(email);

        if(oUser.isEmpty())return "User does not exist";
        User user = oUser.get();


        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);



        return "Password was changed Successfully.";

    }


}
