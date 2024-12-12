package com.inventario.backend_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.backend_inventario.entity.Notification;
import com.inventario.backend_inventario.service.NotificationService;
import com.inventario.backend_inventario.util.ApiErrorResponse;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    
    /**
     * Método para buscar todas as notificações.
     */
    @GetMapping
    public ResponseEntity<Object> getAllNotification() {
        try {
            notificationService.checkAndCreateNotificationsItem(); // remover antes de entrar em producao
            notificationService.checkAndCreateNotificationsLoan(); // remover antes de entrar em producao
            List<Notification> notifications = notificationService.getAllNotification();
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/notification");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Método para atualizar a exibição da notificação.
     * 
     * @param notificationId ID da notificação.
     * @param displayed Status de exibição.
     * @return ResponseEntity com status da operação.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNotification(
            @PathVariable Long id, 
            @RequestParam boolean displayed) {
        try {
            notificationService.updateNotification(id, displayed);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/notification/" + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Método para buscar notificações por tipo.
     * 
     * @param type Tipo da notificação (e.g., "VENCIMENTO_EMPRESTIMO").
     * @return Lista de notificações do tipo especificado.
     */
    @GetMapping("/type")
    public ResponseEntity<Object> getNotificationsByType(@RequestParam String type) {
        try {
            List<Notification> notifications = notificationService.findNotificationsByType(type);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/notification/type");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Método para buscar notificações não exibidas por tipo.
     * 
     * @param type Tipo da notificação (e.g., "MANUTENCAO_ITEM").
     * @return Lista de notificações não exibidas do tipo especificado.
     */
    @GetMapping("/undisplayed")
    public ResponseEntity<Object> getUndisplayedNotificationsByType(@RequestParam String type) {
        try {
            List<Notification> notifications = notificationService.findUndisplayedNotificationsByType(type);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            ApiErrorResponse errorResponse = new ApiErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal Server Error",
                    e.getMessage(),
                    "/notification/undisplayed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
