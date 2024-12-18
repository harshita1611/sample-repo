package com.students.studentProfile.repository;

import com.students.studentProfile.model.Student;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentRepository {

    private final Map<Integer, Student> studentEntries = new HashMap<>();

    public  Map<Integer,Student> getStudentEntries(){
        return studentEntries;
    }
}
