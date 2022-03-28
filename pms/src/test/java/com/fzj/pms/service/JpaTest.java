package com.fzj.pms.service;

import com.fzj.pms.dao.HouseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JpaTest {

    @Autowired
    private HouseRepository houseRepository;

    @Test
    public void jpaTest(){
        if(houseRepository.findByBuilding("5")==null){
            System.out.println("null");
        }else if(houseRepository.findByBuilding("5").isEmpty()){
            System.out.println("empty");
        }
        System.out.println(houseRepository.findByBuilding("5"));
    }
}
