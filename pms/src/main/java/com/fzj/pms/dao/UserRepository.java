package com.fzj.pms.dao;

import com.fzj.pms.entity.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByUsername(String username);

    @Modifying
    @Query(value ="update User set deleteFlag=1 where id=:id")
    int lockUser(@Param("id") Long id);

    Optional<User> findByUsernameAndPassword(String username,String password);

//    @Override
    Page<User> findAll(Specification<User> specification, Pageable pageable);

//    @Query(value ="select new com.fzy.pms.entity.dto.AccountDto(user.id,user.username,user.realName,user.balance) from User user")
//    Page<AccountDto> findAccountAll(Pageable pageable);
//
//    @Query(value ="select new com.fzy.pms.entity.dto.AccountDto(user.id,user.username,user.realName,user.balance) from User user where username= :username")
//    Page<AccountDto> findAccountByUserId(@Param("username") String username,Pageable pageable);
}
