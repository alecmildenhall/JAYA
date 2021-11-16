package com.jaya.fridge;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
public class FridgeControllerTests {

    private MockMvc mvc;

    @InjectMocks
    private FridgeController fridgecontroller;

    // ObjectMapper om = new ObjectMapper();

    @Before
    public void setUp() throws Exception{
        mvc = MockMvcBuilders.standaloneSetup(fridgecontroller).build();
    }
    
    // @Test
	// public void addFoodItemTest() throws Exception{
    //     Food food = new Food();
    //     food.setUserId(1L);
    //     food.setFoodName("cake");
    //     food.setFoodQuantity(1L);
    //     food.setCoreQuantity(1L);
    //     String jsonRequest = om.writeValueAsString(food);
    //     MvcResult result = mvc.perform(post("/api/v1/fridge/add-food")
    //     .content(jsonRequest).content(MediaType.APPLICATION_JSON_VALUE))
    //     .andExpect(status().isOk()).andReturn();
    //     String resultContent = result.getResponse().getContentAsString();
    //     assertEquals(resultContent, "true");
    
    // }

    @Test
	public void getFridgeTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/fridge/get-fridge"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("hello"));
    
    }
        
}

