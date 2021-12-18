package com.melnikovsavva.shop.rest.advice;

import com.melnikovsavva.shop.goods.CakeService;
import com.melnikovsavva.shop.rest.controller.UserController;
import com.melnikovsavva.shop.rest.dto.cake.Cake;
import com.melnikovsavva.shop.rest.dto.cake.Cakes;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.util.AssertionErrors;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

public class CakeControllerTest {

    @Test
    void testCakes() {
        Cakes cakes = new Cakes();
        cakes.setCakeList(Collections.emptyList());
        CakeService cakeService = Mockito.mock(CakeService.class);
        Mockito.doReturn(cakes).when(cakeService).getCakes();
        UserController cakeController = new UserController(cakeService);
        Cakes cakesTest = cakeController.cakes();
        AssertionErrors.assertEquals("", cakesTest, cakes);
        Mockito.verify(cakeService, Mockito.times(1)).getCakes();
    }

    @Test
    void testGetCakeById() {
        Cake cake = new Cake();
        CakeService cakeService = Mockito.mock(CakeService.class);
        Mockito.doReturn(cake).when(cakeService).getCake(any());
        UserController cakeController = new UserController(cakeService);
        Cake cakeTest = cakeController.getCakeById(1L);
        AssertionErrors.assertEquals("", cakeTest, cake);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(cakeService, Mockito.times(1)).getCake(argumentCaptor.capture());
        AssertionErrors.assertEquals("", argumentCaptor.getValue(), 1L);
    }
}
