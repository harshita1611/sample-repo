package com.students.studentProfile.service;

import com.students.studentProfile.model.Student;
import com.students.studentProfile.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private  StudentRepository repository;

    private final Logger logger= LoggerFactory.getLogger(StudentService.class);

    public List<Student> getAllStudents() {
        return repository.getAllStudents();
    }

    public boolean createStudent(Student student) {
        return repository.insertStudent(student);
    }

    public Student getStudentById(Integer id) {
        return repository.getStudentById(id);
    }

    public void updateStudentById(Integer id, Student updatedEntry) {
        repository.updateStudentById(id, updatedEntry);
    }

    public Student deleteStudent(Integer id) {
        Student student = repository.getStudentById(id);
        if (repository.deleteStudentById(id)) {
            return student;
        }
        return null;
    }

    public int DeriveAge(LocalDate dob){
        logger.info("inside derive age function");
        if (dob==null){
            throw new IllegalArgumentException("DOB cannot be null");
        }
        logger.info(" the age is {}",Period.between(dob,LocalDate.now()).getYears());
        return Period.between(dob,LocalDate.now()).getYears();
    }

}
