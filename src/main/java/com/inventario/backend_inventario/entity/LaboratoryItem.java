package com.inventario.backend_inventario.entity;

import java.time.LocalDate;
import java.util.List;

import com.inventario.backend_inventario.entity.enums.CategoryItemEnum;
import com.inventario.backend_inventario.entity.enums.StatusItemEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "laboratory_item")
public class LaboratoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_laboratory_item_heritage", nullable = false)
    private Long id;

    @Column(name = "name_item", nullable = false)
    private String nameItem;

    @Column(name = "brand", nullable = true)
    private String brand;

    @Column(name = "model", nullable = true)
    private String model;

    @Column(name = "serial_number", nullable = true)
    private String serialNumber;

    @Column(name = "invoice_number", nullable = true)
    private String invoiceNumber;

    @Column(name = "entry_date", nullable = true)
    private LocalDate entryDate;

    @Column(name = "next_calibration", nullable = true)
    private LocalDate nextCalibration;

    @Column(name = "Status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusItemEnum status;

    @Column(name = "category", nullable = true)
    @Enumerated(EnumType.ORDINAL)
    private CategoryItemEnum category;

    @OneToMany(mappedBy = "laboratoryItem")
    private List<Maintenance> maintenances;

    @OneToMany(mappedBy = "laboratoryItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    @ManyToMany(mappedBy = "laboratoryItems")
    private List<Loan> loans;

    public LaboratoryItem() {
    }

    

    public LaboratoryItem(Long id, String nameItem, String brand, String model, String serialNumber,
            String invoiceNumber, LocalDate entryDate, LocalDate nextCalibration, StatusItemEnum status,
            CategoryItemEnum category, List<Maintenance> maintenances, List<Attachment> attachments, List<Loan> loans) {
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
        this.maintenances = maintenances;
        this.attachments = attachments;
        this.loans = loans;
    }



    @PrePersist // DEFINI VALOR INICIAL PARA STATUS
    protected void onCreate() {
        if (status == null) {
            status = StatusItemEnum.ACTIVE;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LaboratoryItem other = (LaboratoryItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}