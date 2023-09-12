package com.finki.tilers.market.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ApplicationUser class. Stores metadata about users in the database.
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Role> role;


    public void addRole(Role role) {
        if (this.role == null) {
            this.role = new ArrayList<>();
        }
        this.role.add(role);
    }

    @JsonProperty("role")
    public String getRoleNames() {
        return role.stream().map(Role::getRoleName).collect(Collectors.joining(", "));
    }

    private String phoneNumber;
    @JsonIgnore
    private String password;

    /**
     * Email must be unique or SQLServerException will be thrown
     */
    private String email;
    private String firstName;
    private String lastName;

    @JsonIgnore
    @Column(columnDefinition = "VARCHAR(5000)") // Set the desired length for the column
    private String refreshToken;

    @JsonIgnore
    @Column(columnDefinition = "VARCHAR(50000)") // Set the desired length for the column
    private String accessToken;

    /**
     * If the user is not active, user cannot login
     */
    @JsonIgnore
    private Boolean isActive;

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public boolean isUserEqual(ApplicationUser user) {
        return this.getEmail().equals(user.getEmail()) && this.getId().equals(user.getId());
    }

}
