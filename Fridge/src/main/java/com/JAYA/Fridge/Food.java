package com.JAYA.Fridge;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

    private Long userID;
    private String foodName;
    private Long foodQuantity;
    private Long coreQuantity;

    public Food() {

    }
    
    //constructor with all values defined
    public Food(Long userID, String foodName, Long foodQuantity, Long coreQuantity){
        this.userID = userID;
        this.foodName = foodName;
        this.foodQuantity = foodQuantity;
        this.coreQuantity = coreQuantity;
    }

    //constructor without coreQuantity
    public Food(Long userID, String foodName, Long foodQuantity){
        this.userID = userID;
        this.foodName = foodName;
        this.foodQuantity = foodQuantity;
    }

    /**
     * @return Long return the userID
     */
    public Long getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(Long userID) {
        this.userID = userID;
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

}
