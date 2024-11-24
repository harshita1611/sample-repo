package com.students.studentProfile.controller;

import com.students.studentProfile.student.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("students")
public class StudentData {

    private Map <Integer, Student> studentEntries = new HashMap<>();

    @GetMapping
    public List<Student> getALL(){
        return new ArrayList<>(studentEntries.values());
    }

    @PostMapping
    public boolean createStudent(@RequestBody Student newStudent){
        studentEntries.put(newStudent.getId(),newStudent);
        return true;
    }

    @GetMapping("id/{studentId}")
    public Student getStudentByID(@PathVariable("studentId") Integer studentId){
        return studentEntries.get(studentId);
    }

    @PutMapping("id/{id}")
    public Student updateStudentDetails(@PathVariable("id") Integer id , @RequestBody Student updatedEntry){
        return studentEntries.put(id,updatedEntry);
    }

    @DeleteMapping("id/{studentId}")
    public Student deleteStudentDetail(@PathVariable("studentId") Integer studentId){
        return studentEntries.remove(studentId);
    }

}
