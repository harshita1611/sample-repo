package com.students.studentProfile.repository;

import com.students.studentProfile.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstructorRepository {

    private final JdbcTemplate mysqlJdbcTemplate;
    private final JdbcTemplate postgresJdbcTemplate;

    public InstructorRepository(@Qualifier("mysqlJdbcTemplate") JdbcTemplate mysqlJdbcTemplate, @Qualifier("postgresJdbcTemplate")JdbcTemplate postgresJdbcTemplate){
        this.mysqlJdbcTemplate=mysqlJdbcTemplate;
        this.postgresJdbcTemplate=postgresJdbcTemplate;
    }


    /**
     * get all details of all instructors
     *
     * @return
     */
    public List<Instructor> getAllInstructors() {
        String SELECT_QUERY = "SELECT * FROM Instructors;";
        return mysqlJdbcTemplate.query(SELECT_QUERY, (rs, rowNum) -> {
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

    /**
     * get all details of a particular instructor
     *
     * @param instructorId
     * @return
     */

    public Instructor getInstructorById(Integer instructorId) {
        String SELECT_QUERY = "SELECT * FROM Instructors WHERE instructor_id = ?;";
        return mysqlJdbcTemplate.queryForObject(SELECT_QUERY, (rs, rowNum) -> {
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


    /**
     * add a new instructor to the database
     *
     * @param instructor
     * @return
     */
    public boolean insertInstructor(Instructor instructor) {
        String INSERT_QUERY = "INSERT INTO Instructors (name, email, phone, department, joining_date) VALUES (?, ?, ?, ?, ?);";
        return mysqlJdbcTemplate.update(INSERT_QUERY,
                instructor.getName(),
                instructor.getEmail(),
                instructor.getPhone(),
                instructor.getDepartment(),
                instructor.getJoiningDate()) > 0;
    }

    /**
     * Update details of a particular instructor
     *
     * @param instructorId
     * @param updatedInstructor
     * @return
     */
    public boolean updateInstructorById(Integer instructorId, Instructor updatedInstructor) {
        String UPDATE_QUERY = "UPDATE Instructors SET name = ?, email = ?, phone = ?, department = ?, joining_date = ? WHERE instructor_id = ?;";
        return mysqlJdbcTemplate.update(UPDATE_QUERY,
                updatedInstructor.getName(),
                updatedInstructor.getEmail(),
                updatedInstructor.getPhone(),
                updatedInstructor.getDepartment(),
                updatedInstructor.getJoiningDate(),
                instructorId) > 0;
    }

    /**
     * delete all details of a particular instructor
     *
     * @param instructorId
     * @return
     */
    public boolean deleteInstructorById(Integer instructorId) {
        String DELETE_QUERY = "DELETE FROM Instructors WHERE instructor_id = ?;";
        return mysqlJdbcTemplate.update(DELETE_QUERY, instructorId) > 0;
    }

}
