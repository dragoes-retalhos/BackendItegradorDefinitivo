package com.inventario.backend_inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.backend_inventario.entity.Attachment;


public interface AttachmentRepository extends JpaRepository<Attachment, Long>{
   
    List<Attachment> findByLaboratoryItemId(Long laboratoryItemId);
}
