package com.Graduation.Arabi_Fix.controllers;

import com.Graduation.Arabi_Fix.entitie.User;
import com.Graduation.Arabi_Fix.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Arabi-Fix/get-profile")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;
    @GetMapping
    public User getUser(@RequestBody String email)
    {
        return userService.getUser(email);
    }

}
