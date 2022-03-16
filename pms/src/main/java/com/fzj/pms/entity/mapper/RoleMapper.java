package com.fzj.pms.entity.mapper;

import com.fzj.pms.entity.dto.RoleDto;
import com.fzj.pms.entity.security.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel  = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends EntityMapper<RoleDto, Role> {
}
