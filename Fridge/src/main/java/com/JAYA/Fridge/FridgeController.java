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

    @GetMapping(path = "/get-fridge")
	  public List<Food> getFridge(@RequestBody Long userID){ return fridgeService.getFridge(userID); }

    @PostMapping(path = "/add-food")
    public void addFoodItem(@RequestBody Food food){
        fridgeService.addFoodItem(food);
    }
  
    @PostMapping(path = "/add-core")
    public void addCoreItem(@RequestBody Food food){
        fridgeService.addCoreItem(food);
    }

    @PostMapping(path = "/missing-core")
    public List<Food> missingCore(@RequestBody Long userID){
        return fridgeService.missingCore(userID);
    }
  
    @DeleteMapping(path = "delete-food")
    public void deleteFoodItem(@RequestBody Food food) {
        fridgeService.deleteFoodItem(food);
    }

    @DeleteMapping(path = "delete-core")
    public void deleteCoreItem(@RequestBody Food food) {
        fridgeService.deleteCoreItem(food);
    }
}
