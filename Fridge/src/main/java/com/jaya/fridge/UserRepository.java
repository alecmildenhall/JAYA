package com.jaya.fridge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT f FROM User f WHERE f.userId = ?1")
  List<User> findUser(Long userId);

  @Modifying
  @Transactional
  @Query("DELETE FROM User f WHERE f.userId = ?1")
  void deleteUser(Long userId);

}
