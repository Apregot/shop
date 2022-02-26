package com.melnikovsavva.shop.db.orders;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsById(Long id);
    List<OrderEntity> findAllByUser_Number(String number, Pageable page);

    List<OrderEntity> findOrderEntitiesByUser_Number(String number);
}
