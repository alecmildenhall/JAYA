package com.JAYA.Fridge;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;


@Service
public class FridgeService {
    public List<Food> getFridge(){
        return List.of(new Food(1L, "Pumpkin", 3L, 3L));
    }
}