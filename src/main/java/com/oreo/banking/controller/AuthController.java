package com.oreo.banking.controller;

import com.oreo.banking.model.RefreshToken;
import com.oreo.banking.model.User;
import com.oreo.banking.payload.*;
import com.oreo.banking.repository.UserRepository;
import com.oreo.banking.service.AuthService;
import com.oreo.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        System.out.println("Entered here.");
        String jwt = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        User user = userService.findByUsername(loginRequest.getUsername()).get();
        RefreshToken refreshToken = authService.createRefreshToken(user);

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        User user = userService.registerUser(signUpRequest.getUsername(), signUpRequest.getPassword());
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return authService.findByToken(requestRefreshToken)
                .map(authService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = authService.jwtUtils.generateJwtToken(user.getUsername());
                    return ResponseEntity.ok(new JwtResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.findByUsername(authentication.getName()).get();
            authService.deleteByUserId(user.getId());
        }
        return ResponseEntity.ok("Log out successful!");
    }
}
