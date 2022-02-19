package com.melnikovsavva.shop.db.orders;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
    boolean existsById(Long id);
}
