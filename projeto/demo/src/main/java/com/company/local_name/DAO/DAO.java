package com.company.local_name.DAO;

import java.util.List;
import java.sql.SQLException;

public interface DAO<T> {

    public void create(T object) throws SQLException;
    public T get(Object key) throws SQLException;
    public List<T> getAll() throws SQLException;
    public void update(T object) throws SQLException;
    public void delete(Object key) throws SQLException;
    
}
