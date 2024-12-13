package com.inventario.backend_inventario.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.inventario.backend_inventario.entity.enums.StatusItemEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventario.backend_inventario.entity.LaboratoryItem;
import com.inventario.backend_inventario.entity.dto.LaboratoryItemDto;
import com.inventario.backend_inventario.entity.dto.ListBrandModelDto;
import com.inventario.backend_inventario.entity.dto.ListItemViewDto;
import com.inventario.backend_inventario.repository.LaboratoryItemRepository;
import com.inventario.backend_inventario.util.ResourceNotFoundException;

@Service
public class LaboratoryItemService {

    @Autowired
    private LaboratoryItemRepository laboratoryItemRepository;

    public List<ListItemViewDto> dynamicList() {
        List<Object[]> rawResults = laboratoryItemRepository.findAllListItems();

        return rawResults.stream()
                .map(result -> new ListItemViewDto(
                        (String) result[0],
                        ((Number) result[2]).longValue(),
                        (String) result[1]))
                .collect(Collectors.toList());
    }

    public List<LaboratoryItemDto> getItemsByName(String nameItem) {

        validateLaboratoryItemExistsByName(nameItem);

        return laboratoryItemRepository.findAllByNameItem(nameItem)
                .stream()
                .map(item -> new LaboratoryItemDto(
                        item.getId(),
                        item.getNameItem(),
                        item.getBrand(),
                        item.getModel(),
                        item.getSerialNumber(),
                        item.getInvoiceNumber(),
                        item.getEntryDate(),
                        item.getNextCalibration(),
                        item.getStatus(),
                        item.getCategory()))
                .collect(Collectors.toList());
    }

    public LaboratoryItemDto getItemById(Long id) {
        LaboratoryItem item = laboratoryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com ID: " + id));

        return new LaboratoryItemDto(
                item.getId(),
                item.getNameItem(),
                item.getBrand(),
                item.getModel(),
                item.getSerialNumber(),
                item.getInvoiceNumber(),
                item.getEntryDate(),
                item.getNextCalibration(),
                item.getStatus(),
                item.getCategory());
    }

    public LaboratoryItem createItem(LaboratoryItem laboratoryItem) {
        return laboratoryItemRepository.save(laboratoryItem);
    }

    public ListBrandModelDto getAllBrandModelsFromView() {

        List<Object[]> rawResults = laboratoryItemRepository.findAllBrandModels();

        Map<String, String> uniqueBrandModelMap = rawResults.stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0], // Chave: brand
                        result -> (String) result[1], // Valor: model
                        (existing, replacement) -> existing // Em caso de chave duplicada, manter o existente
                ));

        List<String> uniqueBrands = new ArrayList<>(uniqueBrandModelMap.keySet());
        List<String> uniqueModels = new ArrayList<>(uniqueBrandModelMap.values());

        return new ListBrandModelDto(uniqueBrands, uniqueModels);

    }

    private void validateLaboratoryItemExistsByName(String nameItem) {
        if (!laboratoryItemRepository.existsByNameItem(nameItem)) {
            throw new ResourceNotFoundException("Item não encontrado com o nome: " + nameItem);
        }
    }

    public void updateItem(LaboratoryItem laboratoryItem) {
        laboratoryItemRepository.save(laboratoryItem);
    }

    public List<LaboratoryItemDto> getItemsByStatus(StatusItemEnum status) {
        return laboratoryItemRepository.findAllByStatus(status)
                .stream()
                .map(item -> new LaboratoryItemDto(
                        item.getId(),
                        item.getNameItem(),
                        item.getBrand(),
                        item.getModel(),
                        item.getSerialNumber(),
                        item.getInvoiceNumber(),
                        item.getEntryDate(),
                        item.getNextCalibration(),
                        item.getStatus(),
                        item.getCategory()))
                .collect(Collectors.toList());
    }

}