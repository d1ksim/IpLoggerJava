package com.example.iplogger.service;

import com.example.iplogger.entity.UserEntity;
import com.example.iplogger.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void createUser(UserEntity userEntity) {
        userRepo.save(userEntity);
    }

    public UserEntity findByLogin(String login) {
        return userRepo.findByLogin(login);
    }
}
