package com.inventario.backend_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.backend_inventario.entity.Maintenance;
import com.inventario.backend_inventario.entity.dto.MaintenanceDTO;
import com.inventario.backend_inventario.entity.dto.MaintenanceUpDateDto;
import com.inventario.backend_inventario.service.MaintenanceService;
import com.inventario.backend_inventario.util.ApiErrorResponse;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping("/{laboratoryId}")
    public ResponseEntity<Object> getMaintenanceByLaboratoryItemId(@PathVariable Long laboratoryId) {

        try {
            List<MaintenanceDTO> maintenanceList = maintenanceService.getMaintenanceData(laboratoryId);
            if (maintenanceList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(maintenanceList);
        } catch (RuntimeException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    e.getMessage(),
                    "/api/maintenance/" + laboratoryId);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Erro ao buscar os usuários",
                    "/api/maintenance/");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @PostMapping
    public ResponseEntity<Object> createMaintenance(@RequestBody Maintenance maintenance) {

        try {
            Maintenance newMaintenance = maintenanceService.createMaintenance(maintenance);
            return ResponseEntity.status(HttpStatus.CREATED).body(newMaintenance);
        } catch (RuntimeException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    e.getMessage(),
                    "/api/maintenance/" + maintenance.getLaboratoryItem().getId());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Erro ao buscar os usuários",
                    "/api/maintenance/");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/up-date/{id}")
    public ResponseEntity<Maintenance> updateMaintenance(
            @PathVariable Long id,
            @RequestBody MaintenanceUpDateDto maintenanceDTO) {

        Maintenance updatedMaintenance = maintenanceService.updateMaintenance(id, maintenanceDTO);
        return ResponseEntity.ok(updatedMaintenance);
    }

}
