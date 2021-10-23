package com.melnikovsavva.shop.rest.controller;

import com.melnikovsavva.shop.dto.Cake;
import com.melnikovsavva.shop.dto.Cakes;
import com.melnikovsavva.shop.exception.CakeNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
//@RequestMapping ("v1/cakes")
public class CakeController {

    private Long availableId = 3L;
    private final Cakes cakeList = new Cakes();

    public CakeController(){
        Cake cake1 = new Cake();
        cake1.setId(1L);
        cake1.setName("Napoleon");
        cake1.setPrice(new BigDecimal(100));
        cake1.setWeight(new BigDecimal(250));
        cake1.setCalories(new BigDecimal(800));
        cake1.setImage("cake1.jpg");

        Cake cake2 = new Cake();
        cake2.setId(2L);
        cake2.setName("Rose");
        cake2.setPrice(new BigDecimal(100));
        cake2.setWeight(new BigDecimal(250));
        cake1.setCalories(new BigDecimal(800));
        cake2.setImage("cake1.jpg");

        List<Cake> tmp = new ArrayList<Cake>();
        tmp.add(cake1);
        tmp.add(cake2);

        cakeList.setCakeList(tmp);
    }

    @GetMapping(value = "cakes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cakes cakes(){
        return cakeList;
    }


    @GetMapping(value = "cake/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cake cake(@PathVariable Long id){

        return cakeList.getCakeList().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new CakeNotFoundException("Cake not found."));
    }

    @PostMapping(path = "cake",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void addCake(@Valid @RequestBody Cake newCake){
        newCake.setId(availableId);
        cakeList.getCakeList().add(newCake);
        availableId++;
    }
}
