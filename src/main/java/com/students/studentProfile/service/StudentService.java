package com.students.studentProfile.service;

import com.students.studentProfile.model.Student;
import com.students.studentProfile.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    public List<Student> getAllStudents(){
        return  new ArrayList<>(repository.getStudentEntries().values());
    }
    public boolean createStudent(Student newStudent){
        repository.getStudentEntries().put(newStudent.getId(),newStudent);
        return true;
    }

    public Student getStudentById(int id){
        return repository.getStudentEntries().get(id);
    }

    public Student updateStudentById(int id , Student updatedStudent){
        return repository.getStudentEntries().put(id,updatedStudent);
    }

    public Student deleteStudent(int id){
        return repository.getStudentEntries().remove(id);
    }
}
