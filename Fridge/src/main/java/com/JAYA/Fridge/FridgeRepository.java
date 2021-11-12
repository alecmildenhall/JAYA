package com.JAYA.Fridge;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Food, Long>{
    @Query("SELECT f FROM Food f WHERE f.userID = ?1")
    List<Food> findUsersFood(Long userID);
}
