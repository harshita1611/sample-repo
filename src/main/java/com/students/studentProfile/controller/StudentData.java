package com.students.studentProfile.controller;

import com.students.studentProfile.model.Student;
import com.students.studentProfile.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentData {

    @Autowired
    private StudentService service;


    @GetMapping
    public List<Student> getAllStudents(){
        return service.getAllStudents();
    }

    @PostMapping
    public boolean createStudent(@RequestBody Student newStudent){
        return service.createStudent(newStudent);
    }

    @GetMapping("id/{studentId}")
    public Student getStudentByID(@PathVariable("studentId") Integer studentId){
        return service.getStudentById(studentId);
    }

    @PutMapping("id/{id}")
    public String updateStudentDetails(@PathVariable("id") Integer id , @RequestBody Student updatedEntry){
        service.updateStudentById(id,updatedEntry);
        return "Data updated successfully";
    }

    @DeleteMapping("id/{studentId}")
    public Student deleteStudentDetail(@PathVariable("studentId") Integer studentId){
        return service.deleteStudent(studentId);
    }

}

