package com.jaya.fridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "api/v1/fridge")
public class FridgeController {

  private final FridgeService fridgeService;

  @Autowired
  public FridgeController(FridgeService fridgeService) {
    this.fridgeService = fridgeService;
  }

  @GetMapping(path = "/get-fridge")
  public List<Food> getFridge(@RequestBody Long userID) {
    return fridgeService.getFridge(userID);
  }

  @GetMapping(path = "/get-all")
  public List<Food> getFridgeAll() {
    return fridgeService.getFridgeAll();
  }

  @PostMapping(path = "/update-food")
  public Food updateFood(@RequestBody Food food) {
    return fridgeService.updateFood(food);
  }

  @PostMapping(path = "/missing-core")
  public List<Food> missingCore(@RequestBody Long userID) {
    return fridgeService.missingCore(userID);
  }

  @DeleteMapping(path = "delete-food")
  public Boolean deleteFoodItem(@RequestBody Food food) {
    return fridgeService.deleteFoodItem(food);
  }

  @DeleteMapping(path = "delete-core")
  public Boolean deleteCoreItem(@RequestBody Food food) {
    return fridgeService.deleteCoreItem(food);
  }

  @PostMapping(path = "add-user")
  public Boolean addUser(@RequestBody User user) {
    return fridgeService.addUser(user);
  }

  @DeleteMapping(path = "delete-user")
  public Boolean deleteUser(@RequestBody Long userID) {
    return fridgeService.deleteUser(userID);
  }
}
