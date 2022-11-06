package org.example.repository;

import org.checkerframework.checker.units.qual.A;
import org.example.modul.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;



    public String name (String str){
        String sql = "select name from profie where name = '" + str + "'";

        return jdbcTemplate.queryForObject(sql, String.class);
    }

    public List<Long> getUserId(){
        String sql = "select userId from profile" ;
        return jdbcTemplate.queryForList( sql, Long.class);
    }

    public void addUser(Profile profile) {
         String sql = "INSERT INTO profile (name,userName,userId) values(?,?,?)";


        System.out.println(sql);
         jdbcTemplate.update(sql,profile.getName(),profile.getUserName(),profile.getUserId());
    }

    public Long getUsersCount(){
        String sql = "select count(userId) from profile";

        return jdbcTemplate.queryForObject(sql,Long.class);
    }

    public List<Profile> getUsersList() {
        String sql = "select * from profile";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class));

    }
}
