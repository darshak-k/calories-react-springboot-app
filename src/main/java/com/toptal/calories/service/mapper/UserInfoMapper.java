package com.toptal.calories.service.mapper;

import com.toptal.calories.domain.UserInfo;
import com.toptal.calories.service.dto.UserInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserInfo} and its DTO {@link UserInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserInfoMapper extends EntityMapper<UserInfoDTO, UserInfo> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserInfoDTO toDtoId(UserInfo userInfo);
}
