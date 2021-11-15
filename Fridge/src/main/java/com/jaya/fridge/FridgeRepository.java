package com.jaya.fridge;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Food, Long> {

  @Query("SELECT f FROM Food f WHERE f.foodName = ?1 AND f.userId = ?2")
  Optional<Food> findUsersFood(String FoodName, Long userID);

  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Transactional
  @Query("UPDATE Food f SET f.foodQuantity = f.foodQuantity + ?1, f.coreQuantity = ?2 WHERE f.rowId = ?3")
  void updateUsersFood(Long foodQuantity, Long coreQuantity, Long rowId);

  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Transactional
  @Query("UPDATE Food f SET f.foodQuantity = ?1 WHERE f.rowId = ?2")
  void setUsersFood(Long foodQuantity, Long rowId);

  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Transactional
  @Query("UPDATE Food f SET f.coreQuantity = ?1 WHERE f.rowId = ?2")
  void setUsersCore(Long coreQuantity, Long rowId);

  @Query("SELECT f FROM Food f WHERE f.userId = ?1")
  List<Food> findUsersFridge(Long userID);

  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Transactional
  @Query("DELETE FROM Food f WHERE f.userId = ?1")
  void deleteUserFood(Long userID);
}
