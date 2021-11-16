package com.jaya.fridge;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class FridgeServiceTests {
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private FridgeRepository fridgeRepository;

  @Autowired
  private UserRepository userRepository;

//  @Autowired
//  private FridgeService fridge;

  @Test
  void testUpdateFoodNewFood() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);

    fridge.updateFood(new UpdateQuantity(5L, 7L), 1234L, "chocolate");
    Optional<Food> food = this.fridgeRepository.findUsersFood("chocolate", 1234L);
    assertThat(food.isPresent()).isTrue();
    assertThat(food.get().getUserId()).isEqualTo(1234L);
    assertThat(food.get().getFoodName()).isEqualTo("chocolate");
    assertThat(food.get().getFoodQuantity()).isEqualTo(5L);
    assertThat(food.get().getCoreQuantity()).isEqualTo(7L);
  }

  @Test
  void testUpdateFoodUpdateFoodQuantity() throws Exception {
    FridgeService fridge = new FridgeService(fridgeRepository, userRepository);

    fridge.updateFood(new UpdateQuantity(5L, 7L), 1234L, "chocolate");
    fridge.updateFood(new UpdateQuantity(12L, 8L), 1234L, "chocolate");
    fridge.updateFood(new UpdateQuantity(13L, 9L), 1234L, "chocolate");

    Optional<Food> food = this.fridgeRepository.findUsersFood("chocolate", 1234L);
    assertThat(food.isPresent()).isTrue();
    assertThat(food.get().getUserId()).isEqualTo(1234L);
    assertThat(food.get().getFoodName()).isEqualTo("chocolate");
    assertThat(food.get().getCoreQuantity()).isEqualTo(9L);

    assertThat(food.get().getFoodQuantity()).isEqualTo(30L);
  }

}

