package com.jaya.fridge;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Food class represents a food item in a user's fridge.
 */

@Entity
@Table
public class Food {

  @Id
  @SequenceGenerator(
      name = "food_sequence",
      sequenceName = "food_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "food_sequence"
  )

  private Long rowId;
  private Long userId;
  private String foodName;
  private Long foodQuantity;
  private Long coreQuantity;

  public Food() {
  }

  //constructor without rowID
  public Food(Long userId, String foodName, Long foodQuantity, Long coreQuantity) {
    this.userId = userId;
    this.foodName = foodName;
    this.foodQuantity = foodQuantity;
    this.coreQuantity = coreQuantity;
  }

  /**
   * @return Long return the userID
   */
  public Long getRowId() {
    return rowId;
  }

  /**
   * @param rowId the rowId to set
   */
  public void setRowId(Long rowId) {
    this.rowId = rowId;
  }

  /**
   * @return Long return the userID
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * @param userID the userID to set
   */
  public void setUserId(Long userID) {
    this.userId = userID;
  }

  /**
   * @return String return the foodName
   */
  public String getFoodName() {
    return foodName;
  }

  /**
   * @param foodName the foodName to set
   */
  public void setFoodName(String foodName) {
    this.foodName = foodName;
  }

  /**
   * @return Long return the foodQuantity
   */
  public Long getFoodQuantity() {
    return foodQuantity;
  }

  /**
   * @param foodQuantity the foodQuantity to set
   */
  public void setFoodQuantity(Long foodQuantity) {
    this.foodQuantity = foodQuantity;
  }

  /**
   * @return Long return the coreQuantity
   */
  public Long getCoreQuantity() {
    return coreQuantity;
  }

  /**
   * @param coreQuantity the coreQuantity to set
   */
  public void setCoreQuantity(Long coreQuantity) {
    this.coreQuantity = coreQuantity;
  }

  @Override
  public String toString() {
    return "Food{" +
        "userID=" + userId +
        ", foodName=" + foodName +
        ", foodQuantity=" + foodQuantity +
        ", coreQuantity=" + coreQuantity +
        "}";
  }

}
