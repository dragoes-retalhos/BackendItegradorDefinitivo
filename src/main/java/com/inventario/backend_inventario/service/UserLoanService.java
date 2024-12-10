package com.inventario.backend_inventario.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.inventario.backend_inventario.entity.UserLoan;
import com.inventario.backend_inventario.entity.dto.UserLoanDTO;
import com.inventario.backend_inventario.repository.UserLoanRepository;
import com.inventario.backend_inventario.util.ResourceNotFoundException;

import jakarta.validation.Valid;

@Service
@Validated
public class UserLoanService {

    @Autowired
    private UserLoanRepository userRepository;

    /**
     * Cria um novo usuário.
     *
     * @param userLoan Dados do usuário.
     * @return Usuário criado.
     */
    public UserLoanDTO createUser(@Valid UserLoan userLoan) {
        UserLoan savedUser = userRepository.save(userLoan);
        return UserLoanDTO.fromEntity(savedUser);
    }

    /**
     * Retorna todos os usuários.
     *
     * @return Lista de usuários como DTOs.
     */
    public List<UserLoanDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserLoanDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Retorna um usuário pelo ID.
     *
     * @param id ID do usuário.
     * @return DTO do usuário encontrado.
     */
    public UserLoanDTO getUserById(Long id) {
        UserLoan userLoan = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        return UserLoanDTO.fromEntity(userLoan);
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param newUser Dados do usuário atualizado.
     * @return DTO do usuário atualizado.
     */
    public UserLoanDTO updateUser(@Valid UserLoan newUser) {
        validateUserExists(newUser.getId());
        UserLoan updatedUser = userRepository.save(newUser);
        return UserLoanDTO.fromEntity(updatedUser);
    }

    /**
     * Deleta um usuário pelo ID.
     *
     * @param id ID do usuário.
     */
    public void deleteUser(Long id) {
        validateUserExists(id);
        userRepository.deleteById(id);
    }

    /**
     * Busca usuários pelo nome.
     *
     * @param name Nome do usuário.
     * @return Lista de DTOs dos usuários encontrados.
     */
    public List<UserLoanDTO> getUserByName(String name) {
        List<UserLoan> users = userRepository.findByNameContaining(name);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum usuário encontrado com o nome: " + name);
        }
        return users.stream()
                .map(UserLoanDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Valida se um usuário com o ID fornecido existe no banco.
     *
     * @param id ID do usuário.
     */
    private void validateUserExists(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
    }
}
