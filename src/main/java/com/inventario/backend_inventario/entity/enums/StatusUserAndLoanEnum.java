package com.inventario.backend_inventario.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;


public enum StatusUserAndLoanEnum {
    ACTIVE("ATIVO", 0),
    DISABLED("DESATIVADO", 1);

    private final String descricaoPortugues;
    private final int codigo;

    StatusUserAndLoanEnum(String descricaoPortugues, int codigo) {
        this.descricaoPortugues = descricaoPortugues;
        this.codigo = codigo;
    }

    public String getDescricaoPortugues() {
        return descricaoPortugues;
    }

    public int getCodigo() {
        return codigo;
    }

    public static StatusUserAndLoanEnum fromValue(int codigo) {
        for (StatusUserAndLoanEnum status : values()) {
            if (status.codigo == codigo) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + codigo);
    }

    @Override
    @JsonValue
    public String toString() {
        return descricaoPortugues;
    }
}
