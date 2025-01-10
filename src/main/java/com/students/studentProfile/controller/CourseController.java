package com.students.studentProfile.controller;

import com.students.studentProfile.model.Course;
import com.students.studentProfile.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


    @RestController
    @RequestMapping("/api/courses")
    public class CourseController {
        private final Logger logger = LoggerFactory.getLogger(CourseController.class);

        @Autowired
        private CourseService courseService;

        @GetMapping
        public ResponseEntity<List<Course>> getAllCourses() {
            logger.info("Fetching all courses");
            return ResponseEntity.ok(courseService.getAllCourses());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Course> getCourseById(@PathVariable Integer id) {
            logger.info("Fetching course with id: {}", id);
            Course course = courseService.getCourseById(id);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(course);
        }

        @GetMapping("/{id}/students")
        public ResponseEntity<List<Map<String, Object>>> getCourseStudents(@PathVariable Integer id) {
            logger.info("Fetching students for course with id: {}", id);
            return ResponseEntity.ok(courseService.getCourseStudents(id));
        }
    }

