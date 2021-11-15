package com.jaya.fridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class FridgeService {

  private final FridgeRepository fridgeRepository;
  private final UserRepository userRepository;

  @Autowired
  public FridgeService(FridgeRepository fridgeRepository, UserRepository userRepository) {
    this.fridgeRepository = fridgeRepository;
    this.userRepository = userRepository;
  }

  public List<Food> getFridge(Long userID) {
    return fridgeRepository.findUsersFridge(userID);
  }

  public List<Food> getFridgeAll() {
    return fridgeRepository.findAll();
  }

  public Food updateFood(Food food) {
    //checks if the user already has the item in their fridge
    Optional<Food> usersFood = fridgeRepository
        .findUsersFood(food.getFoodName(), food.getUserId());
    //if they do, update it but don't add new item
    if (usersFood.isPresent()) {
      Food foodItem = usersFood.get();
      //if they haven't specified a core quantity or foodQuantity, keep it the same
      Long core = food.getCoreQuantity();
      Long foodQuan = food.getFoodQuantity();
      if (core == null) {
        food.setCoreQuantity(0L);
        //if the core hasn't been set yet in the db, make it 0
        if(foodItem.getCoreQuantity() == null){
          core = 0L;
          foodItem.setCoreQuantity(0L);
        }
         //else make core the original quantity in the db
        else{
          core = foodItem.getCoreQuantity();
        }
      }
      if (foodQuan == null){
        food.setFoodQuantity(0L);
        //if the food quantity hasn't been set yet in the db, make foodQuan 0
        if(foodItem.getFoodQuantity() == null){
          foodQuan = 0L;
          foodItem.setFoodQuantity(0L);
        }
        //else make foodQuan the original quantity in the db
        else{
          foodQuan = foodItem.getFoodQuantity();
        }
      }
    
      //update the quantity of the item
      fridgeRepository.updateUsersFood(foodQuan, core, foodItem.getRowId());
      System.out.println(food.getFoodQuantity());
      System.out.println(core);
      System.out.println(foodItem.getRowId());

      Food f = new Food(foodItem.getRowId(), food.getUserId(), food.getFoodName(),
          food.getFoodQuantity() + foodItem.getFoodQuantity(), 
          food.getCoreQuantity() + foodItem.getCoreQuantity());
      System.out.println(f.toString());
      System.out.println(hasCoreFood(f));
      return f;
      
    } 
    else {
      fridgeRepository.save(food);
      System.out.println(hasCoreFood(food));
      return food;
    }
  }

  public Food addCoreItem(Food food) {
    //checks if the user already has the item in their fridge
    Optional<Food> usersFood = fridgeRepository
        .findUsersFood(food.getFoodName(), food.getUserId());
    //if they do, update the core quantity and don't change anything else
    if (usersFood.isPresent()) {
      Food foodItem = usersFood.get();
      //if they haven't specified a food quantity then keep it the same
      Long foodQuan = food.getFoodQuantity();
      if (foodQuan == null) {
        foodQuan = 0L;
      }
      //update the quantity of the item
      fridgeRepository.updateUsersFood(foodQuan, food.getCoreQuantity(), foodItem.getRowId());
      Food f = new Food(food.getRowId(), food.getUserId(), food.getFoodName(),
          foodQuan, food.getCoreQuantity());
      System.out.println(hasCoreFood(f));
      return f;

    }
    //saves the food with the specifications given
    else {
      fridgeRepository.save(food);
      System.out.println(hasCoreFood(food));
      return food;
    }
  }

  //returns a list of foods that are below their core quantity amount
  public List<Food> missingCore(Long userID) {
    //gets all food in the user's fridge
    List<Food> usersFridge = fridgeRepository.findUsersFridge(userID);
    List<Food> missingCore = new ArrayList<>();
    //calls hasCoreFood for each food
    for (Food f : usersFridge) {
      if (hasCoreFood(f) == false) {
        missingCore.add(f);
      }
    }
    return missingCore;
  }

  //returns false if food is below core quantity
  public Boolean hasCoreFood(Food f) {
    if (f.getCoreQuantity() == null) {
      return true;
    } else if (f.getFoodQuantity() < f.getCoreQuantity()) {
      return false;
    }
    return true;
  }

  /*
  Deletes desired amount of food item
  Input: food to delete, where foodQuantity = desired amount to delete
  Return: Boolean, error messages if impossible request
   */
  public Boolean deleteFoodItem(Food food) {
    // Check if the user has the item in their fridge
    Optional<Food> usersFood = fridgeRepository
        .findUsersFood(food.getFoodName(), food.getUserId());
    // If food exists, remove desired amount from food quantity if possible
    if (usersFood.isPresent()) {
      Food foodItem = usersFood.get();
      Long currentQuantity = foodItem.getFoodQuantity();
      Long removedQuantity = food.getFoodQuantity();

      // Update food quantity if possible
      if (currentQuantity >= removedQuantity) {
        currentQuantity = foodItem.getFoodQuantity() - removedQuantity;
      } else {
        System.out.println("Not enough food to delete desired quantity");
        return false;
      }

      // Save updated food quantity to database
      fridgeRepository.setUsersFood(currentQuantity, foodItem.getRowId());
      System.out.println(foodItem.toString());
    } else {
      System.out.println("Food item does not exist");
      return false;
    }
    return true;
  }

  /*
  Deletes desired amount of core food item
  Input: core food to delete, where coreQuantity = desired amount to delete
  Return: nothing, error messages if impossible request
   */
  public Boolean deleteCoreItem(Food food) {
    // Check if the user has the item in their fridge
    Optional<Food> usersFood = fridgeRepository
        .findUsersFood(food.getFoodName(), food.getUserId());
    // If food exists, remove desired amount from food quantity if possible
    if (usersFood.isPresent()) {
      Food foodItem = usersFood.get();
      Long currentQuantity = foodItem.getCoreQuantity();
      if (currentQuantity == null) {
        System.out.println("Food item has no core quantity");
        return false;
      }
      Long removedQuantity = food.getCoreQuantity();

      // Update food quantity if possible
      if (currentQuantity >= removedQuantity) {
        currentQuantity = foodItem.getCoreQuantity() - removedQuantity;
      } else {
        System.out.println("Not enough of core amount to delete desired quantity");
        return false;
      }

      // Save updated food quantity to database
      fridgeRepository.setUsersCore(currentQuantity, foodItem.getRowId());
      System.out.println(foodItem.toString());
    } else {
      System.out.println("Core item does not exist");
      return false;
    }
    return true;
  }

  public Boolean addUser(User user) {
    // checks if the user already exists
    List<User> users = userRepository
        .findUser(user.getUserId());
    // if they don't, add user
    if (users.size() == 0) {

      // add user
      userRepository.save(user);
    } else {
      System.out.println("user already exists");
      return false;
    }
    return true;
  }

  public Boolean deleteUser(Long userID) {
    userRepository.deleteUser(userID);
    fridgeRepository.deleteUserFood(userID);
    return true;
  }
}