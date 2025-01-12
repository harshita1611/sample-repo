package com.students.studentProfile.repository;

import com.students.studentProfile.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstructorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Instructor> getAllInstructors() {
        String SELECT_QUERY = "SELECT * FROM Instructors;";
        return jdbcTemplate.query(SELECT_QUERY, (rs, rowNum) -> {
            Instructor instructor = new Instructor();
            instructor.setInstructorId(rs.getInt("instructor_id"));
            instructor.setName(rs.getString("name"));
            instructor.setEmail(rs.getString("email"));
            instructor.setPhone(rs.getString("phone"));
            instructor.setDepartment(rs.getString("department"));
            instructor.setJoiningDate(rs.getString("joining_date"));
            return instructor;
        });
    }

    public Instructor getInstructorById(Integer instructorId) {
        String SELECT_QUERY = "SELECT * FROM Instructors WHERE instructor_id = ?;";
        return jdbcTemplate.queryForObject(SELECT_QUERY, (rs, rowNum) -> {
            Instructor instructor = new Instructor();
            instructor.setInstructorId(rs.getInt("instructor_id"));
            instructor.setName(rs.getString("name"));
            instructor.setEmail(rs.getString("email"));
            instructor.setPhone(rs.getString("phone"));
            instructor.setDepartment(rs.getString("department"));
            instructor.setJoiningDate(rs.getString("joining_date"));
            return instructor;
        }, instructorId);
    }

    public boolean insertInstructor(Instructor instructor) {
        String INSERT_QUERY = "INSERT INTO Instructors (name, email, phone, department, joining_date) VALUES (?, ?, ?, ?, ?);";
        return jdbcTemplate.update(INSERT_QUERY,
                instructor.getName(),
                instructor.getEmail(),
                instructor.getPhone(),
                instructor.getDepartment(),
                instructor.getJoiningDate()) > 0;
    }

    public boolean updateInstructorById(Integer instructorId, Instructor updatedInstructor) {
        String UPDATE_QUERY = "UPDATE Instructors SET name = ?, email = ?, phone = ?, department = ?, joining_date = ? WHERE instructor_id = ?;";
        return jdbcTemplate.update(UPDATE_QUERY,
                updatedInstructor.getName(),
                updatedInstructor.getEmail(),
                updatedInstructor.getPhone(),
                updatedInstructor.getDepartment(),
                updatedInstructor.getJoiningDate(),
                instructorId) > 0;
    }

    public boolean deleteInstructorById(Integer instructorId) {
        String DELETE_QUERY = "DELETE FROM Instructors WHERE instructor_id = ?;";
        return jdbcTemplate.update(DELETE_QUERY, instructorId) > 0;
    }

}
