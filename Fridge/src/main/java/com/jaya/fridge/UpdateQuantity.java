package com.jaya.fridge;

public class UpdateQuantity {
  private Long deltaFoodQuantity;
  private Long newCoreQuantity;

  public UpdateQuantity() {

  }

  public UpdateQuantity(Long deltaFoodQuantity, Long coreQuantity) {
    this.deltaFoodQuantity = deltaFoodQuantity;
    this.newCoreQuantity = coreQuantity;
  }

  public Long getDeltaFoodQuantity() {
    return deltaFoodQuantity;
  }

  public void setDeltaFoodQuantity(Long deltaFoodQuantity) {
    this.deltaFoodQuantity = deltaFoodQuantity;
  }

  public Long getNewCoreQuantity() {
    return newCoreQuantity;
  }

  public void setNewCoreQuantity(Long newCoreQuantity) {
    this.newCoreQuantity = newCoreQuantity;
  }

  @Override
  public String toString() {
    return "DeltaQuantity{" +
        "deltaFoodQuantity=" + deltaFoodQuantity +
        ", deltaCoreQuantity=" + newCoreQuantity +
        '}';
  }
}
