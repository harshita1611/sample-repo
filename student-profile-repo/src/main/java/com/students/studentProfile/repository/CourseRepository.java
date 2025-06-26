package com.students.studentProfile.repository;

import com.students.studentProfile.controller.CourseController;
import com.students.studentProfile.model.Course;
import com.students.studentProfile.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CourseRepository {

    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    /**
     * get list of all courses
     * @return list of all courses and all other details
     */
    private final JdbcTemplate mysqlJdbcTemplate;
    private final JdbcTemplate postgresJdbcTemplate;

    public CourseRepository(@Qualifier("mysqlJdbcTemplate") JdbcTemplate mysqlJdbcTemplate, @Qualifier("postgresJdbcTemplate")JdbcTemplate postgresJdbcTemplate){
        this.mysqlJdbcTemplate=mysqlJdbcTemplate;
        this.postgresJdbcTemplate=postgresJdbcTemplate;
    }

    public List<Course> getAllCourses(){
        String sql="Select * from Courses";
        return mysqlJdbcTemplate.query(sql,(rs, rowNum) -> {
            Course course = new Course();
            course.setCourseId(rs.getInt("course_id"));
            course.setCourseCode(rs.getString("course_code"));
            course.setCourseName(rs.getString("course_name"));
            course.setDescription(rs.getString("description"));
            course.setCredits(rs.getInt("credits"));
            return course;
        });
    }

    /**
     * Add a new course
     *
     * @param course
     * @return true if course is added successfully else false
     */
    public boolean insertCourse(Course course) {
        String INSERT_QUERY = "INSERT INTO Courses (course_id, course_code, course_name, description, credits) VALUES (?, ?, ?, ?, ?);";
        return mysqlJdbcTemplate.update(INSERT_QUERY, course.getCourseId(), course.getCourseCode(),course.getCourseName(),course.getDescription(), course.getCredits())>0;
    }

    /**
     * get all deails of a course with particular id
     *
     * @param course_id
     * @return
     */
    public Course getCourseById(Integer course_id) {
        String SELECT_QUERY = "SELECT * FROM Courses WHERE course_id = ?;";
        return mysqlJdbcTemplate.queryForObject(SELECT_QUERY, (rs, rowNum) -> {
            Course course = new Course();
            course.setCourseId(rs.getInt("course_id"));
            course.setCourseCode(rs.getString("course_code"));
            course.setCourseName(rs.getString("course_name"));
            course.setDescription(rs.getString("description"));
            course.setCredits(rs.getInt("credits"));
            return course;
        }, course_id);
    }


    /**
     * get all students enrolled in a particular course
     *
     * @param teacher_id
     * @return
     */
    public List<Map<String, Object>> getCourseStudents(Integer teacher_id) {
        String postgres="Select * from teacher where teacher_id=?";

        List<Map<String,Object>> teacher=postgresJdbcTemplate.queryForList(postgres,teacher_id);


        List<Integer> studentIds = new ArrayList<>();
        for(Map<String,Object> row:teacher){
            Object StudentIdObj = row.get("student_id");
            if (StudentIdObj!=null){
                studentIds.add(Integer.parseInt(StudentIdObj.toString()));
            }
        }
        logger.info("{}",studentIds);
        String sql =String.format("SELECT * FROM students WHERE id IN (%s)",studentIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        logger.info("{}",sql);
        List<Map<String,Object>> students=mysqlJdbcTemplate.queryForList(sql);
        logger.info("Students for teacher_id {}: {}", teacher_id, students);

        return students;
    }


}
