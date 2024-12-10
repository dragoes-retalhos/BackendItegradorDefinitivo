package com.inventario.backend_inventario.util;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "application/pdf");
    private static final List<String> ALLOWED_EXTENSIONS = List.of(".jpg", ".jpeg", ".pdf");
    private static final long MAX_SIZE = 5 * 1024 * 1024; // 5 MB

    public static void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo está vazio ou não foi enviado.");
        }

        // Validação de tipo MIME
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Tipo de arquivo não suportado. Apenas JPG e PDF são permitidos.");
        }

        // Validação de extensão
        String fileName = file.getOriginalFilename();
        if (fileName == null || ALLOWED_EXTENSIONS.stream().noneMatch(fileName.toLowerCase()::endsWith)) {
            throw new IllegalArgumentException("Extensão de arquivo inválida. Apenas JPG e PDF são permitidos.");
        }

        // Validação de tamanho
        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("Arquivo excede o tamanho máximo permitido de 5 MB.");
        }
    }
}
