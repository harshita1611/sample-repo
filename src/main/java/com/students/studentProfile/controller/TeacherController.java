package com.students.studentProfile.controller;

import com.students.studentProfile.model.Student;
import com.students.studentProfile.model.Teacher;
import com.students.studentProfile.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("teachers")
public class TeacherController {

    private static final Logger logger = LoggerFactory.getLogger(StudentData.class);

    @Autowired
    private  TeacherService teacherService;

    @GetMapping
    public List<Teacher>getAllTeachers(){
        logger.info("inside get");
        return teacherService.getAllTeachers();
    }
}
