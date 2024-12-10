package com.inventario.backend_inventario.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusMaintenanceEnum {

    PENDING("PENDENTE"),
    PROCEEDING("EM ANDAMENTO"),
    COMPLETED("CONCLUÍDA");

    private final String descricaoPortugues;

    StatusMaintenanceEnum(String descricaoPortugues) {
        this.descricaoPortugues = descricaoPortugues;
    }

    @Override
    @JsonValue
    public String toString() {
        return descricaoPortugues;
    }

    public String getDescricaoPortugues() {
        return descricaoPortugues;
    }

}
