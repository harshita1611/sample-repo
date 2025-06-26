package com.students.studentProfile.repository;

import com.students.studentProfile.enums.BatchEnum;
import com.students.studentProfile.enums.BatchEnumMap;
import com.students.studentProfile.model.Student;
import com.students.studentProfile.service.StudentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Repository
public class StudentRepository {

    private final JdbcTemplate mysqlJdbcTemplate;


    public StudentRepository(@Qualifier("mysqlJdbcTemplate") JdbcTemplate mysqlJdbcTemplate, @Qualifier("postgresJdbcTemplate")JdbcTemplate postgresJdbcTemplate){
        this.mysqlJdbcTemplate=mysqlJdbcTemplate;
    }

    private final Logger logger = LoggerFactory.getLogger(StudentRepository.class);

    private final RowMapper<Student> studentRowMapper=(ResultSet rs, int rowNum)-> {
        Student student = new Student();
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));
        student.setBatch(BatchEnum.valueOf(rs.getString("batch").toUpperCase()));
        student.setAge(rs.getInt("age"));
        student.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
        student.setCourseList(List.of(rs.getString("courseList").split(",")));
        return student;
    };

    /**
     * Get list of all students
     *
     * @return all details of all students
     */
    public List<Student> getAllStudents(int page, int size) {
        int offset = (page - 1) * size;
        logger.info("offset is: {}", offset);
        String SELECT_QUERY = "SELECT * FROM Students ORDER BY id LIMIT ? OFFSET ?;";
        return mysqlJdbcTemplate.query(SELECT_QUERY,studentRowMapper,size,offset);
    }

    public int getTotalStudentsCount() {
        String COUNT_QUERY = "SELECT COUNT(*) FROM Students;";
        return mysqlJdbcTemplate.queryForObject(COUNT_QUERY, Integer.class);
    }


    /**
     * Add a new student to the database
     *
     * @param student
     * @return
     */

    public boolean insertStudent(Student student) {
        logger.info("inside student repo to create new student");
        int derivedAge= Period.between(student.getDateOfBirth(),LocalDate.now()).getYears();
        logger.info("batchnum:{}",student.getBatchNumber());
        logger.info("log{}",getEnumBatchFromNumber((Integer) student.getBatchNumber()));
        String derivedBatch=getEnumBatchFromNumber((Integer) student.getBatchNumber()).name();
        logger.info("batch:{}",derivedBatch);
        String INSERT_QUERY = "INSERT INTO Students (name, email, phone, batch, age, dateOfBirth, courseList) VALUES (?, ?, ?, ?, ?, ?, ?);";
        return mysqlJdbcTemplate.update(INSERT_QUERY, student.getName(), student.getEmail(), student.getPhone(), derivedBatch, derivedAge, student.getDateOfBirth(), String.join(",", student.getCourseList())) > 0;
    }

    /**
     * get details of a particular student
     *
     * @param id
     * @return
     */
    public Student getStudentById(Integer id) {
        String SELECT_QUERY = "SELECT * FROM Students WHERE id = ?;";
        return mysqlJdbcTemplate.queryForObject(SELECT_QUERY, studentRowMapper,id);
    }

    /**
     * Update details of a student
     *
     * @param id
     * @param updatedEntry
     * @return
     */
    public boolean updateStudentById(Integer id, Student updatedEntry) {
        int derivedAge= Period.between(updatedEntry.getDateOfBirth(),LocalDate.now()).getYears();
        String derivedBatch=getEnumBatchFromNumber((Integer) updatedEntry.getBatchNumber()).name();
        String UPDATE_QUERY = "UPDATE Students SET name = ?, email = ?, phone = ?, batch = ?, age = ?, dateOfBirth = ?, courseList = ? WHERE id = ?;";
        return mysqlJdbcTemplate.update(UPDATE_QUERY, updatedEntry.getName(), updatedEntry.getEmail(), updatedEntry.getPhone(), derivedBatch, derivedAge, updatedEntry.getDateOfBirth(), String.join(",", updatedEntry.getCourseList()), id) > 0;
    }

    /**
     * Delete all details of a particular student
     *
     * @param id
     * @return
     */
    public boolean deleteStudentById(Integer id) {
        String DELETE_QUERY = "DELETE FROM Students WHERE id = ?;";
        return mysqlJdbcTemplate.update(DELETE_QUERY, id) > 0;
    }

    /**
     * get list of courses a particular student has enrolled in
     *
     * @param studentId
     * @return
     */
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
        return mysqlJdbcTemplate.queryForList(sql, studentId);
    }

    public BatchEnum getEnumBatchFromNumber(Integer number) {
        for (Map.Entry<BatchEnum, Integer> entry : BatchEnumMap.batches.entrySet()) {
            if (entry.getValue().equals(number)) {
                logger.info("Mapped number {} to BatchEnum {}", number, entry.getKey());
                return entry.getKey();
            }
        }
        logger.error("Invalid batch number: {}", number);
        throw new IllegalArgumentException("Invalid batch number: " + number);
    }
}
