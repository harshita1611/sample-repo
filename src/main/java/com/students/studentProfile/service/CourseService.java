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

    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }

    public boolean createCourse(Course course){return courseRepository.insertCourse(course);}

    public Course getCourseById(Integer id) {
        return courseRepository.getCourseById(id);
    }

    public List<Map<String, Object>> getCourseStudents(Integer courseId) {
        return courseRepository.getCourseStudents(courseId);
    }
}

