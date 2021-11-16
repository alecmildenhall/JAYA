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

  public List<Food> getFridge(Long userId) {
    return fridgeRepository.findUsersFridge(userId);
  }

  public List<Food> getFridgeAll() {
    return fridgeRepository.findAll();
  }

  //returns a list of foods that are below their core quantity amount
  public List<Food> missingCore(Long userId) {
    //gets all food in the user's fridge
    List<Food> usersFridge = fridgeRepository.findUsersFridge(userId);
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

  public Boolean deleteUser(Long userId) {
    userRepository.deleteUser(userId);
    fridgeRepository.deleteUserFood(userId);
    return true;
  }

  private long longOrNullToLong(Long x) {
    return x == null ? 0 : x;
  }

  public Food updateFood(UpdateQuantity update, Long userId, String foodName) {
    Optional<Food> usersFood = fridgeRepository
        .findUsersFood(foodName, userId);
    //if they do, update the quantity but don't add new item
    if (usersFood.isPresent()) {
      Food foodItem = usersFood.get();

      //update the quantity of the item
      System.out.println(update.getDeltaFoodQuantity());
      System.out.println(longOrNullToLong(update.getDeltaFoodQuantity()));
      fridgeRepository.updateUsersFood (
          longOrNullToLong(update.getDeltaFoodQuantity()),
          update.getNewCoreQuantity() == null ? foodItem.getCoreQuantity() : update.getNewCoreQuantity(),
          foodItem.getRowId()
      );
      Optional<Food> updatedFood = fridgeRepository
        .findUsersFood(foodName, userId);
      Food f = updatedFood.get();
      //if its quantity is 0, delete from the fridge
      if (f.getFoodQuantity() == 0 && f.getCoreQuantity() == 0){
        deleteItem(f.getUserId(), f.getFoodName());
      }
      return f;

    } else {
      Long foodQuan = longOrNullToLong(update.getDeltaFoodQuantity());
      Long core = longOrNullToLong(update.getNewCoreQuantity());
      Food newFood = new Food(userId, foodName, foodQuan, core);
      fridgeRepository.save(newFood);
      System.out.println(hasCoreFood(newFood));
      return newFood;
    }
  }

  public Boolean deleteItem(Long userId, String foodName) {
    // Check if the user has the item in their fridge
    System.out.println(userId);
    System.out.println(foodName);
    Optional<Food> usersFood = fridgeRepository
        .findUsersFood(foodName, userId);
    // If food exists, delete item
    if (usersFood.isPresent()) {
      fridgeRepository.delete(usersFood.get());
      return true;
    }
    else {
      System.out.println("Item does not exist");
      return false;
    }
  }
}