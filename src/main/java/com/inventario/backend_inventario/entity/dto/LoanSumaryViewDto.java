package com.inventario.backend_inventario.entity.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.inventario.backend_inventario.entity.enums.StatusUserAndLoanEnum;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoanSumaryViewDto {

    private Long id;

    private StatusUserAndLoanEnum status;

    private LocalDateTime loanDate;

    private LocalDate expectedReturnDate;

    private String userName;

    private String loanedItems;

    public LoanSumaryViewDto() {

    }

    public LoanSumaryViewDto(Long id, StatusUserAndLoanEnum status, LocalDateTime loanDate,
            LocalDate expectedReturnDate,
            String userName, String loanedItems) {
        this.id = id;
        this.status = status;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.userName = userName;
        this.loanedItems = loanedItems;
    }

}
