package com.inventario.backend_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.inventario.backend_inventario.entity.User;
import com.inventario.backend_inventario.repository.UserRepository;
import com.inventario.backend_inventario.util.ResourceNotFoundException;
import com.inventario.backend_inventario.util.PasswordUtil; // Importando o PasswordUtil

import jakarta.validation.Valid;

@Service
@Validated
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    public User createUser(@Valid User user) {

        user.setPassword(passwordUtil.encrypt(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
    }

    public User updateUser(User newUser) {
        validateUserExists(newUser.getId());

        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
            newUser.setPassword(passwordUtil.encrypt(newUser.getPassword()));
        }

        return userRepository.save(newUser);
    }

    public void deleteUser(Long id) {
        validateUserExists(id);
        userRepository.deleteById(id);
    }

    private void validateUserExists(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
    }
}
