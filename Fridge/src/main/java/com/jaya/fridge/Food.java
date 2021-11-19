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

  /**
   * Food constructor that takes in all instance variables of the class.
   *
   * @param rowId the row number of the entry into dataset
   * @param userId the user's unique ID
   * @param foodName the name of the food
   * @param foodQuantity the amount of this food in the fridge
   * @param coreQuantity the amount of this food desired to always be in the fridge
   */
  public Food(Long rowId, Long userId, String foodName, Long foodQuantity, Long coreQuantity) {
    this.rowId = rowId;
    this.userId = userId;
    this.foodName = foodName;
    this.foodQuantity = foodQuantity;
    this.coreQuantity = coreQuantity;
  }

  /**
   * Food constructor that takes in all instance variables of the class.
   *
   * @param userId the user's unique ID
   * @param foodName the name of the food
   * @param foodQuantity the amount of this food in the fridge
   * @param coreQuantity the amount of this food desired to always be in the fridge
   */
  public Food(Long userId, String foodName, Long foodQuantity, Long coreQuantity) {
    this.userId = userId;
    this.foodName = foodName;
    this.foodQuantity = foodQuantity;
    this.coreQuantity = coreQuantity;
  }

  /**
   * Food constructor that takes in all instance variables of the class.
   *
   * @param rowId the row number of the entry into dataset
   * @param userId the user's unique ID
   * @param foodName the name of the food
   * @param foodQuantity the amount of this food in the fridge
   */
  public Food(Long rowId, Long userId, String foodName, Long foodQuantity) {
    this.rowId = rowId;
    this.userId = userId;
    this.foodName = foodName;
    this.foodQuantity = foodQuantity;
  }

  /**
   * Food constructor that takes in all instance variables of the class.
   *
   * @param userId the user's unique ID
   * @param foodName the name of the food
   * @param foodQuantity the amount of this food in the fridge
   */
  public Food(Long userId, String foodName, Long foodQuantity) {
    this.userId = userId;
    this.foodName = foodName;
    this.foodQuantity = foodQuantity;
  }

  /**
   * Getter method for rowId.
   *
   * @return the rowId
   */
  public Long getRowId() {
    return rowId;
  }

  /**
   * Setter method for rowId.
   *
   * @param rowId the rowId to set
   */
  public void setRowId(Long rowId) {
    this.rowId = rowId;
  }

  /**
   * Getter method for userId.
   *
   * @return the userId
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Setter method for userId.
   *
   * @param userId the userId to set
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Getter method for foodName.
   *
   * @return the foodName
   */
  public String getFoodName() {
    return foodName;
  }

  /**
   * Setter method for foodName.
   *
   * @param foodName the foodName to set
   */
  public void setFoodName(String foodName) {
    this.foodName = foodName;
  }

  /**
   * Getter method for foodQuantity.
   *
   * @return the foodQuantity
   */
  public Long getFoodQuantity() {
    return foodQuantity;
  }

  /**
   * Setter method for foodQuantity.
   *
   * @param foodQuantity the foodQuantity to set
   */
  public void setFoodQuantity(Long foodQuantity) {
    this.foodQuantity = foodQuantity;
  }

  /**
   * Getter method for coreQuantity.
   *
   * @return the coreQuantity
   */
  public Long getCoreQuantity() {
    return coreQuantity;
  }

  /**
   * Setter method for coreQuantity.
   *
   * @param coreQuantity the coreQuantity to set
   */
  public void setCoreQuantity(Long coreQuantity) {
    this.coreQuantity = coreQuantity;
  }

  @Override
  public String toString() {
    return "Food {"
        + "userID=" + userId
        + ", foodName=" + foodName
        + ", foodQuantity=" + foodQuantity
        + ", coreQuantity=" + coreQuantity
        + "}";
  }

}
