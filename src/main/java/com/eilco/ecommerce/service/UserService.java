package com.eilco.ecommerce.service;

import com.eilco.ecommerce.dto.UserRequest;
import com.eilco.ecommerce.dto.UserResponse;
import com.eilco.ecommerce.model.entities.User;
import com.eilco.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {
        User user = convertUserRequestToUser(userRequest);
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        return convertUserToUserResponse(user);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        existingUser.setUserName(userRequest.getUserName());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(userRequest.getPassword());
        existingUser.setAddress(userRequest.getAddress());
        existingUser.setPhoneNumber(userRequest.getPhoneNumber());

        User updatedUser = userRepository.save(existingUser);
        return convertUserToUserResponse(updatedUser);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    private User convertUserRequestToUser(UserRequest userRequest) {
        return User.builder()
                .userName(userRequest.getUserName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .address(userRequest.getAddress())
                .phoneNumber(userRequest.getPhoneNumber())
                .build();
    }

    private UserResponse convertUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getUserId());
        userResponse.setUserName(user.getUserName());
        userResponse.setEmail(user.getEmail());
        userResponse.setAddress(user.getAddress());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        return userResponse;
    }
}