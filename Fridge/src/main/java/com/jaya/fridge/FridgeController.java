package com.jaya.fridge;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

import com.squareup.okhttp.Response;
import org.json.simple.parser.ParseException;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/fridge")
public class FridgeController {

  private final FridgeService fridgeService;

  @Autowired
  public FridgeController(FridgeService fridgeService) {
    this.fridgeService = fridgeService;
  }

  @CrossOrigin
  @GetMapping(path = "/get-fridge/{userId}")
  public List<Food> getFridge(@PathVariable Long userId) {
    return fridgeService.getFridge(userId);
  }

  @GetMapping(path = "/get-all")
  public List<Food> getFridgeAll() {
    return fridgeService.getFridgeAll();
  }

  @GetMapping(path = "/get-user/{email}")
  public User getUser(@PathVariable String email) {
    return fridgeService.getUser(email);
  }

  @GetMapping(path = "/missing-core/{userId}")
  public List<Food> missingCore(@PathVariable Long userId) {
    return fridgeService.missingCore(userId);
  }

  @PostMapping(path = "/has-core")
  public Boolean hasCoreFood(@RequestBody Food f) {
    return fridgeService.hasCoreFood(f);
  }

  @DeleteMapping(path = "user/{userId}/food/{foodName}/delete")
  public Boolean deleteItem(@PathVariable Long userId, @PathVariable String foodName) {
    return fridgeService.deleteItem(userId, foodName);
  }

  @PostMapping(path = "add-user")
  public User addUser(@RequestBody User user) {
    return fridgeService.addUser(user);
  }

  @DeleteMapping(path = "delete-user/{userId}")
  public Boolean deleteUser(@PathVariable Long userId) {
    return fridgeService.deleteUser(userId);
  }

  @PostMapping(path = "user/{userId}/food/{foodName}/update")
  public Food updateFood(@RequestBody UpdateQuantity delta, @PathVariable Long userId, @PathVariable String foodName) {
    return fridgeService.updateFood(delta, userId, foodName);
  }

  @GetMapping(path = "get-recipe/ingredients/{ingredients}")
  public JSONArray getRecipe(@PathVariable String ingredients ) throws IOException, ParseException {
    return fridgeService.getRecipe(ingredients);
  }

}
