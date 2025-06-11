package com.students.studentProfile.service;


import com.students.studentProfile.model.Teacher;
import com.students.studentProfile.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repository;

    public List<Teacher>getAllTeachers(){
        return repository.getAllTeacher();
    }
}