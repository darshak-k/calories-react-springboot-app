package com.toptal.calories.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodUserMapperTest {

    private FoodUserMapper foodUserMapper;

    @BeforeEach
    public void setUp() {
        foodUserMapper = new FoodUserMapperImpl();
    }
}
