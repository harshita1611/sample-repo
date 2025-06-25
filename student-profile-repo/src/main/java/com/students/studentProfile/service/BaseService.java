package com.students.studentProfile.service;
import java.util.List;

public interface BaseService <T, ID> {
    List<T> getAll();
    boolean create(T entity);
}
