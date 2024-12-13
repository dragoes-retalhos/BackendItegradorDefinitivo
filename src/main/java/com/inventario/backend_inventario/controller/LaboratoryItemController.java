package com.inventario.backend_inventario.controller;

import java.util.List;

import com.inventario.backend_inventario.entity.enums.StatusItemEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.inventario.backend_inventario.entity.LaboratoryItem;
import com.inventario.backend_inventario.entity.dto.LaboratoryItemDto;
import com.inventario.backend_inventario.entity.dto.ListBrandModelDto;
import com.inventario.backend_inventario.entity.dto.ListItemViewDto;
import com.inventario.backend_inventario.service.LaboratoryItemService;
import com.inventario.backend_inventario.util.ApiErrorResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/item")
@Validated
public class LaboratoryItemController {

    @Autowired
    private LaboratoryItemService laboratoryItemService;


    @GetMapping("/dynamiclist")
    public ResponseEntity<Object> dynamicList() {
        try {
            List<ListItemViewDto> listItem = laboratoryItemService.dynamicList();
            if (listItem.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(listItem);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Erro ao buscar itens",
                    "/api/item/dynamiclist");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/by-name/{nameItem}")
    public ResponseEntity<Object> getItemsByName(@PathVariable String nameItem) {
        if (nameItem == null || nameItem.isEmpty()) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Bad Request",
                    "O nome do item não pode ser nulo ou vazio.",
                    "/api/item/by-name/" + nameItem);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            List<LaboratoryItemDto> items = laboratoryItemService.getItemsByName(nameItem);
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    e.getMessage(),
                    "/api/item/by-name/" + nameItem);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Erro ao buscar o item",
                    "/api/item/by-name/" + nameItem);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable Long id) {
        try {
            LaboratoryItemDto laboratoryItemDto = laboratoryItemService.getItemById(id);
            return ResponseEntity.ok(laboratoryItemDto);
        } catch (RuntimeException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    e.getMessage(),
                    "/api/item/" + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Erro ao buscar o item",
                    "/api/item/" + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/brand-models")
    public ResponseEntity<Object> getBrandModels() {
        try {
            ListBrandModelDto brandModels = laboratoryItemService.getAllBrandModelsFromView();
            return ResponseEntity.ok(brandModels);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Erro ao buscar marcas e modelos",
                    "/api/item/brand-models");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@Valid @RequestBody LaboratoryItem laboratoryItem) {
        try {
            laboratoryItemService.createItem(laboratoryItem); 
            return ResponseEntity.status(HttpStatus.CREATED).body("Item criado com sucesso.");
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Erro interno no servidor",
                    "/api/item"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable Long id, @Valid @RequestBody LaboratoryItem laboratoryItem) {
        try {
            laboratoryItem.setId(id);
            laboratoryItemService.updateItem(laboratoryItem);
            return ResponseEntity.ok("Item atualizado com sucesso.");
        } catch (RuntimeException e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Not Found",
                    e.getMessage(),
                    "/api/item/" + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Erro ao atualizar o item",
                    "/api/item/" + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getItemsByStatus(@PathVariable StatusItemEnum status) {
        try {
            List<LaboratoryItemDto> items = laboratoryItemService.getItemsByStatus(status);
            if (items.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    "Erro ao buscar itens com status " + status,
                    "/api/item/status/" + status);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}