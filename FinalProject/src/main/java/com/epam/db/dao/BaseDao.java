package com.epam.db.dao;

import com.epam.entity.Entity;
import com.epam.exceptions.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseDao <K,T extends Entity> {
    List<T> findAllEntities() throws SQLException;

    Optional<T> findEntityById(K id) throws SQLException;

    boolean create(T t) throws DaoException, SQLException;

    boolean delete(K id) throws DaoException, SQLException;

    Optional<T> update(T t) throws DaoException;
}
