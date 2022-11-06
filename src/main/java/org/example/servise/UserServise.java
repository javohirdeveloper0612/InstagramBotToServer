package org.example.servise;

import org.example.modul.Profile;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServise {
    @Autowired
    private UserRepository userRepository;

    public boolean isExists(Long id){
       List<Long> userId = userRepository.getUserId();
        for (Long aLong : userId) {
            if (aLong.equals(id)){
                return true;
            }
        }
        return false;
    }



    public void addUser(Profile profile) {
        userRepository.addUser(profile);
    }
}

