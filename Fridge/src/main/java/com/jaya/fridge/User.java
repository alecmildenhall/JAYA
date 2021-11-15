package com.jaya.fridge;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @SequenceGenerator(
      name = "user_sequence",
      sequenceName = "user_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "user_sequence"
  )

  private Long userId;
  private String email;
  private String name;

  // default constructor
  public User() {

  }

  // constructor with all values defined
  public User(Long userId, String email, String name) {
    this.userId = userId;
    this.email = email;
    this.name = name;
  }

  // constructor with just userID
  public User(Long userId) {
    this.userId = userId;
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
   * @return String return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return String return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
}
