package com.finki.tilers.market.model.dto;

import com.finki.tilers.market.model.entity.Role;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDtoUser {
    private Long id;
    private List<Role> role;

    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "First Name is required")
    private String firstName;
    @NotNull(message = "Last Name is required")
    private String lastName;
    private String phoneNumber;
}
