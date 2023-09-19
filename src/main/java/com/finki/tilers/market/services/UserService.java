package com.finki.tilers.market.services;

import com.finki.tilers.market.exception.CustomBadRequestException;
import com.finki.tilers.market.model.dto.RegisterDtoUser;
import com.finki.tilers.market.model.entity.ApplicationUser;
import com.finki.tilers.market.model.entity.Role;
import com.finki.tilers.market.repositories.RoleRepository;
import com.finki.tilers.market.repositories.UserRepository;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private RoleService roleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Initializes the application by creating an admin user if it doesn't exist.
     */

    @PostConstruct
    public void init() {
//        Role adminRole = roleService.getByNameOrNull("ADMIN");
//        Role userRole = roleService.getByNameOrNull("USER");
//        Role modRole = roleService.getByNameOrNull("MODERATOR");

//        ApplicationUser admin = getByEmailOrNull("admin");
//        if (admin == null) {
//            admin = ApplicationUser.builder()
//                    .firstName("Admin")
//                    .lastName("Administrator")
//                    .email("admin")
//                    .isActive(true)
//                    .build();
//            admin.addRole(roleService.createRole("ADMIN"));
//            userRepository.save(admin);
//        }
//        ApplicationUser user = getByEmailOrNull("user");
//        if (user == null) {
//            userRepository.save(
//                    ApplicationUser.builder()
//                            .firstName("user")
//                            .lastName("user")
//                            .email("user")
//                            .isActive(true)
//                            .role(List.of(userRole))
//                            .build());
//        }
//        ApplicationUser mod = getByEmailOrNull("mod");
//        if (mod == null) {
//            userRepository.save(
//                    ApplicationUser.builder()
//                            .firstName("mod")
//                            .lastName("mod")
//                            .email("mod")
//                            .isActive(true)
//                            .role(List.of(userRole, modRole))
//                            .build());
//        }
    }
    /**
     * Retrieves a list of all users.
     *
     * @return A list of all users.
     */
    public List<ApplicationUser> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by email, allowing a partial match by searching for email ending with the specified value.
     *
     * @param email The partial email to match.
     * @return The ApplicationUser object matching the email.
     * @throws CustomBadRequestException If the user with the specified partial email doesn't exist.
     */
    public ApplicationUser getUserByEmail(String email) throws CustomBadRequestException {
        return userRepository.getApplicationUserByEmail(email)
                .orElseThrow(() -> new CustomBadRequestException("User with email " + email + " doesn't exist"));
    }
    /**
     * Retrieves a user by email, or returns null if the user doesn't exist.
     *
     * @param email The email of the user to retrieve.
     * @return The ApplicationUser object matching the email, or null if the user doesn't exist.
     */
    public ApplicationUser getByEmailOrNull(String email) {
        return userRepository.getApplicationUserByEmail(email).orElse(null);
    }
    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The ApplicationUser object matching the ID.
     * @throws CustomBadRequestException If the user with the specified ID doesn't exist.
     */
    public ApplicationUser getUserById(Long id) throws CustomBadRequestException {
        return userRepository.findById(id).orElseThrow(
                () -> new CustomBadRequestException("User with id " + id + " doesn't exist"));
    }

    /**
     * Retrieves the current authenticated user.
     *
     * @return The ApplicationUser object representing the current authenticated user.
     * @throws CustomBadRequestException If the current user cannot be retrieved.
     */
    public ApplicationUser getCurrentUser() throws CustomBadRequestException {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return getUserByEmail(email);
    }

    /**
     * Retrieves the email of the current authenticated user.
     *
     * @return The emailgit c of the current authenticated user.
     */
    public static String getCurrentEmail() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        } catch (Exception e) {
            return "startup";
        }
    }

    /**
     * Retrieves the current authentication object.
     *
     * @return The current Authentication object.
     * @throws CustomBadRequestException If the current authentication cannot be retrieved.
     */
    public Authentication getCurrentAuth() throws CustomBadRequestException {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    /**
     * Retrieves a list of all users.
     *
     * @return A list of all users.
     */
    public List<ApplicationUser> findAll() {

        return userRepository.findAll();
    }

    /**
     * Saves the specified user.
     *
     * @param user The user to save.
     * @return The saved ApplicationUser object.
     */
    public ApplicationUser saveUser(ApplicationUser user) {
        return userRepository.save(user);
    }

    /**
     * Creates and saves an ApplicationUser object based on the provided LdapUserDto and Docuvita object ID.
     *
     * @param user       The RegisterUserDto object containing the user information.
     * @return The created and saved ApplicationUser object.
     */
    public ApplicationUser createOrUpdateUser(RegisterDtoUser user, Boolean isRegister) {

        if (!isRegister && user.getId() == null) {
            throw new CustomBadRequestException("To update the user you must provide an id on the body.");
        }
        if(isRegister) {
            ApplicationUser us = getByEmailOrNull(user.getEmail());
            if(us != null) {
                throw new CustomBadRequestException("User already exists!");
            }
        }

        ApplicationUser userToCreate;
        if (user.getId() != null) {
            userToCreate = getUserById(user.getId());
        } else {
            userToCreate = new ApplicationUser();
        }
        userToCreate.setEmail(user.getEmail());
        userToCreate.setFirstName(user.getFirstName());
        userToCreate.setLastName(user.getLastName());
        userToCreate.setPhoneNumber(user.getPhoneNumber());
        userToCreate.setPassword(passwordEncoder.encode(user.getPassword()));

        if(isRegister) {
            Role role = roleService.getByNameOrNull("USER");
            userToCreate.setRole(List.of(role));
        }

        return userRepository.save(userToCreate);
    }

    public ApplicationUser checkIfUserPasswordMatches(String email, String rawPassword) {
        ApplicationUser user = userRepository.getApplicationUserByEmail(email).orElse(null);  // Assuming findByEmail method exists in your repository
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }

}
