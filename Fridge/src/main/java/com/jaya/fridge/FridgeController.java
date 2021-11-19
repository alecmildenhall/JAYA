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
  public List<Food> getFridge(@RequestBody Long userId) {
    return fridgeService.getFridge(userId);
  }

  @GetMapping(path = "/get-all")
  public List<Food> getFridgeAll() {
    return fridgeService.getFridgeAll();
  }

  @PostMapping(path = "/missing-core")
  public List<Food> missingCore(@RequestBody Long userId) {
    return fridgeService.missingCore(userId);
  }

  @DeleteMapping(path = "user/{userId}/food/{foodName}/delete")
  public Boolean deleteItem(@PathVariable Long userId, @PathVariable String foodName) {
    return fridgeService.deleteItem(userId, foodName);
  }

  @PostMapping(path = "add-user")
  public Boolean addUser(@RequestBody User user) {
    return fridgeService.addUser(user);
  }

  @DeleteMapping(path = "delete-user")
  public Boolean deleteUser(@RequestBody Long userId) {
    return fridgeService.deleteUser(userId);
  }

  @PostMapping(path = "user/{userId}/food/{foodName}/update")
  public Food updateFood(@RequestBody UpdateQuantity delta, @PathVariable Long userId, @PathVariable String foodName) {
    return fridgeService.updateFood(delta, userId, foodName);
  }
}
