package com.melnikovsavva.shop.goods;

import com.melnikovsavva.shop.rest.dto.cake.Cake;
import com.melnikovsavva.shop.rest.dto.cake.Cakes;

public interface CakeService {
    Cakes getCakes();
    Cake getCake(Long requestId);
}
