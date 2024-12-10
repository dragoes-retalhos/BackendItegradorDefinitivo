package com.inventario.backend_inventario.entity.dto;

import java.math.BigDecimal;

import com.inventario.backend_inventario.entity.enums.StatusMaintenanceEnum;

public class MaintenanceUpDateDto {
    private StatusMaintenanceEnum statusMaintenance;
    private BigDecimal cost;
    private String description;

    public StatusMaintenanceEnum getStatusMaintenance() {
        return statusMaintenance;
    }

    public void setStatusMaintenance(StatusMaintenanceEnum statusMaintenance) {
        this.statusMaintenance = statusMaintenance;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
