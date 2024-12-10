package com.inventario.backend_inventario.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.inventario.backend_inventario.entity.Attachment;
import com.inventario.backend_inventario.entity.LaboratoryItem;
import com.inventario.backend_inventario.entity.Maintenance;
import com.inventario.backend_inventario.entity.dto.AttachmentDto;
import com.inventario.backend_inventario.entity.enums.AttachmentTypeEnum;
import com.inventario.backend_inventario.repository.AttachmentRepository;
import com.inventario.backend_inventario.repository.LaboratoryItemRepository;
import com.inventario.backend_inventario.repository.MaintenanceRepository;
import com.inventario.backend_inventario.util.FileValidator;
import com.inventario.backend_inventario.util.ResourceNotFoundException;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private LaboratoryItemRepository laboratoryItemRepository;

    private static final String UPLOAD_DIR = System.getenv("UPLOAD_DIR");

    public AttachmentDto saveAttachment(MultipartFile file, Long id, String description, AttachmentTypeEnum type)
            throws IOException {

        FileValidator.validateFile(file);

        if (type == AttachmentTypeEnum.MAINTENANCE) {
            validateMaintenanceExists(id);
        } else if (type == AttachmentTypeEnum.ITEM) {
            validateLaboratoryItemExists(id);
        }

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String filePath = uploadPath.resolve(originalFilename).toString();
        Files.copy(file.getInputStream(), Paths.get(filePath));

        Attachment attachment = new Attachment();
        attachment.setNameAttachment(originalFilename);
        attachment.setPathAttachment(filePath);
        attachment.setTypeAttachment(file.getContentType());
        attachment.setSizeAttachment(file.getSize());
        attachment.setCreationDate(LocalDateTime.now());
        attachment.setDescription(description);

        if (type == AttachmentTypeEnum.MAINTENANCE) {
            Maintenance maintenance = maintenanceRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Manutencao not found"));
            attachment.setMaintenance(maintenance);
        } else if (type == AttachmentTypeEnum.ITEM) {
            LaboratoryItem item = laboratoryItemRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
            attachment.setLaboratoryItem(item);
        }

        Attachment savedAttachment = attachmentRepository.save(attachment);

        return AttachmentDto.convertToAttachment(savedAttachment);
    }

    public List<AttachmentDto> getAttachmentsByLaboratoryItemId(Long laboratoryItemId) {
        validateLaboratoryItemExists(laboratoryItemId);
        List<Attachment> attachments = attachmentRepository.findByLaboratoryItemId(laboratoryItemId);

        return AttachmentDto.convertToAttachmentDtos(attachments);
    }

    public Path getAttachmentFile(Long attachmentId) throws IOException {

        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found"));

        Path filePath = Paths.get(attachment.getPathAttachment());

        if (!Files.exists(filePath)) {
            throw new IOException("File not found on server");
        }

        return filePath;
    }

    private void validateLaboratoryItemExists(Long laboratoryItemId) {
        if (!laboratoryItemRepository.existsById(laboratoryItemId)) {
            throw new ResourceNotFoundException("Item não encontrado com ID: " + laboratoryItemId);
        }
    }

    private void validateMaintenanceExists(Long maintenanceId) {
        if (!maintenanceRepository.existsById(maintenanceId)) {
            throw new ResourceNotFoundException("Manutencao não encontrada com ID: " + maintenanceId);
        }
    }
}
