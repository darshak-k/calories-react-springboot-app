package com.toptal.calories.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toptal.calories.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FoodUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodUserDTO.class);
        FoodUserDTO foodUserDTO1 = new FoodUserDTO();
        foodUserDTO1.setId(1L);
        FoodUserDTO foodUserDTO2 = new FoodUserDTO();
        assertThat(foodUserDTO1).isNotEqualTo(foodUserDTO2);
        foodUserDTO2.setId(foodUserDTO1.getId());
        assertThat(foodUserDTO1).isEqualTo(foodUserDTO2);
        foodUserDTO2.setId(2L);
        assertThat(foodUserDTO1).isNotEqualTo(foodUserDTO2);
        foodUserDTO1.setId(null);
        assertThat(foodUserDTO1).isNotEqualTo(foodUserDTO2);
    }
}
