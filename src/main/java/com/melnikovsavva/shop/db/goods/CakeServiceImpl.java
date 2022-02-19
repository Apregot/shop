package com.melnikovsavva.shop.db.goods;

import com.melnikovsavva.shop.exception.CakeNotFoundException;
import com.melnikovsavva.shop.rest.dto.cake.Cake;
import com.melnikovsavva.shop.rest.dto.cake.Cakes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

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
            newCake.setDescription(c.getDescription());
            return newCake;
        }).collect(Collectors.toList());
        Cakes cakes = new Cakes();
        cakes.setCakeList(cakeList);
        return cakes;
    }

    @Override
    public List<Cake> getSomeCake(Integer page, Integer size) {
        Pageable limit = PageRequest.of(page, size);
        List<CakeEntity> cakeEntities = cakeRepository.findAll(limit).toList();
        return cakeEntities.stream().map(ce -> {
            Cake cake = new Cake();
            cake.setWeight(ce.getWeight());
            cake.setCalories(ce.getCalories());
            cake.setPrice(ce.getPrice());
            cake.setImage(ce.getImage());
            cake.setName(ce.getName());
            cake.setId(ce.getId());
            return cake;
        }).collect(Collectors.toList());
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
                    cake.setImage(cakeEntity.getImage());
                    cake.setDescription(cakeEntity.getDescription());
                    return cake;
                })
                .orElseThrow(() -> new CakeNotFoundException("No such cake"));
    }

    @Override
    public void addCake(Cake cake) {
        CakeEntity cakeEntity = new CakeEntity();
        cakeEntity.setName(cake.getName());
        cakeEntity.setImage(cake.getImage());
        cakeEntity.setWeight(cake.getWeight());
        cakeEntity.setDescription(cake.getDescription());
        cakeEntity.setPrice(cake.getPrice());
        cakeEntity.setCalories(cake.getCalories());
        cakeRepository.saveAndFlush(cakeEntity);
    }

    @Override
    public void updateCake(Cake cake) {
        CakeEntity ce = cakeRepository.getById(cake.getId());
        ce.setCalories(cake.getCalories());
        ce.setImage(cake.getImage());
        ce.setPrice(cake.getPrice());
        ce.setWeight(cake.getWeight());
        ce.setName(cake.getName());
        cakeRepository.save(ce);
    }
}
