package org.example.orderservice.service;


import lombok.RequiredArgsConstructor;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.mapper.UserMapper;
import org.example.orderservice.model.User;
import org.example.orderservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }



    @Transactional(readOnly = true)
    public List<User> getAllUser() {
        return userRepository.findAll();
    }




    @Transactional
    public void deleteUserById(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }


    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found"));
    }

}
