package com.Graduation.Arabi_Fix.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Arabi-Fix")
public class DemoController
{
    @GetMapping("/demo")
    public ResponseEntity<String> sayHello()
    {
        return ResponseEntity.ok("Hello from Arabi Fix demo.");
    }
}
