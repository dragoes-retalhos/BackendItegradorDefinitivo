package com.inventario.backend_inventario.entity.dto;

import com.inventario.backend_inventario.entity.enums.StatusUserAndLoanEnum;

import lombok.Data;

@Data
public class UpdateLoanStatusDTO {
    private StatusUserAndLoanEnum status;
}