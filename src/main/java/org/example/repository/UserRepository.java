package org.example.repository;

import org.checkerframework.checker.units.qual.A;
import org.example.modul.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean isExistsUser(Long id){
        long userId=0;
        String sql = "select userId from profile where userId = " + id;
         userId = jdbcTemplate.queryForObject(sql, Long.class);
        System.out.println(userId);

        if (userId != 0){
            return false;
        }
        return true;
    }

    public void addUser(Profile profile) {
         String sql = "INSERT INTO profile (name,userName,userId) values(?,?,?)";


        System.out.println(sql);
         jdbcTemplate.update(sql,profile.getName(),profile.getUserName(),profile.getUserId());
    }
}
