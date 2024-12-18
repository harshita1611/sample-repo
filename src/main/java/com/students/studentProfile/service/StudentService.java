package com.students.studentProfile.service;

import com.students.studentProfile.model.Student;
import com.students.studentProfile.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for student operations.
 */

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository repository;

    public List<Student> getAllStudents(){
        logger.info("service: getting all student records");
        return  new ArrayList<>(repository.getStudentEntries().values());
    }

    public boolean createStudent(Student newStudent){
        logger.info("service: creating a new student");
        repository.getStudentEntries().put(newStudent.getId(),newStudent);
        return true;
    }

    public Student getStudentById(int id){
        logger.info("service: getting a student's detail by id");
        return repository.getStudentEntries().get(id);
    }

    public Student updateStudentById(int id , Student updatedStudent){
        logger.info("Service: updating data of a student by id");
        return repository.getStudentEntries().put(id,updatedStudent);
    }

    public Student deleteStudent(int id){
        logger.info("Service: deleting a students data by id");
        return repository.getStudentEntries().remove(id);
    }
}
