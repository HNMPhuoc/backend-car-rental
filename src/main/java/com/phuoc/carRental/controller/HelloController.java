package com.phuoc.carRental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "Hello controller")
public class HelloController {

    @Operation(summary = "test api", description = "Mo ta chi tiet")
    @GetMapping("/hello")
    public String greeting(@RequestParam String name) {
        return "hello" + name;
    }
}
