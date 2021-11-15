package com.jaya.fridge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class FridgeControllerTests {
  @Test
  void helloWorldTest(@Autowired MockMvc mvc) throws Exception {
    mvc.perform(get("/api/v1/fridge/hello"))
        .andExpect(status().isOk())
        .andExpect(content().string("Hello World!"));
  }

  @Test
  void updateFoodTest(@Autowired MockMvc mvc) throws Exception {
    mvc.perform(post("/api/v1/fridge/user/1234/food/cherry/update")
            .content("{\"deltaFoodQuantity\": 42, \"newCoreQuantity\": 5}")
            .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"userId\": 1234, \"foodQuantity\": 42, \"coreQuantity\": 5, \"foodName\": \"cherry\"}"));
  }
}
