package com.fzj.pms.service;

import com.fzj.pms.entity.security.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class roleTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void listTest(){
        System.out.println(roleService.searchByName("admin",10,1));
    }

}
