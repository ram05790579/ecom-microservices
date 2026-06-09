package com.ecommerce.user.controllers;


import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {


//    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

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
        log.info("user information for user : {}" ,id);
        log.trace("user trace full deatiled : {}", id);
        log.debug("user information for developer : {}", id);
        log.warn("user information for user warning  : {}", id);
        log.error("user information for user full error : {}" ,id);
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
