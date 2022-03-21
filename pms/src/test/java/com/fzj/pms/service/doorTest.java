package com.fzj.pms.service;

import com.fzj.pms.dao.DoorRepository;
import com.fzj.pms.dao.UserRepository;
import com.fzj.pms.entity.dto.UserDto;
import com.fzj.pms.entity.enums.DoorType;
import com.fzj.pms.entity.pms.Door;
import com.fzj.pms.entity.security.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class doorTest {

    @Autowired
    private DoorRepository doorRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void doorStatusTest(){
        User user=userRepository.getById(1L);
        Door door = new Door();
        door.setUser(user);
        door.setDeleteFlag(1);
        door.setDoorType(DoorType.QR);
        doorRepository.save(door);
    }

}
