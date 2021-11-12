package com.JAYA.Fridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	public List<Fridge> getFridge(){
        return fridgeService.getFridge();
	}
    
}
