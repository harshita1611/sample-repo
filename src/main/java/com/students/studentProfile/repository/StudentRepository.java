package com.students.studentProfile.repository;

import com.students.studentProfile.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Student> getAllStudents() {
        String SELECT_QUERY = "SELECT * FROM StudentDetails;";
        return jdbcTemplate.query(SELECT_QUERY, (rs, rowNum) -> {
            Student student = new Student();
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setPhone(rs.getString("phone"));
            student.setBatch(rs.getString("batch"));
            student.setAge(rs.getInt("age"));
            student.setDateOfBirth(rs.getString("dateOfBirth"));
            student.setCourseList(List.of(rs.getString("courseList").split(",")));
            return student;
        });
    }

    public boolean insertStudent(Student student) {
        String INSERT_QUERY = "INSERT INTO StudentDetails (name, email, phone, batch, age, dateOfBirth, courseList) VALUES (?, ?, ?, ?, ?, ?, ?);";
        return jdbcTemplate.update(INSERT_QUERY, student.getName(), student.getEmail(), student.getPhone(), student.getBatch(), student.getAge(), student.getDateOfBirth(), String.join(",", student.getCourseList())) > 0;
    }

    public Student getStudentById(Integer id) {
        String SELECT_QUERY = "SELECT * FROM StudentDetails WHERE id = ?;";
        return jdbcTemplate.queryForObject(SELECT_QUERY, (rs, rowNum) -> {
            Student student = new Student();
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setPhone(rs.getString("phone"));
            student.setBatch(rs.getString("batch"));
            student.setAge(rs.getInt("age"));
            student.setDateOfBirth(rs.getString("dateOfBirth"));
            student.setCourseList(List.of(rs.getString("courseList").split(",")));
            return student;
        }, id);
    }

    public boolean updateStudentById(Integer id, Student updatedEntry) {
        String UPDATE_QUERY = "UPDATE StudentDetails SET name = ?, email = ?, phone = ?, batch = ?, age = ?, dateOfBirth = ?, courseList = ? WHERE id = ?;";
        return jdbcTemplate.update(UPDATE_QUERY, updatedEntry.getName(), updatedEntry.getEmail(), updatedEntry.getPhone(), updatedEntry.getBatch(), updatedEntry.getAge(), updatedEntry.getDateOfBirth(), String.join(",", updatedEntry.getCourseList()), id) > 0;
    }

    public boolean deleteStudentById(Integer id) {
        String DELETE_QUERY = "DELETE FROM StudentDetails WHERE id = ?;";
        return jdbcTemplate.update(DELETE_QUERY, id) > 0;
    }
}
