package com.JAYA.Fridge;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;


@Service
public class FridgeService {
    public List<Fridge> getFridge(){
        return List.of(new Fridge(1L, "Pumpkin", 35L, 30L));
    }
    
}