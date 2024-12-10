package com.inventario.backend_inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.backend_inventario.entity.UserLoan;

public interface UserLoanRepository extends JpaRepository<UserLoan, Long> {
    List<UserLoan> findByNameContaining(String name);
}
