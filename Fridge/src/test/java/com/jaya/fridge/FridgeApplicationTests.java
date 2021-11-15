// package com.jaya.fridge;


// import com.fasterxml.jackson.databind.ObjectMapper;

// import org.junit.runner.RunWith;
// import org.junit.Before;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;

// @RunWith(SpringRunner.class)
// @SpringBootTest
// public class FridgeApplicationTests {

//     @Autowired 
// 	private FridgeService fridgeService;

//     @MockBean
//     private FridgeRepository fridgeRepository;

//     @Test
//     public void getFridgeAll(){
//         when(fridgeRepository.findAll()).thenReturn(Stream
//         .of(new Food(1L, "carrot", 2L, 3L), new Food(43L, "cake", 3L))
//         .collect(Collectors.toList()));
//         assertEquals(2, fridgeService.getFridgeAll().size());
//     }
	
//     @Test
//     public void missingCoreTest(){
//         Long userID = 1L;
//         Food f = new Food(1L, "carrot", 2L, 3L);
//         List<Food> l = new ArrayList<>();
//         l.add(f);
//         when(fridgeRepository.findUsersFridge(userID)).thenReturn(Stream
//         .of(f, new Food(43L, "cake", 3L))
//         .collect(Collectors.toList()));
//         assertEquals(l, fridgeService.missingCore(userID));

//     }

//     // @Test
//     // public void addFoodItemTest(){
//     //     Long userID = 1L;
//     //     Food f = new Food(1L, "carrot", 2L, 3L);
//     //     when(fridgeRepository.findUsersFridge(userID)).thenReturn(Stream
//     //     .of(new Food(3L, "water", 4L, 53L), new Food(43L, "cake", 3L))
//     //     .collect(Collectors.toList()));
//     //     when(fridgeRepository.save(f)).thenReturn(f);
//     //     assertEquals(f, fridgeService.addFoodItem(f));

//     // }

//     @Test
//     public void updateFoodItemTest(){
//         Long userID = 1L;
//         Food f = new Food(1L, "carrot", 2L, 3L);
//         when(fridgeRepository.findUsersFridge(userID)).thenReturn(Stream
//         .of(new Food(3L, "carrot", 4L, 53L), new Food(43L, "cake", 3L))
//         .collect(Collectors.toList()));
//         fridgeRepository.updateUsersFood(
//             f.getFoodQuantity(), 
//             f.getCoreQuantity(),
//             f.getUserId());
//         assertEquals(f, fridgeService.addFoodItem(f));
//     }

//     @Test
//     public void addCoreItemTest(){
//         Long userID = 1L;
//         Food f = new Food(1L, "carrot", 2L, 3L);
//         when(fridgeRepository.findUsersFridge(userID)).thenReturn(Stream
//         .of(new Food(3L, "water", 4L, 53L), new Food(43L, "cake", 3L))
//         .collect(Collectors.toList()));
//         when(fridgeRepository.save(f)).thenReturn(f);
//         assertEquals(f, fridgeService.addCoreItem(f));

//     }

//     @Test
//     public void updateCoreItemTest(){
//         Long userID = 1L;
//         Food f = new Food(1L, "carrot", 2L, 3L);
//         when(fridgeRepository.findUsersFridge(userID)).thenReturn(Stream
//         .of(new Food(3L, "carrot", 4L, 53L), new Food(43L, "cake", 3L))
//         .collect(Collectors.toList()));
//         fridgeRepository.updateUsersFood(
//             f.getFoodQuantity(), 
//             f.getCoreQuantity(),
//             f.getUserId());
//         assertEquals(f, fridgeService.addFoodItem(f));
//     }




        
// }

