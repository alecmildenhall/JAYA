package com.JAYA.Fridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "api/v1/fridge")
public class FridgeController {

    private final FridgeService fridgeService;

    @Autowired
    public FridgeController(FridgeService fridgeService){
        this.fridgeService = fridgeService;
    }

    @GetMapping
	public Optional<Food> getFridge(Long userID){
        return fridgeService.getFridge(userID);
	}

    @PostMapping
    public void addFoodItem(@RequestBody Food food){
        fridgeService.addFoodItem(food);

    }

    @DeleteMapping
    public void deleteFoodItem(@RequestBody Food food, @RequestBody long removedQuantity) {
        fridgeService.deleteFoodItem(food, removedQuantity);
    }
    
}
