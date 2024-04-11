package org.nightfury.persistence.repository;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import org.nightfury.persistence.repository.exception.AlreadyExistsException;

public interface Repository<T> {

    void save(T entity) throws SQLException, AlreadyExistsException;

    List<T> findAll() throws SQLException;

    T findByID(int id) throws SQLException;

    T findByName(String name) throws SQLException;
}
