package com.inventario.backend_inventario.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario.backend_inventario.entity.enums.AttachmentTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attachment", nullable = false)
    private Long id;
    
    @Column(name = "name_attachment", nullable = true)
    private String nameAttachment;
    
    @Column(name = "path_attachment", nullable = true)
    private String pathAttachment;
    
    @Column(name = "type_attachment", nullable = true)
    private String typeAttachment;
    
    @Column(name = "size_attachment", nullable = true)
    private Long sizeAttachment;
    
    @Column(name = "creation_date", nullable = true)
    private LocalDateTime creationDate;
    
    @Column(name = "description", nullable = true)
    private String description;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private AttachmentTypeEnum attachmentType;

    @ManyToOne
    @JoinColumn(name = "laboratory_item_id_laboratory_item_heritage", nullable = true)
    private LaboratoryItem laboratoryItem;

    @ManyToOne
    @JoinColumn(name = "maintenance_id_maintenance", nullable = true) 
    private Maintenance maintenance;

    public Attachment() {
        
    }

    public Attachment(Long id, String nameAttachment, String pathAttachment, String typeAttachment,
            Long sizeAttachment, LocalDateTime creationDate, String description,
            LaboratoryItem laboratoryItem) {
        this.id = id;
        this.nameAttachment = nameAttachment;
        this.pathAttachment = pathAttachment;
        this.typeAttachment = typeAttachment;
        this.sizeAttachment = sizeAttachment;
        this.creationDate = creationDate;
        this.description = description;
        this.laboratoryItem = laboratoryItem;
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
        Attachment other = (Attachment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}
