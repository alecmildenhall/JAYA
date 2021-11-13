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

    public List<Food> getFridge(){
        return fridgeRepository.findAll();
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
                foodQuan = foodItem.getFoodQuantity();
            }
            //update the quantity of the item
            fridgeRepository.updateUsersFood(foodQuan, food.getCoreQuantity(), foodItem.getRowID());
            System.out.println(foodItem.toString());
        }
        //saves the food with the specifications given
        else{
            fridgeRepository.save(food);
        }
    }
}