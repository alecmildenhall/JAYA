package com.jaya.fridge;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FridgeController.class)
public class FridgeControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc = MockMvcBuilders.webAppContextSetup(context).build();

    ObjectMapper om = new ObjectMapper();

    
    @Test
	public void addFoodItemTest() throws Exception{
        Food food = new Food();
        food.setUserId(1L);
        food.setFoodName("cake");
        food.setFoodQuantity(1L);
        food.setCoreQuantity(1L);
        String jsonRequest = om.writeValueAsString(food);
        MvcResult result = mvc.perform(post("api/v1/fridge/add-food")
        .content(jsonRequest).content(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        assertEquals(resultContent, "true");
    
    }

    @Test
	public void getFridgeTest() throws Exception{
        MvcResult result = mvc.perform(get("api/v1/fridge/get-fridge")
        .content(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        assertEquals(resultContent, "true");
    
    }
        
}

