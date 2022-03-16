package com.fzj.pms.entity.dto;

import lombok.Data;

import java.util.Set;

@Data
public class MenuDto implements Comparable {

    private Long id;

    private String name;

    private String path;

    private String component;

    private String icon;

    private Set<MenuDto> children;

    @Override
    public int compareTo(Object obj) {
        if (! (obj instanceof MenuDto)) throw new RuntimeException("NotSuchTypeException");

        MenuDto m= (MenuDto) obj;
        return this.id.compareTo(m.id);
    }
}
