package com.students.studentProfile.controller;

import com.students.studentProfile.model.Course;
import com.students.studentProfile.service.CourseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

    @RestController
    @RequestMapping("/api/courses")
    public class CourseController {
        private final Logger logger = LoggerFactory.getLogger(CourseController.class);

        @Autowired
        private CourseService courseService;

        /**
         * Get all courses
         * @return list of courses
         */
        @GetMapping
        public ResponseEntity<List<Course>> getAllCourses() {
            logger.info("Fetching all courses");
            return ResponseEntity.ok(courseService.getAllCourses());
        }

        /**
         * Add a new course
         *
         * @param newCourse
         * @return
         */
        @PostMapping
        public boolean createCourse(@Valid @RequestBody Course newCourse){
            logger.info("inside post courses");
            return courseService.createCourse(newCourse);
        }


        /**
         * Get course by id
         * @param id
         * @return
         */
        @GetMapping("/{id}")
        public ResponseEntity<Course> getCourseById(@PathVariable Integer id) {
            logger.info("Fetching course with id: {}", id);
            Course course = courseService.getCourseById(id);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(course);
        }


        /**
         * get students enrolled in particular course
         *
         * @param id
         * @return
         */
        @GetMapping("/{id}/students")
        public ResponseEntity<List<Map<String, Object>>> getCourseStudents(@PathVariable Integer id) {
            logger.info("Fetching students for course with id: {}", id);
            return ResponseEntity.ok(courseService.getCourseStudents(id));
        }
    }

