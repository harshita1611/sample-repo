package com.students.studentProfile.repository;


import com.students.studentProfile.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class UserRepository {
    private  final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final JdbcTemplate mysqlJdbcTemplate;

    public UserRepository(@Qualifier("mysqlJdbcTemplate") JdbcTemplate mysqlJdbcTemplate){
        this.mysqlJdbcTemplate=mysqlJdbcTemplate;
    }

    public String insertUser(User user){
        logger.info("insert user repo to create user");
        String SQL_QUERY="INSERT INTO USERS (username,password,role) VALUES(?,?,?) ";
        try{
            mysqlJdbcTemplate.update(SQL_QUERY,user.getUsername(),user.getPassword(),user.getRole());
            return "added new user";
        } catch (Exception e){
            throw e;
        }
    }

    public User findByUsername(String username) {
        String SQL = "SELECT * FROM users WHERE username = ?";

        return mysqlJdbcTemplate.queryForObject(SQL, (rs, rowNum) -> {
            User user = new User();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role")); // Assuming there's a setRole() method
            return user;
        }, username);
    }


}
