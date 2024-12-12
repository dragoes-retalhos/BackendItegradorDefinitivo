package com.inventario.backend_inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventario.backend_inventario.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query(value = "SELECT loan_id, loan_status, loan_date, expected_return_date, user_name, loaned_items FROM loan_summary", nativeQuery = true)
    List<Object[]> findAllLoanSummary();

    @Query("SELECT l FROM Loan l JOIN l.laboratoryItems li WHERE li.id = :itemId")
    List<Loan> findByLaboratoryItemsId(@Param("itemId") Long itemId);

  
}
