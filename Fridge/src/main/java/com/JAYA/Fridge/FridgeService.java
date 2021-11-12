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
        List<Food> usersFood = fridgeRepository
                .findUsersFood(food.getUserID());
        for(Food f : usersFood) {
            System.out.print(f.toString() + ", ");
            if (food.getFoodName().equals(f.getFoodName())){
                throw new IllegalStateException("food already in user's fridge. number needs to be updated");
            }
        }
        fridgeRepository.save(food);
    }
}