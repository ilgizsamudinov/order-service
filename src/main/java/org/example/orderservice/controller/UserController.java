package org.example.orderservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.user.UserRequest;
import org.example.orderservice.dto.user.UserResponse;
import org.example.orderservice.mapper.UserMapper;
import org.example.orderservice.model.User;
import org.example.orderservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        User user = userMapper.toEntity(userRequest);
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(createdUser));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }


    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUser().stream().map(userMapper::toResponse).toList());
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
