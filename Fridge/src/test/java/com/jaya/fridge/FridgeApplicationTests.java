package com.jaya.fridge;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.hamcrest.beans.SamePropertyValuesAs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FridgeApplicationTests {

    @Autowired 
	private FridgeService fridgeService;

    @MockBean
    private FridgeRepository fridgeRepository;

    @Test
    public void getFridgeAll(){
        when(fridgeRepository.findAll()).thenReturn(Stream
        .of(new Food(1L, "carrot", 2L, 3L), new Food(43L, "cake", 3L))
        .collect(Collectors.toList()));
        assertEquals(2, fridgeService.getFridgeAll().size());
    }
	
    @Test
    public void missingCoreTest(){
        Long userID = 1L;
        Food f = new Food(1L, "carrot", 2L, 3L);
        List<Food> l = new ArrayList<>();
        l.add(f);
        when(fridgeRepository.findUsersFridge(userID)).thenReturn(Stream
        .of(f, new Food(43L, "cake", 3L))
        .collect(Collectors.toList()));
        assertEquals(l, fridgeService.missingCore(userID));

    }

    @Test
    public void updateAddNewFoodTest(){
        Long userID = 1L;
        Food f = new Food(userID, "carrot", 2L, 3L);
        UpdateQuantity uq = new UpdateQuantity(f.getFoodQuantity(),f.getCoreQuantity());
        when(fridgeRepository.save(f)).thenReturn(f);
        assertEquals(f.toString(), fridgeService.updateFood(uq, userID, f.getFoodName()).toString());
    }

    // @Test
    // public void updateFoodTest(){
    //     fridgeService.updateFood(new UpdateQuantity(5L, 7L), 1234L, "chocolate");

    //     Optional<Food> food = this.fridgeRepository.findUsersFood("chocolate", 1234L);
    //     assertEquals(food.toString(), fridgeService.updateFood(uq, userID, f.getFoodName()).toString());
    //     // Long userID = 1L;
    //     // Food f = new Food(userID, "carrot", 2L, 3L);
    //     // UpdateQuantity uq = new UpdateQuantity(f.getFoodQuantity(),f.getCoreQuantity());
    //     // when(fridgeRepository.findUsersFridge(userID)).thenReturn(Stream
    //     // .of(new Food(userID, "carrot", 4L, 53L), new Food(43L, "cake", 3L))
    //     // .collect(Collectors.toList()));
    //     // doNothing().when(fridgeService.updateFood(uq, userID, f.getFoodName()));
    //     // fridgeService.updateFood(uq, userID, f.getFoodName());
    //     // assertEquals(f.toString(), fridgeService.updateFood(uq, userID, f.getFoodName()).toString());
    // }

        
}

