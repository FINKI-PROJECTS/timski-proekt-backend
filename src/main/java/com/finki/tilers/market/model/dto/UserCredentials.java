package com.finki.tilers.market.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public
class UserCredentials {
    @NotNull(message = "Username must be provided")
    private String username;
    @NotNull(message = "Password must be provided")
    private String password;

    // Getter, Setter, and other standard methods
}
