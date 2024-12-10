package com.inventario.backend_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.inventario.backend_inventario.entity.UserLoan;
import com.inventario.backend_inventario.entity.dto.UserLoanDTO;
import com.inventario.backend_inventario.service.UserLoanService;
import com.inventario.backend_inventario.util.ApiErrorResponse;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/userLoan")
public class UserLoanController {
    

 
    @Autowired
    private UserLoanService userLoanService;

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserLoan userLoan) {
        try {

            UserLoanDTO createdUser = userLoanService.createUser(userLoan);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

        } catch (DataIntegrityViolationException e) {

            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.CONFLICT.value(),
                    "Conflict",
                    e.getMessage(),
                    "/api/userLoan");

            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

        } catch (Exception e) {

            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/api/userLoan");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<UserLoanDTO> users = userLoanService.getAllUsers();
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/api/userLoan");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getIdUser(@PathVariable Long id) {
        try {
            UserLoanDTO userLoan = userLoanService.getUserById(id);
            return ResponseEntity.ok(userLoan);
        } catch (RuntimeException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    e.getMessage(),
                    "/api/userLoan/" + id);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Erro ao buscar o usuário",
                    "/api/userLoan/" + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserLoan userLoan) {

        try {

            UserLoanDTO updateUser = userLoanService.updateUser(userLoan);
            return ResponseEntity.status(HttpStatus.CREATED).body(updateUser);

        } catch (DataIntegrityViolationException e) {

            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.CONFLICT.value(),
                    "Conflict",
                    e.getMessage(),
                    "/api/userLoan");

            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

        } catch (RuntimeException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    e.getMessage(),
                    "/api/userLoan/" + userLoan.getId());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {

            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/api/userLoan");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {

        try {
            userLoanService.deleteUser(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            // Retorna erro 404 caso o usuário não seja encontrado
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    e.getMessage(),
                    "/api/userLoan/" + id);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            // Retorna erro 500 para qualquer erro interno no servidor
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/api/userLoan/" + id);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

} 

