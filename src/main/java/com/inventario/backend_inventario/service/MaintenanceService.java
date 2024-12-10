package com.inventario.backend_inventario.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventario.backend_inventario.entity.Maintenance;
import com.inventario.backend_inventario.entity.dto.AttachmentDto;
import com.inventario.backend_inventario.entity.dto.MaintenanceDTO;
import com.inventario.backend_inventario.entity.dto.MaintenanceUpDateDto;
import com.inventario.backend_inventario.repository.LaboratoryItemRepository;
import com.inventario.backend_inventario.repository.MaintenanceRepository;
import com.inventario.backend_inventario.util.ResourceNotFoundException;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private LaboratoryItemRepository laboratoryItemRepository;

    public List<MaintenanceDTO> getMaintenanceData(Long laboratoryItemId) {

        List<Maintenance> maintenances = maintenanceRepository.findByLaboratoryItemId(laboratoryItemId);

        return maintenances.stream()
                .map(maintenance -> {

                    List<AttachmentDto> attachmentDtos = AttachmentDto
                            .convertToAttachmentDtos(maintenance.getAttachments());

                    MaintenanceDTO maintenanceDTO = new MaintenanceDTO(
                            maintenance.getId(),
                            maintenance.getMaintenanceType(),
                            maintenance.getDescription(),
                            maintenance.getStatusMaintenance(),
                            maintenance.getCost(),
                            maintenance.getCreationDate(),
                            maintenance.getDeliveryDate(),
                            maintenance.getLaboratoryItem().getId(),
                            attachmentDtos);

                    return maintenanceDTO;
                })
                .collect(Collectors.toList());
    }

    public Maintenance createMaintenance(Maintenance maintenance) {
        validateLaboratoryItemExists(maintenance.getLaboratoryItem().getId());
        return maintenanceRepository.save(maintenance);
    }

    @Transactional
    public Maintenance updateMaintenance(Long maintenanceId, MaintenanceUpDateDto maintenanceUpDateDto) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Manutenção não encontrada com o ID: " + maintenanceId));

        updateFields(maintenance, maintenanceUpDateDto);
        return maintenanceRepository.save(maintenance);
    }

    private void validateLaboratoryItemExists(Long laboratoryItemId) {
        if (!laboratoryItemRepository.existsById(laboratoryItemId)) {
            throw new ResourceNotFoundException("Item não encontrado com ID: " + laboratoryItemId);
        }
    }

    private void updateFields(Maintenance maintenance, MaintenanceUpDateDto dto) {
        Optional.ofNullable(dto.getStatusMaintenance())
                .ifPresent(maintenance::setStatusMaintenance);

        Optional.ofNullable(dto.getCost())
                .ifPresent(maintenance::setCost);

        Optional.ofNullable(dto.getDescription())
                .ifPresent(maintenance::setDescription);
    }
}
