package com.epam.db.dao.impl;

import com.epam.criteria.PaymentCriteria;
import com.epam.db.dao.PaymentDao;
import com.epam.entity.Payment;
import com.epam.exceptions.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PaymentDaoImpl implements PaymentDao {
    @Override
    public List<Payment> findAllEntities() throws DaoException {
        return null;
    }

    @Override
    public Optional<Payment> findEntityById(Integer id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean create(Payment payment) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        return false;
    }

    @Override
    public Optional<Payment> update(Payment payment) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<Payment> findPaymentByCriteria(PaymentCriteria paymentCriteria) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Payment> findAllPaymentByCriteria(PaymentCriteria paymentCriteria) throws DaoException {
        return null;
    }
}
