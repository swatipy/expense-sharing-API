package org.example.controller;

import org.example.dto.UserSignupInputDTO;
import org.example.model.ApiResponse;
import org.example.dto.UserLoginInputDTO;
import org.example.repository.UserRepository;
import org.example.service.UserDetailsServiceImpl;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody UserSignupInputDTO signupInput,
                                              BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse("Validation failed", result.getFieldErrors()));
        }

        boolean isUserCreated = userDetailsService.createUser(signupInput);

        if (isUserCreated) {
            return new ResponseEntity<>(new ApiResponse("User created successfully", null), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ApiResponse("User creation failed", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/validate")
    public ResponseEntity<String> validate(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        System.out.println("Token received: " + token);
        System.out.println("Extracted username: " + jwtUtil.extractUsername(token));
        try {
            String username = jwtUtil.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                return ResponseEntity.ok("Valid");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token validation failed");
        }

    }


    // POST /api/login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginInputDTO request) {
        System.out.println("Username: " + request.getUsername());
        System.out.println("Password: " + request.getPassword());
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // If successful, generate token
        //return jwtUtil.generateToken(request.getUsername());

        String jwt = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(Map.of("token", jwt));
    }
}


