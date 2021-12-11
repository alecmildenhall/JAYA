package com.jaya.fridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.*;
import java.io.IOException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


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
    System.out.println(userId);
    return fridgeRepository.findUsersFridge(userId);
  }

  public List<Food> getFridgeAll() {
    return fridgeRepository.findAll();
  }

  public User getUser(String email){
    System.out.println(email);
    Optional<User> userByEmail = fridgeRepository.getUser(email);
    if (userByEmail.isPresent()) {
      User user =  userByEmail.get();
      return user;
    }
    else{
      System.out.println("Error. User does not exist");
      return new User(-1L);
    }
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

  public User addUser(User user) {
    // checks if the user already exists
    List<User> users = userRepository
        .findUser(user.getUserId());

    // check if email already exists
    List<User> emails = userRepository
            .findEmail(user.getEmail());

    // if the email and user doesn't exist, add user
    if (users.size() == 0 && emails.size() == 0) {
      // add user
      userRepository.save(user);
    } else if (users.size() != 0) {
        System.out.println("user already exists");
        return new User(-1L);
    } else {
        System.out.println("email already exists");
        return new User(-1L);
    }
    return user;
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

    // check user exists in user table before adding food
    List<User> user = userRepository.
            findUser(userId);

    // if user does not exist in table, return
    if (user.size() == 0) {
      System.out.println("User does not exist in user table");
      return null;
    }

    //makes the food name all lowercase
    foodName = foodName.toLowerCase();

    Optional<Food> usersFood = fridgeRepository
        .findUsersFood(foodName, userId);
    // update the quantity but don't add new item is food is already present
    if (usersFood.isPresent()) {
      Food foodItem = usersFood.get();

      // update the quantity of the item
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
      if (f.getFoodQuantity() <= 0 && f.getCoreQuantity() <= 0){
        deleteItem(f.getUserId(), f.getFoodName());
      }
      System.out.println(hasCoreFood(f));
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

public JSONArray getRecipe(String ingredients) throws IOException, ParseException{
  OkHttpClient client = new OkHttpClient();
   String url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?ingredients=";
   url += ingredients.replace(",", "%2C");
   url += "&number=5&ignorePantry=true&ranking=1";

  Request request = new Request.Builder()
    .url(url)
    .get()
    .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
    .addHeader("x-rapidapi-key", "baff636bb0msh6c837292fc1a057p1d43bejsn20d37ef436f6")
    .build();

  Response response = client.newCall(request).execute();
  JSONParser parser = new JSONParser();
  String recipe = response.body().string();
  JSONArray json = (JSONArray) parser.parse(recipe);
  for(int i = 0; i < json.size(); i++){
		JSONObject obj = (JSONObject)json.get(i);
    System.out.println(obj.get("title".toString()));
  }
  return json;
}


public JSONObject getRecipeLink(Long recipeId) throws IOException, ParseException{
  OkHttpClient client = new OkHttpClient();
   String url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/";
   url += recipeId.toString();
   url += "/information";

   Request request = new Request.Builder()
   .url(url)
   .get()
   .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
   .addHeader("x-rapidapi-key", "baff636bb0msh6c837292fc1a057p1d43bejsn20d37ef436f6")
   .build();
 
  Response response = client.newCall(request).execute();

  JSONParser parser = new JSONParser();
  String recipeLink = response.body().string();
  JSONObject json = (JSONObject) parser.parse(recipeLink);
  System.out.println(json.get("sourceUrl".toString()));
  return json;
}
}
