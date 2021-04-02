package com.navspeak.ldapdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class UserService {
    @Autowired
    private UserRepository userRepository;

    List<User> search(String u) {
        final List<User> userList = new LinkedList<>();
        userRepository.findAll().forEach(user -> {
            if (user.getUsername().contains(u)){
                userList.add(user);
            }
        });
        if (userList == null) {
            return Collections.emptyList();
        }

        User modifieduser = userList.get(0);
        modifieduser.setLastname("Modified");
        userRepository.save(modifieduser);

        return userList;
    }

}

