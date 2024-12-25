package com.eilco.ecommerce.service;

import com.eilco.ecommerce.dto.UserRequest;
import com.eilco.ecommerce.dto.UserResponse;
import com.eilco.ecommerce.model.entities.CustomerUserDetails;
import com.eilco.ecommerce.model.entities.User;
import com.eilco.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest) {
        User user = convertUserRequestToUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode the password
        User savedUser = userRepository.save(user);
        return convertUserToUserResponse(savedUser);
    }

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertUserToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id.intValue())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        return convertUserToUserResponse(user);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id.intValue())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        User updatedUser = User.builder()
                .id(existingUser.getId())
                .username(existingUser.getUsername()) // Retain existing ID
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(existingUser.getRole()) // Retain existing role
                .build();

        updatedUser = userRepository.save(updatedUser);
        return convertUserToUserResponse(updatedUser);
    }


    public void deleteUserById(Long id) {
        userRepository.deleteById(id.intValue());
    }

    private User convertUserRequestToUser(UserRequest userRequest) {
        return User.builder()
                .username(userRequest.getUserName())
                .password(userRequest.getPassword())
                .role(userRequest.getRole()) // Map role
                .build();
    }

    private UserResponse convertUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUserName(user.getFirstName());
        userResponse.setRole(user.getRole());
        return userResponse;
    }

}