package com.jaya.fridge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class FridgeControllerTests {

  @Test
  void getFridgeAllTest(@Autowired MockMvc mvc) throws Exception{
    mvc.perform(post("/api/v1/fridge/add-user")
              .content("{\"userId\": 1, \"email\": \"test@gmail.com\", \"name\": \"Alex\"}")
              .contentType("application/json"));
    mvc.perform(post("/api/v1/fridge/user/1/food/cherry/update")
            .content("{\"deltaFoodQuantity\": 42, \"newCoreQuantity\": 5}")
            .contentType("application/json"));
    mvc.perform(get("/api/v1/fridge/get-all"))
       .andExpect(status().isOk())
       .andExpect(content().json("[{\"userId\": 1, \"foodQuantity\": 42, \"coreQuantity\": 5, \"foodName\": \"cherry\"}]"));
    }

  @Test
  void getFridgeTest(@Autowired MockMvc mvc) throws Exception{
    mvc.perform(post("/api/v1/fridge/add-user")
              .content("{\"userId\": 1, \"email\": \"test@gmail.com\", \"name\": \"Alex\"}")
              .contentType("application/json"));
    mvc.perform(post("/api/v1/fridge/user/1/food/cherry/update")
            .content("{\"deltaFoodQuantity\": 42, \"newCoreQuantity\": 5}")
            .contentType("application/json"));
    mvc.perform(get("/api/v1/fridge/get-fridge/1"))
       .andExpect(status().isOk())
       .andExpect(content().json("[{\"userId\": 1, \"foodQuantity\": 42, \"coreQuantity\": 5, \"foodName\": \"cherry\"}]"));
    }

    @Test
    void getUserTest(@Autowired MockMvc mvc) throws Exception{
        mvc.perform(post("/api/v1/fridge/add-user")
                .content("{\"userId\": 1, \"email\": \"test@gmail.com\", \"name\": \"Alex\"}")
                .contentType("application/json"));
      mvc.perform(get("/api/v1/fridge/get-user/test@gmail.com"))
         .andExpect(status().isOk())
         .andExpect(content().json("{\"userId\": 1, \"email\": \"test@gmail.com\", \"name\": \"Alex\"}"));
      }

  @Test
  void missingCoreTest(@Autowired MockMvc mvc) throws Exception{
    mvc.perform(post("/api/v1/fridge/add-user")
              .content("{\"userId\": 1, \"email\": \"test@gmail.com\", \"name\": \"Alex\"}")
              .contentType("application/json"));
    mvc.perform(post("/api/v1/fridge/user/1/food/cherry/update")
            .content("{\"deltaFoodQuantity\": 3, \"newCoreQuantity\": 40}")
            .contentType("application/json"));
    mvc.perform(get("/api/v1/fridge/missing-core/1"))
        .andExpect(status().isOk())
        .andExpect(content().json("[{\"userId\": 1, \"foodQuantity\": 3, \"coreQuantity\": 40, \"foodName\": \"cherry\"}]"));
  }

  @Test
  void hasCoreTest(@Autowired MockMvc mvc) throws Exception{
    mvc.perform(post("/api/v1/fridge/has-core")
            .content("{\"foodName\": \"apple\", \"foodQuantity\": 10, \"coreQuantity\": 40}")
            .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
  }

  @Test
  void deleteItemTest(@Autowired MockMvc mvc) throws Exception{
    mvc.perform(post("/api/v1/fridge/add-user")
              .content("{\"userId\": 1, \"email\": \"test@gmail.com\", \"name\": \"Alex\"}")
              .contentType("application/json"));
    mvc.perform(post("/api/v1/fridge/user/1/food/cherry/update")
            .content("{\"deltaFoodQuantity\": 3, \"newCoreQuantity\": 40}")
            .contentType("application/json"));
    mvc.perform(delete("/api/v1/fridge/user/1/food/cherry/delete")
            .content("")
            .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }

  @Test
  void addUserTest(@Autowired MockMvc mvc) throws Exception{
        mvc.perform(post("/api/v1/fridge/add-user")
                .content("{\"userId\": 10, \"email\": \"test@gmail.com\", \"name\": \"Alex\"}")
                .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
 }

  @Test
   void deleteUserTest(@Autowired MockMvc mvc) throws Exception{
        mvc.perform(post("/api/v1/fridge/user/add-user")
                .content("{\"userId\": 10, \"email\": \"test@gmail.com\", \"name\": \"Alex\"}")
                .contentType("application/json"));
        mvc.perform(delete("/api/v1/fridge/delete-user")
                .content("10")
                .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
   }

  @Test
   void updateFoodTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(post("/api/v1/fridge/add-user")
              .content("{\"userId\": 1, \"email\": \"test@gmail.com\", \"name\": \"Alex\"}")
              .contentType("application/json"));
        mvc.perform(post("/api/v1/fridge/user/1/food/cherry/update")
                .content("{\"deltaFoodQuantity\": 42, \"newCoreQuantity\": 5}")
                .contentType("application/json"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"userId\": 1, \"foodQuantity\": 42, \"coreQuantity\": 5, \"foodName\": \"cherry\"}"));
   }

}
