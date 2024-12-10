package com.inventario.backend_inventario.entity.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.inventario.backend_inventario.entity.UserLoan;
import com.inventario.backend_inventario.entity.enums.StatusUserAndLoanEnum;
import com.inventario.backend_inventario.entity.enums.TypeUserLoanEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoanDTO {

    private long id;
    private String name;
    private String email;
    private String rna;
    private String enterprise;
    private String identification;
    private String phone;
    private StatusUserAndLoanEnum status;
    private TypeUserLoanEnum typeUserLoan;
    private List<LoanDTO> loans;

    public static UserLoanDTO fromEntity(UserLoan userLoan) {
        if (userLoan == null) {
            return null;
        }

        return new UserLoanDTO(
            userLoan.getId(),
            userLoan.getName(),
            userLoan.getEmail(),
            userLoan.getRna(),
            userLoan.getEnterprise(),
            userLoan.getIdentification(),
            userLoan.getPhone(),
            userLoan.getStatus(),
            userLoan.getTypeUserLoan(),
            userLoan.getLoans() != null
                ? userLoan.getLoans().stream()
                    .map(LoanDTO::fromEntity)
                    .collect(Collectors.toList())
                : null
        );
    }

   
}
