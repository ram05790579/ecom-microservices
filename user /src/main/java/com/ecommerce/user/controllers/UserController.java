package com.ecommerce.user.controllers;


import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //    @GetMapping("/api/users")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.fetchUsers());
    }


    //    @GetMapping("/api/users/{id}")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {

//        User user = userService.fetchUser(id);
//        if(user == null){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(user);

        return userService.fetchUser(id).stream()
                .map(ResponseEntity::ok)
                .findFirst().orElseGet(() -> ResponseEntity.notFound().build());

    }

    //    @PostMapping("/api/users")
    @PostMapping
    public ResponseEntity<String> createUsers(@RequestBody UserRequest userRequest) {

        userService.addUsers(userRequest);
        return ResponseEntity.ok("user added successfully");

    }

    //    @PutMapping("/api/users/{id}")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest updatedUserRequest) {

        boolean updated = userService.updateUser(id, updatedUserRequest);
        if (updated) {
            return ResponseEntity.ok("user updated successfully");

        }
        return ResponseEntity.notFound().build();
    }

}
