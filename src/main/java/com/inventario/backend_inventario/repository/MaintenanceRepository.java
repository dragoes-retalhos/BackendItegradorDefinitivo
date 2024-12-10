package com.inventario.backend_inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventario.backend_inventario.entity.Maintenance;



public interface MaintenanceRepository extends JpaRepository<Maintenance, Long>{
    
    @Query("SELECT m FROM Maintenance m " +
    "LEFT JOIN FETCH m.attachments a " +
    "WHERE m.laboratoryItem.id = :laboratoryItemId")
    List<Maintenance> findByLaboratoryItemId(Long laboratoryItemId);


}
