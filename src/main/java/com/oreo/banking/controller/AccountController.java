package com.oreo.banking.controller;

import com.oreo.banking.payload.TransferRequest;
import com.oreo.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@Valid @RequestBody TransferRequest transferRequest, Authentication authentication) {
        // In a real application, you'd fetch the user's account based on authentication
        accountService.transferMoney(transferRequest.getFromAccountId(), transferRequest.getToAccountId(), transferRequest.getAmount());
        return ResponseEntity.ok("Transfer successful!");
    }
}

