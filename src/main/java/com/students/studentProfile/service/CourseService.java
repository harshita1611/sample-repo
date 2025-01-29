package com.students.studentProfile.service;

import com.students.studentProfile.model.Course;
import com.students.studentProfile.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    /**
     * get list of all courses - service layer
     *
     * @return all details of all courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }

    /**
     * add a new course - service layer
     *
     * @param course
     * @return  true if new course is added successfully else false
     */
    public boolean createCourse(Course course){return courseRepository.insertCourse(course);}

    /**
     * get details of a particular course
     *
     * @param id
     * @return details of a course
     */
    public Course getCourseById(Integer id) {
        return courseRepository.getCourseById(id);
    }

    /**
     * get list of student enrolled in particular course
     *
     * @param courseId
     * @return
     */
//    public List<Map<String, Object>> getCourseStudents(Integer courseId) {
//        return courseRepository.getCourseStudents(courseId);
//    }
}

