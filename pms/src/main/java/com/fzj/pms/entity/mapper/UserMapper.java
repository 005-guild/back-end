package com.fzj.pms.entity.mapper;

import com.fzj.pms.entity.dto.UserDto;
import com.fzj.pms.entity.security.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel  = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDto, User> {
}
