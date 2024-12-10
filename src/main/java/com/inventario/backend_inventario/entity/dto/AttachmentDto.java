package com.inventario.backend_inventario.entity.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.inventario.backend_inventario.entity.Attachment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttachmentDto {

    private Long id;
    private String nameAttachment;
    private String pathAttachment;
    private String typeAttachment;
    private Long sizeAttachment;
    private LocalDateTime creationDate;
    private String description;

    public AttachmentDto() {
    }

    public AttachmentDto(Long id, String nameAttachment, String pathAttachment, String typeAttachment,
            Long sizeAttachment, LocalDateTime creationDate, String description) {
        this.id = id;
        this.nameAttachment = nameAttachment;
        this.pathAttachment = pathAttachment;
        this.typeAttachment = typeAttachment;
        this.sizeAttachment = sizeAttachment;
        this.creationDate = creationDate;
        this.description = description;
    }

    public static AttachmentDto convertToAttachment(Attachment attachment) {
        return new AttachmentDto(
                attachment.getId(),
                attachment.getNameAttachment(),
                attachment.getPathAttachment(),
                attachment.getTypeAttachment(),
                attachment.getSizeAttachment(),
                attachment.getCreationDate(),
                attachment.getDescription());
    }

    public static List<AttachmentDto> convertToAttachmentDtos(List<Attachment> attachments) {
        return attachments.stream()
                .map(AttachmentDto::convertToAttachment)
                .collect(Collectors.toList());
    }
}
