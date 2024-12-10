package com.inventario.backend_inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inventario.backend_inventario.entity.LaboratoryItem;

@Repository
public interface LaboratoryItemRepository extends JpaRepository<LaboratoryItem, Long> {

    boolean existsByNameItem(String nameItem);

    @Query("SELECT li FROM LaboratoryItem li WHERE li.nameItem = :nameItem")
    List<LaboratoryItem> findAllByNameItem(@Param("nameItem") String nameItem);
    
    @Query(value = "SELECT name_item, description, COUNT(*) AS amount " +
    "FROM laboratory_item " +
    "GROUP BY name_item, description",
    nativeQuery = true)
    List<Object[]> findAllListItems();


    @Query(value = "SELECT brand, model FROM unique_brands_models", nativeQuery = true)
    List<Object[]> findAllBrandModels();

    
}