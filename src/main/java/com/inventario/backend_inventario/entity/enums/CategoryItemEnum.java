package com.inventario.backend_inventario.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoryItemEnum {

    UTENSILIO("Utensílio"),
    EQUIPAMENTO("Equipamento");

    private final String descricaoPortugues;
   

    CategoryItemEnum(String descricaoPortugues) {
        this.descricaoPortugues = descricaoPortugues;
    }

    @Override
    @JsonValue
    public String toString() {
        return descricaoPortugues;
    }

}
