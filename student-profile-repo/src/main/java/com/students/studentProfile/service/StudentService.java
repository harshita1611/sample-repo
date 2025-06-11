package com.students.studentProfile.service;

import com.students.studentProfile.dto.PaginatedResponse;
import com.students.studentProfile.enums.BatchEnum;
import com.students.studentProfile.enums.BatchEnumMap;
import com.students.studentProfile.model.Student;
import com.students.studentProfile.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

@Service
public class StudentService{

    private BatchEnumMap batchEnumMap;

    @Autowired
//    private  final  StudentRepository repository;
    private StudentRepository repository;

    public StudentService(StudentRepository repository){
        this.repository=repository;
    }

    private final Logger logger= LoggerFactory.getLogger(StudentService.class);


    /**
     *
     * @param page - decides page number
     * @param size - decides size of page
     * @return list of response which has {size} number of entries of sql table in page number {page}
     */
    public PaginatedResponse<Student> getPaginatedStudents(int page, int size) {
        List<Student> students = repository.getAllStudents(page, size);
        int totalRecords = repository.getTotalStudentsCount();
        return new PaginatedResponse<>(students, page, size, totalRecords);
    }
//    /**
//     * get list of all students - service layer
//     * @return
//     */
//    public List<Student> getAllStudents(int page, int size) {
//        return repository.getAllStudents(page,size);
//    }
//    @Override
//    public List<Student> fetchAll() {
//        return repository.getAllStudents();
//    }



    /**
     * Add a new student - service layer
     *
     * @param student
     * @return
     */
    public boolean createStudent(Student student) {
        logger.info("service layer");
        return repository.insertStudent(student);
    }
//    @Override
//    public boolean create(Student student) {
//        return repository.insertStudent(student);
//    }


    /**
     * get details of a particular Student - service layer
     *
     * @param id
     * @return
     */
    public Student getStudentById(Integer id) {
        return repository.getStudentById(id);
    }

    /**
     * update details of a particular student - service layer
     *
     * @param id
     * @param updatedEntry
     */
    public void updateStudentById(Integer id, Student updatedEntry) {
        repository.updateStudentById(id, updatedEntry);
    }

    /**
     * delete a particular student - service layer
     * @param id
     * @return
     */
    public Student deleteStudent(Integer id) {
        Student student = repository.getStudentById(id);
        if (repository.deleteStudentById(id)) {
            return student;
        }
        return null;
    }

    /**
     * Derive age from dob to save payload
     * @param dob
     * @return
     */
    public int DeriveAge(LocalDate dob){
        logger.info("inside derive age function");
        if (dob==null){
            throw new IllegalArgumentException("DOB cannot be null");
        }
        logger.info(" the age is {}",Period.between(dob,LocalDate.now()).getYears());
        return Period.between(dob,LocalDate.now()).getYears();
    }





}
