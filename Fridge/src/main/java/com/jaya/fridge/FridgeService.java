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
  Return: nothing, error messages if impossible request
   */
  public void deleteFoodItem(Food food) {
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
        return;
      }

      // Save updated food quantity to database
      fridgeRepository.setUsersFood(currentQuantity, foodItem.getRowId());
      System.out.println(foodItem.toString());
    } else {
      System.out.println("Food item does not exist");
    }
  }

  /*
  Deletes desired amount of core food item
  Input: core food to delete, where coreQuantity = desired amount to delete
  Return: nothing, error messages if impossible request
   */
  public void deleteCoreItem(Food food) {
    // Check if the user has the item in their fridge
    Optional<Food> usersFood = fridgeRepository
        .findUsersFood(food.getFoodName(), food.getUserId());
    // If food exists, remove desired amount from food quantity if possible
    if (usersFood.isPresent()) {
      Food foodItem = usersFood.get();
      Long currentQuantity = foodItem.getCoreQuantity();
      if (currentQuantity == null) {
        System.out.println("Food item has no core quantity");
        return;
      }
      Long removedQuantity = food.getCoreQuantity();

      // Update food quantity if possible
      if (currentQuantity >= removedQuantity) {
        currentQuantity = foodItem.getCoreQuantity() - removedQuantity;
      } else {
        System.out.println("Not enough of core amount to delete desired quantity");
        return;
      }

      // Save updated food quantity to database
      fridgeRepository.setUsersCore(currentQuantity, foodItem.getRowId());
      System.out.println(foodItem.toString());
    } else {
      System.out.println("Core item does not exist");
    }
  }

  public void addUser(User user) {
    // checks if the user already exists
    List<User> users = userRepository
        .findUser(user.getUserId());
    // if they don't, add user
    if (users.size() == 0) {

      // add user
      userRepository.save(user);
    } else {
      System.out.println("user already exists");
    }
  }

  public void deleteUser(Long userID) {
    userRepository.deleteUser(userID);
    fridgeRepository.deleteUserFood(userID);
  }

  private long longOrNullToLong(Long x) {
    return x == null ? 0 : x;
  }

  public Food updateFood(UpdateQuantity delta, Long userId, String foodName) {
    Optional<Food> usersFood = fridgeRepository
        .findUsersFood(foodName, userId);
    //if they do, update the quantity but don't add new item
    if (usersFood.isPresent()) {
      Food foodItem = usersFood.get();

      //update the quantity of the item
      fridgeRepository.updateUsersFood (
          longOrNullToLong(delta.getDeltaFoodQuantity()),
          delta.getNewCoreQuantity() == null ? foodItem.getCoreQuantity() : delta.getNewCoreQuantity(),
          foodItem.getRowId()
      );


    } else {
      Food newFood = new Food(userId, foodName, delta.getDeltaFoodQuantity(), delta.getNewCoreQuantity());
      fridgeRepository.save(newFood);
      System.out.println(hasCoreFood(newFood));
    }

    Optional<Food> updatedFood = fridgeRepository
        .findUsersFood(foodName, userId);
    return updatedFood.get();

  }
}