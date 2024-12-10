package com.inventario.backend_inventario.entity.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.inventario.backend_inventario.entity.enums.MaintenanceTypeEnum;
import com.inventario.backend_inventario.entity.enums.StatusMaintenanceEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenanceDTO {

    private Long id;
    private MaintenanceTypeEnum maintenanceType;
    private String description;
    private StatusMaintenanceEnum statusMaintenance;
    private BigDecimal cost;
    private LocalDateTime creationDate;
    private LocalDate deliveryDate;
    private Long laboratoryItemId;  
    private List<AttachmentDto> attachments;

  
    public MaintenanceDTO(Long id, MaintenanceTypeEnum maintenanceType, String description,
                          StatusMaintenanceEnum statusMaintenance, BigDecimal cost,
                          LocalDateTime creationDate, LocalDate deliveryDate, Long laboratoryItemId,
                          List<AttachmentDto> attachments) {
        this.id = id;
        this.maintenanceType = maintenanceType;
        this.description = description;
        this.statusMaintenance = statusMaintenance;
        this.cost = cost;
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
        this.laboratoryItemId = laboratoryItemId;
        this.attachments = attachments;
    }
}
