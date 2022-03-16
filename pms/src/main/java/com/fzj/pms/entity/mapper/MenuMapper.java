package com.fzj.pms.entity.mapper;

import com.fzj.pms.entity.dto.MenuDto;
import com.fzj.pms.entity.security.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel  = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDto, Menu> {
}
