package com.toptal.calories.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserInfoMapperTest {

    private UserInfoMapper userInfoMapper;

    @BeforeEach
    public void setUp() {
        userInfoMapper = new UserInfoMapperImpl();
    }
}
