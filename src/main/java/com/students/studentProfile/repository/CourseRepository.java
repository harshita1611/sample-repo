package com.students.studentProfile.repository;

import com.students.studentProfile.model.Course;
import com.students.studentProfile.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public class CourseRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Course> getAllCourses(){
        String sql="Select * from Courses";
        return jdbcTemplate.query(sql,(rs, rowNum) -> {
            Course course = new Course();
            course.setCourseId(rs.getInt("course_id"));
            course.setCourseCode(rs.getString("course_code"));
            course.setCourseName(rs.getString("course_name"));
            course.setDescription(rs.getString("description"));
            course.setCredits(rs.getInt("credits"));
            return course;
        });
    }

    public boolean insertCourse(Course course) {
        String INSERT_QUERY = "INSERT INTO Courses (course_id, course_code, course_name, description, credits) VALUES (?, ?, ?, ?, ?);";
        return jdbcTemplate.update(INSERT_QUERY, course.getCourseId(), course.getCourseCode(),course.getCourseName(),course.getDescription(), course.getCredits())>0;
    }
    public Course getCourseById(Integer course_id) {
        String SELECT_QUERY = "SELECT * FROM Courses WHERE course_id = ?;";
        return jdbcTemplate.queryForObject(SELECT_QUERY, (rs, rowNum) -> {
            Course course = new Course();
            course.setCourseId(rs.getInt("course_id"));
            course.setCourseCode(rs.getString("course_code"));
            course.setCourseName(rs.getString("course_name"));
            course.setDescription(rs.getString("description"));
            course.setCredits(rs.getInt("credits"));
            return course;
        }, course_id);
    }

    public List<Map<String, Object>> getCourseStudents(Integer courseId) {
        String sql = """
            SELECT 
                s.id AS studentId, 
                s.name, 
                s.email, 
                s.phone, 
                s.batch, 
                s.age, 
                s.dateOfBirth
            FROM 
                students s
            JOIN 
                course_enrollments ce ON s.id = ce.student_id
            WHERE 
                ce.course_id = ?
        """;

        return jdbcTemplate.queryForList(sql, courseId);
    }


}
