package com.fzj.pms.entity.mapper;

import java.util.List;

public interface EntityMapper<D,E> {

    /**
     * Dto 转 Entity
     * @param dto
     * @return
     */
    E toEntity(D dto);

    /**
     * entity 转 dto
     * @param entity
     * @return
     */
    D toDto(E entity);

    /**
     * DTO集合转Entity集合
     * @param dtoList
     * @return
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * Entity集合转DTO集合
     * @param entityList
     * @return
     */
    List <D> toDto(List<E> entityList);

}

