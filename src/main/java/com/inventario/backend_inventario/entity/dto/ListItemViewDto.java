package com.inventario.backend_inventario.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ListItemViewDto {

    
    private String nameItem;
    private Long amount;
    private String description;

    public ListItemViewDto() {
    }

    public ListItemViewDto(String nameItem, Long amount, String description) {
        this.nameItem = nameItem;
        this.amount = amount;
        this.description = description;
    }

}
