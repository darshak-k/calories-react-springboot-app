package com.toptal.calories.service.mapper;

import com.toptal.calories.domain.Food;
import com.toptal.calories.service.dto.FoodDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Food} and its DTO {@link FoodDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FoodMapper extends EntityMapper<FoodDTO, Food> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FoodDTO toDtoId(Food food);
}
