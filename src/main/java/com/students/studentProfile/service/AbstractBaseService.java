package com.students.studentProfile.service;
import com.students.studentProfile.service.BaseService;
import jakarta.persistence.Id;

import java.util.List;


public abstract class AbstractBaseService<T,ID> implements BaseService<T, ID> {
    protected abstract List<T> fetchAll();
    protected abstract T fetchById(ID id);
    protected abstract boolean insert(T entity);
    protected abstract void modifyById(ID id, T entity);
    protected abstract boolean remove(ID id);

    @Override
    public List<T> getAll(){
        return fetchAll();
    }

    @Override
    public T getById(ID id){
        return fetchById(id);
    }

    @Override
    public boolean create(T entity){
        return  insert(entity);
    }

    @Override
    public void updateById(ID id, T entity){
        modifyById(id,entity);
    }

    @Override
    public T delete(ID id){
        T entity=fetchById(id);
        if (remove(id)){
            return entity;
        }
        return null;
    }

}
