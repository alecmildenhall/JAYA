package com.JAYA.Fridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class FridgeService {

    private final FridgeRepository fridgeRepository;

    @Autowired
    public FridgeService(FridgeRepository fridgeRepository){
        this.fridgeRepository = fridgeRepository;
    }

    public Optional<Food> getFridge(Long userID){
        return fridgeRepository.findFridge(userID);
    }

    public void addFoodItem(Food food){
        //checks if the user already has the item in their fridge
        Optional <Food> usersFood = fridgeRepository
                .findUsersFood(food.getFoodName(), food.getUserID());
        //if they do, update the quantity but don't add new item
        if (usersFood.isPresent()){
            Food foodItem = usersFood.get();
            //if they haven't specified a core quantity, keep it the same
            Long core = food.getCoreQuantity();
            if (core == null){
                core = foodItem.getCoreQuantity();
            }
            //update the quantity of the item
            fridgeRepository.updateUsersFood(food.getFoodQuantity(), core, foodItem.getRowID());
            System.out.println(foodItem.toString());
        }
        else{
            fridgeRepository.save(food);
        }
    }

    /*
    Deletes desired amount of food item

    Input: food to delete, number of items to delete
    Return: nothing, error messages if impossible request
     */
    public void deleteFoodItem(Food food, long removedQuantity){
        // Check if the user has the item in their fridge
        Optional <Food> usersFood = fridgeRepository
                .findUsersFood(food.getFoodName(), food.getUserID());
        // If food exists, remove desired amount from food quantity if possible
        if (usersFood.isPresent()){
            Food foodItem = usersFood.get();
            Long currentQuantity = food.getFoodQuantity();

            // Update food quantity if possible
            if (currentQuantity >= removedQuantity){
                currentQuantity = foodItem.getFoodQuantity() - removedQuantity;
            } else {
                System.out.println("Not enough food to delete desired quantity");
                return;
            }

            // Save updated food quantity to database
            fridgeRepository.updateUsersFood(food.getFoodQuantity(), currentQuantity, foodItem.getRowID());
            System.out.println(foodItem.toString());
        }
        else{
            System.out.println("Food item does not exist");
        }
    }
}