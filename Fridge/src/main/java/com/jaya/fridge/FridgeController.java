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

  @GetMapping(path = "/hello")
  public String hello() {
    return "Hello World!";
  }

  @GetMapping(path = "/get-fridge")
  public List<Food> getFridge(@RequestBody Long userID) {
    return fridgeService.getFridge(userID);
  }

  @PostMapping(path = "/missing-core")
  public List<Food> missingCore(@RequestBody Long userID) {
    return fridgeService.missingCore(userID);
  }

  @PostMapping(path = "add-user")
  public void addUser(@RequestBody User user) {
    fridgeService.addUser(user);
  }

  @DeleteMapping(path = "delete-user")
  public void deleteUser(@RequestBody Long userId) {
    fridgeService.deleteUser(userId);
  }

  @PostMapping(path = "user/{userId}/food/{foodName}/update")
  public Food updateFood(@RequestBody UpdateQuantity update, @PathVariable Long userId, @PathVariable String foodName) {
    return fridgeService.updateFood(update, userId, foodName);
  }

  @DeleteMapping(path = "user/{userId}/food/{foodName}")
  public void deleteFoodItem(@PathVariable Long userId, @PathVariable String foodName) {
    fridgeService.deleteFood(userId, foodName);
  }
}
