package com.jaya.fridge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Optional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class FridgeServiceTests {

  @Autowired
  private FridgeRepository fridgeRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  void testUpdateFoodNewFood() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);

    User user = new User(1L, "test@gmail.com", "test");
    fridge.addUser(user);

    fridge.updateFood(new UpdateQuantity(5L, 7L), 1L, "chocolate");
    Optional<Food> food = this.fridgeRepository.findUsersFood("chocolate", 1L);
    Food returnedFood = food.get();
    Food checkFood = new Food(1L, "chocolate", 5L, 7L);

    assertEquals(checkFood.toString(), returnedFood.toString());
  }

  @Test
  void testUpdateFoodUpdateFoodQuantity() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);

    User user = new User(1L, "test@gmail.com", "test");
    fridge.addUser(user);

    fridge.updateFood(new UpdateQuantity(5L, 7L), 1L, "chocolate");
    fridge.updateFood(new UpdateQuantity(12L, 8L), 1L, "chocolate");
    fridge.updateFood(new UpdateQuantity(13L, 9L), 1L, "chocolate");

    Optional<Food> food = this.fridgeRepository.findUsersFood("chocolate", 1L);
    Food returnedFood = food.get();
    Food checkFood = new Food(1L, "chocolate", 30L, 9L);

    assertEquals(checkFood.toString(), returnedFood.toString());
  }

  @Test
  void testGetFridge() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);

    User user = new User(1L, "test@gmail.com", "test");
    fridge.addUser(user);

    fridge.updateFood(new UpdateQuantity(5L, 7L), 1L, "chocolate");
    fridge.updateFood(new UpdateQuantity(10L, 9L), 1L, "cherry");
    fridge.updateFood(new UpdateQuantity(15L, 8L), 1L, "apple");

    Food food1 = new Food(1L, "chocolate", 5L, 7L);
    Food food2 = new Food(1L, "cherry", 10L, 9L);
    Food food3 = new Food(1L, "apple", 15L, 8L);

    ArrayList<Food> list = new ArrayList<>();
    list.add(food1);
    list.add(food2);
    list.add(food3);

    assertEquals(list.toString(), fridge.getFridge(1L).toString());
  }

  @Test
  void testGetFridgeAll() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);

    User user1 = new User(1L, "test1@gmail.com", "test1");
    fridge.addUser(user1);

    User user2 = new User(2L, "test2@gmail.com", "test2");
    fridge.addUser(user2);

    User user3 = new User(3L, "test3@gmail.com", "test3");
    fridge.addUser(user3);

    fridge.updateFood(new UpdateQuantity(5L, 7L), 1L, "chocolate");
    fridge.updateFood(new UpdateQuantity(10L, 9L), 2L, "cherry");
    fridge.updateFood(new UpdateQuantity(15L, 8L), 3L, "apple");

    Food food1 = new Food(1L, "chocolate", 5L, 7L);
    Food food2 = new Food(2L, "cherry", 10L, 9L);
    Food food3 = new Food(3L, "apple", 15L, 8L);

    ArrayList<Food> list = new ArrayList<>();
    list.add(food1);
    list.add(food2);
    list.add(food3);

    assertEquals(list.toString(), fridge.getFridgeAll().toString());
  }

  @Test
  void testMissingCore() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);

    User user = new User(1L, "test@gmail.com", "test");
    fridge.addUser(user);

    fridge.updateFood(new UpdateQuantity(5L, 7L), 1L, "chocolate");
    fridge.updateFood(new UpdateQuantity(10L, 30L), 1L, "cherry");
    fridge.updateFood(new UpdateQuantity(15L, 8L), 1L, "apple");

    Food food1 = new Food(1L, "chocolate", 5L, 7L);
    Food food2 = new Food(1L, "cherry", 10L, 30L);

    ArrayList<Food> list = new ArrayList<>();
    list.add(food1);
    list.add(food2);

    assertEquals(list.toString(), fridge.missingCore(1L).toString());
  }

  @Test
  void testHasCore() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);

    User user = new User(1L, "test@gmail.com", "test");
    fridge.addUser(user);

    fridge.updateFood(new UpdateQuantity(5L, 7L), 1L, "chocolate");
    Optional<Food> food = this.fridgeRepository.findUsersFood("chocolate", 1L);
    Food returnedFood = food.get();

    assertEquals(false, fridge.hasCoreFood(returnedFood));

  }

  @Test
  void testAddUser() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);
    User user = new User(1L);

    assertEquals(user, fridge.addUser(user));
  }

  @Test
  void testDeleteUser() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);
    User user = new User(1234L);
    fridge.addUser(user);
    fridge.deleteUser(1234L);
    ArrayList<User> returnedList = (ArrayList<User>) userRepository.findUser(1234L);

    assertEquals(0, returnedList.size());
  }

  @Test
  void testDeleteItem() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);

    User user = new User(1L, "test@gmail.com", "test");
    fridge.addUser(user);

    fridge.updateFood(new UpdateQuantity(5L, 7L), 1L, "chocolate");
    fridge.updateFood(new UpdateQuantity(10L, 9L), 1L, "cherry");
    fridge.updateFood(new UpdateQuantity(15L, 8L), 1L, "apple");

    fridge.deleteItem(1L, "apple");

    Food food1 = new Food(1L, "chocolate", 5L, 7L);
    Food food2 = new Food(1L, "cherry", 10L, 9L);

    ArrayList<Food> list = new ArrayList<>();
    list.add(food1);
    list.add(food2);

    assertEquals(list.toString(), fridge.getFridge(1L).toString());
  }

  @Test
  void testGetRecipe() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);

    JSONArray result = fridge.getRecipe("chocolate,flour,sugar");
    ArrayList<String> actualResult = new ArrayList<>();
    for(int i = 0; i < result.size(); i++){
      JSONObject obj = (JSONObject)result.get(i);
      actualResult.add(obj.get("title").toString());
    }

    ArrayList<String> expectedResult = new ArrayList<>();
    expectedResult.add("Gâteau au Chocolat Fondant de Nathalie");
    expectedResult.add("Caramel Chocolate Shortbread");
    expectedResult.add("PAIN AU CHOCOLAT");
    expectedResult.add("Chocolate Melting Cake");
    expectedResult.add("Baci di dama");

    System.out.println("actual: " + actualResult);
    System.out.println("expected: " + expectedResult);
    assertEquals(expectedResult.toString(), actualResult.toString());
  }

  @Test
  void testGetRecipeLink() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);
    String urlResult = fridge.getRecipeLink(555451L).get("sourceUrl").toString();
    String expectedResult = "http://thecornerkitchenblog.com/gateau-au-chocolat-fondant-de-nathalie/";
    assertEquals(expectedResult, urlResult);

  }

}

