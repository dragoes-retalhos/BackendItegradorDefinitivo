package com.inventario.backend_inventario.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListBrandModelDto {
    private List<String> brands;
    private List<String> models;

    public ListBrandModelDto() {
    }

    public ListBrandModelDto(List<String> brands, List<String> models) {
        this.brands = brands;
        this.models = models;
    }
}
