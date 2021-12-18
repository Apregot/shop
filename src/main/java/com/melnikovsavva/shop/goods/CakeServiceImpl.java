package com.melnikovsavva.shop.goods;

import com.melnikovsavva.shop.exception.CakeNotFoundException;
import com.melnikovsavva.shop.rest.dto.cake.Cake;
import com.melnikovsavva.shop.rest.dto.cake.Cakes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CakeServiceImpl implements CakeService {
    private final CakeRepository cakeRepository;

    @Autowired
    public CakeServiceImpl(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    @Override
    public Cakes getCakes() {
        List<CakeEntity> cakeEntityList = cakeRepository.findAll();
        List<Cake> cakeList = cakeEntityList.stream().map(c -> {
            Cake newCake = new Cake();
            newCake.setId(c.getId());
            newCake.setCalories(c.getCalories());
            newCake.setImage(c.getImage());
            newCake.setWeight(c.getWeight());
            newCake.setName(c.getName());
            newCake.setPrice(c.getPrice());
            return newCake;
        }).collect(Collectors.toList());
        Cakes cakes = new Cakes();
        cakes.setCakeList(cakeList);
        return cakes;
    }

    @Override
    public Cake getCake(Long requestId) {
        return cakeRepository.findById(requestId)
                .map(cakeEntity -> {
                    Cake cake = new Cake();
                    cake.setId(cakeEntity.getId());
                    cake.setName(cakeEntity.getName());
                    cake.setPrice(cakeEntity.getPrice());
                    cake.setCalories(cakeEntity.getCalories());
                    cake.setWeight(cakeEntity.getWeight());
                    cake.setImage(cake.getImage());
                    return cake;
                })
                .orElseThrow(() -> new CakeNotFoundException("No such cake"));
    }
}
