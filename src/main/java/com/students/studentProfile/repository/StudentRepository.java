package com.students.studentProfile.repository;

import com.students.studentProfile.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Student> getAllStudents() {
        String SELECT_QUERY = "SELECT * FROM Students;";
        return jdbcTemplate.query(SELECT_QUERY, (rs, rowNum) -> {
            Student student = new Student();
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setPhone(rs.getString("phone"));
            student.setBatch(rs.getString("batch"));
            student.setAge(rs.getInt("age"));
            student.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
            student.setCourseList(List.of(rs.getString("courseList").split(",")));
            return student;
        });
    }

    public boolean insertStudent(Student student) {
        int derivedAge= Period.between(student.getDateOfBirth(),LocalDate.now()).getYears();
        String INSERT_QUERY = "INSERT INTO Students (name, email, phone, batch, age, dateOfBirth, courseList) VALUES (?, ?, ?, ?, ?, ?, ?);";
        return jdbcTemplate.update(INSERT_QUERY, student.getName(), student.getEmail(), student.getPhone(), student.getBatch(), derivedAge, student.getDateOfBirth(), String.join(",", student.getCourseList())) > 0;
    }

    public Student getStudentById(Integer id) {
        String SELECT_QUERY = "SELECT * FROM Students WHERE id = ?;";
        return jdbcTemplate.queryForObject(SELECT_QUERY, (rs, rowNum) -> {
            Student student = new Student();
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setPhone(rs.getString("phone"));
            student.setBatch(rs.getString("batch"));
            student.setAge(rs.getInt("age"));
            student.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
            student.setCourseList(List.of(rs.getString("courseList").split(",")));
            return student;
        }, id);
    }

    public boolean updateStudentById(Integer id, Student updatedEntry) {
        String UPDATE_QUERY = "UPDATE Students SET name = ?, email = ?, phone = ?, batch = ?, age = ?, dateOfBirth = ?, courseList = ? WHERE id = ?;";
        return jdbcTemplate.update(UPDATE_QUERY, updatedEntry.getName(), updatedEntry.getEmail(), updatedEntry.getPhone(), updatedEntry.getBatch(), updatedEntry.getAge(), updatedEntry.getDateOfBirth(), String.join(",", updatedEntry.getCourseList()), id) > 0;
    }

    public boolean deleteStudentById(Integer id) {
        String DELETE_QUERY = "DELETE FROM Students WHERE id = ?;";
        return jdbcTemplate.update(DELETE_QUERY, id) > 0;
    }

    public List<Map<String, Object>> getStudentCourses(Integer studentId) {
        String sql = """
            SELECT c.course_code, c.course_name, b.batch_code, i.name as instructor_name, 
                   ce.enrollment_date, ce.status
            FROM Course_Enrollments ce
            JOIN Courses c ON ce.course_id = c.course_id
            JOIN Batches b ON ce.batch_id = b.batch_id
            JOIN Instructors i ON ce.instructor_id = i.instructor_id
            WHERE ce.student_id = ?
        """;
        return jdbcTemplate.queryForList(sql, studentId);
    }
}
