package com.fzj.pms.service;

import com.fzj.pms.entity.security.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class getUserListByPageTest {

    @Autowired
    private UserService userService;

    @Test
    public void PageTest(){
//        int pageNum=1;
//        int lineNum=6;
//        Sort sort = Sort.by(Sort.Direction.DESC,"id");
//        Pageable pageable = PageRequest.of(pageNum,lineNum,sort);
//        Page<User> userPage = userService.findUserListByPage(pageable);
//        System.out.println(userPage.toString());
    }

//    @Test
//    public void deleteTest(){
//        long id=2;
//        if(userService.findUser(id).isPresent()){
//            userService.deleteUser(id);
//        }
//    }
}
