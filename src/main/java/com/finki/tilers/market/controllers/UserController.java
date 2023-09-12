package com.finki.tilers.market.controllers;

import com.finki.tilers.market.model.dto.RegisterDtoUser;
import com.finki.tilers.market.model.entity.ApplicationUser;
import com.finki.tilers.market.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;



    @PostMapping("/update")
    public ResponseEntity<ApplicationUser> updateUser(@RequestBody RegisterDtoUser updateUserDto) {
        if (updateUserDto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // ID is required for updating
        }
        try {
            ApplicationUser updatedUser = userService.createOrUpdateUser(updateUserDto, false);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data")
    public ResponseEntity<ApplicationUser> getUserData() {
        try {
            ApplicationUser user = userService.getCurrentUser();
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
