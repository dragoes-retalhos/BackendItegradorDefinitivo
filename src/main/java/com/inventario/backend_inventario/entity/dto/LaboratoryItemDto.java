package com.inventario.backend_inventario.entity.dto;

import java.time.LocalDate;

import com.inventario.backend_inventario.entity.enums.CategoryItemEnum;
import com.inventario.backend_inventario.entity.enums.StatusItemEnum;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaboratoryItemDto {

    private Long id;
    private String nameItem;
    private String brand;
    private String model;
    private String serialNumber;
    private String invoiceNumber;
    private LocalDate entryDate;
    private LocalDate nextCalibration;
    private StatusItemEnum status;
    private CategoryItemEnum category;

    public LaboratoryItemDto() {
    }

    public LaboratoryItemDto(Long id, String nameItem, String brand, String model, String serialNumber,
            String invoiceNumber, LocalDate entryDate, LocalDate nextCalibration, StatusItemEnum status,
            CategoryItemEnum category) {
        this.id = id;
        this.nameItem = nameItem;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.invoiceNumber = invoiceNumber;
        this.entryDate = entryDate;
        this.nextCalibration = nextCalibration;
        this.status = status;
        this.category = category;
    }

}
