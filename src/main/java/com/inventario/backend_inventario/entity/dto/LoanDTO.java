package com.inventario.backend_inventario.entity.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.inventario.backend_inventario.entity.Loan;
import com.inventario.backend_inventario.entity.enums.StatusUserAndLoanEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {

    private long id;
    private LocalDateTime loanDate;
    private LocalDate expectedReturnDate;
    private LocalDateTime returnDate;
    private StatusUserAndLoanEnum status;

    // IDs dos relacionamentos para evitar carregar objetos completos
    private long userId;
    private long userLoanId;
    private Set<Long> laboratoryItemIds;

    public static LoanDTO fromEntity(Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setId(loan.getId());
        dto.setLoanDate(loan.getLoanDate());
        dto.setExpectedReturnDate(loan.getExpectedReturnDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setStatus(loan.getStatus());

        // Relacionamentos como IDs
        dto.setUserId(loan.getUser().getId());
        dto.setUserLoanId(loan.getUserLoan().getId());

        // Transformar itens de laboratório em IDs
        if (loan.getLaboratoryItems() != null) {
            dto.setLaboratoryItemIds(
                loan.getLaboratoryItems().stream()
                    .map(item -> item.getId())
                    .collect(Collectors.toSet())
            );
        }

        return dto;
    }

}
