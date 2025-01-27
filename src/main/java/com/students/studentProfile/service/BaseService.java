package com.students.studentProfile.service;
import java.util.List;

public interface BaseService <T, ID> {
    List<T> getAll();
    T getById(ID id);
    boolean create(T entity);
    void updateById(ID id, T entity);
    T delete(ID id);
}
