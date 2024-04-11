package org.nightfury.business_logic.service;

import java.sql.SQLException;
import java.util.List;
import org.nightfury.persistence.entity.GenericEntity;
import org.nightfury.persistence.repository.GenericRepository;
import org.nightfury.persistence.repository.exception.AlreadyExistsException;

public abstract class GenericService<T extends GenericEntity> implements Service<T> {

    protected final GenericRepository<T> genericRepository;

    public GenericService(GenericRepository<T> genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Override
    public void save(T entity) throws SQLException, AlreadyExistsException {
        genericRepository.save(entity);
    }

    @Override
    public List<T> findAll() throws SQLException {
        return genericRepository.findAll();
    }

    @Override
    public T findByID(int id) throws SQLException {
        return genericRepository.findByID(id);
    }

    @Override
    public T findByName(String name) throws SQLException {
        return genericRepository.findByName(name);
    }
}
