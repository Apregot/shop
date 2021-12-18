package com.melnikovsavva.shop.orders;

import com.melnikovsavva.shop.goods.CakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CakeEntity, Long> {
    boolean existsById(Long id);
}
