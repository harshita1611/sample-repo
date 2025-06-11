package com.students.studentProfile.service;

import com.students.studentProfile.model.Instructor;
import com.students.studentProfile.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository repository;

    /**
     * Retrieve all instructors.
     *
     * @return List of all instructors.
     */
    public List<Instructor> getAllInstructors() {
        return repository.getAllInstructors();
    }

    /**
     * Create a new instructor.
     *
     * @param instructor The instructor to be created.
     * @return Success flag.
     */
    public boolean createInstructor(Instructor instructor) {
        return repository.insertInstructor(instructor);
    }

    /**
     * Retrieve an instructor by ID.
     *
     * @param id The ID of the instructor.
     * @return The instructor object.
     */
    public Instructor getInstructorById(Integer id) {
        return repository.getInstructorById(id);
    }

    /**
     * Update an instructor by ID.
     *
     * @param id           The ID of the instructor to update.
     * @param updatedEntry The updated instructor data.
     */
    public void updateInstructorById(Integer id, Instructor updatedEntry) {
        repository.updateInstructorById(id, updatedEntry);
    }

    /**
     * Delete an instructor by ID.
     *
     * @param id The ID of the instructor to delete.
     * @return The deleted instructor object.
     */
    public Instructor deleteInstructor(Integer id) {
        Instructor instructor = repository.getInstructorById(id);
        if (repository.deleteInstructorById(id)) {
            return instructor;
        }
        return null;
    }
}
