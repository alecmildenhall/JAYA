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

    public List<Food> getFridge(Long userID){
        return fridgeRepository.findUsersFridge(userID);
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

            Food f = new Food(food.getRowID(), food.getUserID(), food.getFoodName(),
                            food.getFoodQuantity() + foodItem.getFoodQuantity(), core);
            System.out.println(hasCoreFood(f));
        }
        else{
            fridgeRepository.save(food);
            System.out.println(hasCoreFood(food));
        }
    }

    public void addCoreItem(Food food){
        //checks if the user already has the item in their fridge
        Optional <Food> usersFood = fridgeRepository
                .findUsersFood(food.getFoodName(), food.getUserID());
        //if they do, update the core quantity and don't change anything else
        if (usersFood.isPresent()){
            Food foodItem = usersFood.get();
            //if they haven't specified a food quantity then keep it the same
            Long foodQuan = food.getFoodQuantity();
            if (foodQuan == null){
                foodQuan = 0L;
            }
            //update the quantity of the item
            fridgeRepository.updateUsersFood(foodQuan, food.getCoreQuantity(), foodItem.getRowID());
            Food f = new Food(food.getRowID(), food.getUserID(), food.getFoodName(),
                            foodQuan, food.getCoreQuantity());
            System.out.println(hasCoreFood(f));
        }
        //saves the food with the specifications given
        else{
            fridgeRepository.save(food);
            System.out.println(hasCoreFood(food));
        }
    }

    //returns a list of foods that are below their core quantity amount
    public List<Food> missingCore(Long userID){
        //gets all food in the user's fridge
        List <Food> usersFridge = fridgeRepository.findUsersFridge(userID);
        List<Food> missingCore = new ArrayList<>();
        //calls hasCoreFood for each food
        for(Food f : usersFridge){
            if (hasCoreFood(f)  == false){
                missingCore.add(f);
            }
        } 
        return missingCore;
    }

    //returns false if food is below core quantity
    public Boolean hasCoreFood(Food f){
        if (f.getCoreQuantity() == null){
            return true;
        }
        else if (f.getFoodQuantity() < f.getCoreQuantity()){
            return false;
        }
        return true;
    }

    /*
    Deletes desired amount of food item
    Input: food to delete, number of items to delete
    Return: nothing, error messages if impossible request
     */
    public void deleteFoodItem(Food food){
        // Check if the user has the item in their fridge
        Optional <Food> usersFood = fridgeRepository
                .findUsersFood(food.getFoodName(), food.getUserID());
        // If food exists, remove desired amount from food quantity if possible
        if (usersFood.isPresent()){
            Food foodItem = usersFood.get();
            Long currentQuantity = foodItem.getFoodQuantity();
            Long removedQuantity = food.getFoodQuantity();

            // Update food quantity if possible
            if (currentQuantity >= removedQuantity){
                currentQuantity = foodItem.getFoodQuantity() - removedQuantity;
            } else {
                System.out.println("Not enough food to delete desired quantity");
                return;
            }

            // Save updated food quantity to database
            fridgeRepository.setUsersFood(currentQuantity, foodItem.getCoreQuantity(), foodItem.getRowID());
            System.out.println(foodItem.toString());
        }
        else{
            System.out.println("Food item does not exist");
        }
    }
}