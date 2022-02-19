package com.melnikovsavva.shop.db.goods;

import com.melnikovsavva.shop.rest.dto.cake.Cake;
import com.melnikovsavva.shop.rest.dto.cake.Cakes;

import java.util.List;

public interface CakeService {
    Cakes getCakes();
    Cake getCake(Long requestId);
    void addCake(Cake cake);
    List<Cake> getSomeCake(Integer page, Integer size);
    void updateCake(Cake cake);
}
