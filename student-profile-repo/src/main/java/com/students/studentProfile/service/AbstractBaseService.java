package com.students.studentProfile.service;
import com.students.studentProfile.service.BaseService;
import jakarta.persistence.Id;

import java.util.List;


public abstract class AbstractBaseService<T,ID> implements BaseService<T, ID> {
    protected abstract List<T> fetchAll();
    protected abstract boolean insert(T entity);


    @Override
    public List<T> getAll(){
        return fetchAll();
    }

    @Override
    public boolean create(T entity){
        return  insert(entity);
    }

}
