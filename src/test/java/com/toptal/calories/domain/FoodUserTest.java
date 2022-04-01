package com.toptal.calories.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toptal.calories.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FoodUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodUser.class);
        FoodUser foodUser1 = new FoodUser();
        foodUser1.setId(1L);
        FoodUser foodUser2 = new FoodUser();
        foodUser2.setId(foodUser1.getId());
        assertThat(foodUser1).isEqualTo(foodUser2);
        foodUser2.setId(2L);
        assertThat(foodUser1).isNotEqualTo(foodUser2);
        foodUser1.setId(null);
        assertThat(foodUser1).isNotEqualTo(foodUser2);
    }
}
