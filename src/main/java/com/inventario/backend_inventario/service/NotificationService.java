package com.inventario.backend_inventario.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.inventario.backend_inventario.entity.Notification;
import com.inventario.backend_inventario.repository.NotificationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotification() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Roda todos os dias à meia-noite
    public void checkAndCreateNotificationsLoan() {
        processNotifications(
            notificationRepository.findAllDueSoonLoanSummary(),
            "VENCIMENTO_EMPRESTIMO",
            "O empréstimo %d vence em %s"
        );
    }
    
    @Scheduled(cron = "0 0 0 * * ?") // Roda todos os dias à meia-noite
    public void checkAndCreateNotificationsItem() {
        processNotifications(
            notificationRepository.findAllDueSonnItemList(),
            "MANUTENCAO_ITEM",
            "A próxima manutenção do item %d vence em %s"
        );
    }
    
    /**
     * Processa notificações genéricas.
     *
     * @param upcomingItems Lista de objetos com dados necessários para criar notificações.
     * @param type          Tipo da notificação (e.g., "VENCIMENTO_EMPRESTIMO").
     * @param messageFormat Formato da mensagem para a notificação (e.g., "O empréstimo %d vence em %s").
     */
    private void processNotifications(List<Object[]> upcomingItems, String type, String messageFormat) {
        for (Object[] item : upcomingItems) {
            Long relatedId = ((Number) item[0]).longValue();
            LocalDate date = ((java.sql.Date) item[1]).toLocalDate();
    
            String message = String.format(messageFormat, relatedId, date);
    
            // Evita duplicação verificando se já existe uma notificação com o relatedId
            if (!notificationRepository.existsByRelatedId(relatedId)) {
                createNotification(message, type, relatedId);
            }
        }
    }
    

    /**
     * Atualiza uma notificação existente.
     *
     * @param notificationId ID da notificação a ser atualizada.
     * @param displayed      Novo status de exibição.
     * @throws EntityNotFoundException Se a notificação não for encontrada.
     */
    public void updateNotification(Long notificationId, boolean displayed) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada para o ID: " + notificationId));
        notification.setDisplayed(displayed);
        notificationRepository.save(notification);
    }

    /**
     * Busca todas as notificações de um tipo específico.
     *
     * @param type Tipo da notificação (e.g., "VENCIMENTO_EMPRESTIMO").
     * @return Lista de notificações do tipo especificado.
     */
    public List<Notification> findNotificationsByType(String type) {
        return notificationRepository.findByType(type);
    }

    /**
     * Busca notificações não exibidas de um tipo específico.
     *
     * @param type Tipo da notificação (e.g., "VENCIMENTO_EMPRESTIMO").
     * @return Lista de notificações não exibidas do tipo especificado.
     */
    public List<Notification> findUndisplayedNotificationsByType(String type) {
        return notificationRepository.findByTypeAndDisplayedFalse(type);
    }

    private void createNotification(String message, String type, Long id) {
        Notification notification = new Notification();
        notification.setRelatedId(id);
        notification.setMessage(message);
        notification.setType(type);
        notificationRepository.save(notification);
    }
}
