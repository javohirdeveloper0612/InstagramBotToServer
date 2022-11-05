package org.example.servise;

import org.example.modul.Profile;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServise {
    @Autowired
    private UserRepository userRepository;

    public boolean isExists(Long id){
       boolean result = userRepository.isExistsUser(id);

       return result;
    }



    public void addUser(Profile profile) {
        userRepository.addUser(profile);
    }
}

