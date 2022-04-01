package com.toptal.calories.service.mapper;

import com.toptal.calories.domain.FoodUser;
import com.toptal.calories.service.dto.FoodUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FoodUser} and its DTO {@link FoodUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { FoodMapper.class, UserInfoMapper.class })
public interface FoodUserMapper extends EntityMapper<FoodUserDTO, FoodUser> {
    @Mapping(target = "food", source = "food", qualifiedByName = "id")
    @Mapping(target = "foodUser", source = "foodUser", qualifiedByName = "id")
    FoodUserDTO toDto(FoodUser s);
}
