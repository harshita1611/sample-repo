package com.students.studentProfile.controller;

import com.students.studentProfile.model.Instructor;
import com.students.studentProfile.service.InstructorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing instructor data.
 */
@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    private static final Logger logger = LoggerFactory.getLogger(InstructorController.class);

    @Autowired
    private InstructorService service;

    /**
     * Get all instructors.
     *
     * @return List of instructors.
     */
    @GetMapping
    public List<Instructor> getAllInstructors() {
        logger.info("Fetching all instructors");
        return service.getAllInstructors();
    }

    /**
     * Create a new instructor.
     *
     * @param newInstructor Instructor object to be created.
     * @return Success flag.
     */
    @PostMapping
    public boolean createInstructor(@Valid @RequestBody Instructor newInstructor) {
        logger.info("Creating new instructor: {}", newInstructor.getName());
        return service.createInstructor(newInstructor);
    }

    /**
     * Get instructor by ID.
     *
     * @param instructorId ID of the instructor.
     * @return Instructor object.
     */
    @GetMapping("id/{instructorId}")
    public Instructor getInstructorById(@PathVariable("instructorId") Integer instructorId) {
        logger.info("Fetching instructor with ID: {}", instructorId);
        return service.getInstructorById(instructorId);
    }

    /**
     * Update instructor details.
     *
     * @param id ID of the instructor.
     * @param updatedEntry Updated instructor data.
     * @return Success message.
     */
    @PutMapping("id/{id}")
    public String updateInstructorDetails(@PathVariable("id") Integer id, @RequestBody Instructor updatedEntry) {
        logger.info("Updating instructor with ID: {}", id);
        service.updateInstructorById(id, updatedEntry);
        logger.info("Updated instructor with ID: {}", id);
        return "Instructor updated successfully";
    }

    /**
     * Delete instructor details.
     *
     * @param instructorId ID of the instructor.
     * @return Deleted instructor object.
     */
    @DeleteMapping("id/{instructorId}")
    public Instructor deleteInstructorDetail(@PathVariable("instructorId") Integer instructorId) {
        logger.info("Deleting instructor with ID: {}", instructorId);
        return service.deleteInstructor(instructorId);
    }
}
