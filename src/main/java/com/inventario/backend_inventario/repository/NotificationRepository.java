package com.inventario.backend_inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventario.backend_inventario.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  
    @Query(value = "SELECT " +
    "li.id_loan, " +
    "li.expected_return_date " +
    "FROM loans_due_soon li", nativeQuery = true)
    List<Object[]> findAllDueSoonLoanSummary();

    @Query(value = "SELECT " +
    "li.item_id, " +
    "li.next_calibration " +
    "FROM items_due_for_maintenance li", nativeQuery = true)
    List <Object[]> findAllDueSonnItemList();

    boolean existsByRelatedId(Long relatedId);


     /**
     * Busca notificações por tipo.
     *
     * @param type Tipo da notificação.
     * @return Lista de notificações do tipo especificado.
     */
    List<Notification> findByType(String type);

    /**
     * Busca notificações por tipo que ainda não foram exibidas.
     *
     * @param type Tipo da notificação.
     * @return Lista de notificações não exibidas do tipo especificado.
     */
    List<Notification> findByTypeAndDisplayedFalse(String type);

}
