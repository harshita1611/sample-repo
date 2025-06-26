package com.students.studentProfile.repository;

import com.students.studentProfile.controller.StudentData;
import com.students.studentProfile.model.Instructor;
import com.students.studentProfile.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeacherRepository {




    private final JdbcTemplate mysqlJdbcTemplate;
    private final JdbcTemplate postgresJdbcTemplate;

    public TeacherRepository(@Qualifier("mysqlJdbcTemplate") JdbcTemplate mysqlJdbcTemplate,@Qualifier("postgresJdbcTemplate")JdbcTemplate postgresJdbcTemplate){
        this.mysqlJdbcTemplate=mysqlJdbcTemplate;
        this.postgresJdbcTemplate=postgresJdbcTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(StudentData.class);

    /**
     * get all details of all teachers
     *
     * @return
     */
    public List<Teacher> getAllTeacher() {
        logger.info("before querty");
        String SELECT_QUERY = "SELECT * FROM teacher";
        logger.info("after querty");

        return postgresJdbcTemplate.query(SELECT_QUERY, (rs, rowNum) -> {
            Teacher teacher = new Teacher();
            teacher.setTeacherId(rs.getInt("teacher_id"));
            teacher.setName(rs.getString("name"));
            teacher.setEmail(rs.getString("email"));
            teacher.setPhone(rs.getString("phone"));
            teacher.setDepartment(rs.getString("department"));
            teacher.setJoiningDate(rs.getDate("joining_date"));
            teacher.setStudentId(rs.getInt("student_id"));
            return teacher;
        });

    }

}
