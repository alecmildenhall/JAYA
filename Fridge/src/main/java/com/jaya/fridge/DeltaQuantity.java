package com.jaya.fridge;

public class DeltaQuantity {
  private Long deltaFoodQuantity;
  private Long deltaCoreQuantity;

  public DeltaQuantity() {

  }

  public DeltaQuantity(Long deltaFoodQuantity, Long coreQuantity) {
    this.deltaFoodQuantity = deltaFoodQuantity;
    this.deltaCoreQuantity = coreQuantity;
  }

  public Long getDeltaFoodQuantity() {
    return deltaFoodQuantity;
  }

  public void setDeltaFoodQuantity(Long deltaFoodQuantity) {
    this.deltaFoodQuantity = deltaFoodQuantity;
  }

  public Long getDeltaCoreQuantity() {
    return deltaCoreQuantity;
  }

  public void setDeltaCoreQuantity(Long deltaCoreQuantity) {
    this.deltaCoreQuantity = deltaCoreQuantity;
  }

  @Override
  public String toString() {
    return "DeltaQuantity{" +
        "deltaFoodQuantity=" + deltaFoodQuantity +
        ", deltaCoreQuantity=" + deltaCoreQuantity +
        '}';
  }
}
