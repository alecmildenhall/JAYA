package com.JAYA.Fridge;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Food, Long>{
    @Query("SELECT f FROM Food f WHERE f.foodName = ?1 AND f.userID = ?2")
    Optional <Food> findUsersFood(String FoodName, Long userID);

    @Query("SELECT f FROM Food f WHERE f.userID = ?1")
    Optional <Food> findFridge(Long userID);

    @Modifying
    @Transactional
    @Query("UPDATE Food f SET f.foodQuantity = f.foodQuantity + ?1, f.coreQuantity = ?2 WHERE f.rowID = ?3")
    void updateUsersFood(Long foodQuantity, Long coreQuantity, Long rowId);
}
