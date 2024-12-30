package com.students.studentProfile.model;


import jakarta.validation.constraints.*;

import java.util.List;

/**
 * Student model representing the data structure.
 */


public class Student {

    
    @NotBlank(message = "Name cannot be blank")
    @Size(min=2,max=50,message = "Name must be between 2 to 50 characters")
    private String name;

    @Email(message = "Email must be in correct format")
    @NotBlank(message = "Email cannot be blank")
    private  String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phone;

    @NotBlank(message = "Batch cannot be blank")
    private String batch;

    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 100, message = "Age must be less than or equal to 100")
    private int age;

    @NotBlank(message = "Date of birth cannot be blank")
    private  String dateOfBirth;


    @NotNull(message = "Course list cannot be null")
    private List<String> courseList;
     

    // Getter and Setter of name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter of email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter of phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter and Setter of batch
    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    // Getter and Setter of age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getter and Setter of date of birth
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Getter and Setter of list of course
    public List<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<String> courseList) {
        this.courseList = courseList;
    }
}
