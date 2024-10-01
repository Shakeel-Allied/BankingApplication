package com.oreo.banking.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshRequest {

    @NotBlank
    private String refreshToken;

    // Getters and Setters
    // ...
}
