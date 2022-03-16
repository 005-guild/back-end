package com.fzj.pms.dao;

import com.fzj.pms.entity.security.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role ,Long> {

    @Transactional
    @Modifying
    @Query(value = "update t_role set delete_flag=1 where id in :ids",nativeQuery = true)
    int deleteRoleByIdIn(@Param("ids") Set<Long> ids);

    List<Role> findRoleByNameLike(String name, Sort sort);

    Optional<Role> findRoleByName(String name);
}
