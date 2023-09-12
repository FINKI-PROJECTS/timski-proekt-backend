package com.finki.tilers.market.model.dto;

import com.finki.tilers.market.model.entity.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDtoUser {
    private Long id;

    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password must not be empty")
    @Length(min = 5, message = "Password length must be at least 5 chars")
    private String password;
    @NotNull(message = "First Name is required")
    private String firstName;
    @NotNull(message = "Last Name is required")
    private String lastName;
    private String phoneNumber;
}
