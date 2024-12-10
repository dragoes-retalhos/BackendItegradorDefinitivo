package com.inventario.backend_inventario.entity.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.inventario.backend_inventario.entity.Loan;
import com.inventario.backend_inventario.entity.enums.StatusUserAndLoanEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemLoanDTO {

    private long loanId; // ID do empréstimo
    private LocalDateTime loanDate;
    private LocalDate expectedReturnDate;
    private LocalDateTime returnDate;
    private StatusUserAndLoanEnum status;

    private String userName; // Nome do usuário
    private String userLoanName; // Nome do UserLoan

    public static ItemLoanDTO fromEntity(Loan loan) {
        return new ItemLoanDTO(
            loan.getId(),
            loan.getLoanDate(),
            loan.getExpectedReturnDate(),
            loan.getReturnDate(),
            loan.getStatus(),
            loan.getUser().getName(), // Nome do usuário
            loan.getUserLoan().getName() // Nome do UserLoan
        );
    }
}
