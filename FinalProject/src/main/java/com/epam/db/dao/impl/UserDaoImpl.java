package com.epam.db.dao.impl;

import com.epam.criteria.UserCriteria;
import com.epam.db.ConnectionPool;
import com.epam.db.dao.UserDao;
import com.epam.entity.User;
import com.epam.entity.UserRole;
import com.epam.entity.UserStatus;
import com.epam.exceptions.DaoException;
import com.epam.exceptions.FileException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class UserDaoImpl implements UserDao {
    private final String SQL_SELECT_ALL = "SELECT * FROM USER";
    private final String SQL_SELECT_BY_CRITERIA = "SELECT * FROM USER WHERE ";
    private final String SQL_INSERT = "INSERT INTO User (login,password,name,email,role_fk,status_fk) VALUES(";
    private final String SQL_DELETE = "DELETE FROM User WHERE id = ";
    private final String SQL_UPDATE = "UPDATE User SET ";
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserDaoImpl.class);


    @Override
    public List<User> findAllEntities() throws DaoException{
        List<User> list = new ArrayList<>();
        Optional<Connection> connection = ConnectionPool.getInstance().getConnection();
        if(connection.isPresent()) {
            try {
                ResultSet resultSet = connection.get().createStatement().executeQuery(SQL_SELECT_ALL);
                while (resultSet.next()) {
                    if (getUser(resultSet).isPresent()) {
                        list.add(getUser(resultSet).get());
                    }
                }
                resultSet.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DaoException(e);
            } finally {
                ConnectionPool connectionPool = ConnectionPool.getInstance();
                connectionPool.close(connection.get());
                try {
                    connection.get().close();

                }catch(SQLException e){
                    throw new DaoException(e);
                }
            }
        }
        return list;
    }

    @Override
    public Optional<User> findEntityById(Integer id) throws DaoException{
        Optional<Connection> connection = ConnectionPool.getInstance().getConnection();
        Optional<User> user = Optional.empty();
        if(connection.isPresent()) {
            try {
                ResultSet resultSet = connection.get().createStatement().executeQuery(SQL_SELECT_BY_CRITERIA + "id = " + id);
                if (resultSet.next()) {
                    user = getUser(resultSet);
                }
                resultSet.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DaoException(e);
            } finally {
                ConnectionPool connectionPool = ConnectionPool.getInstance();
                connectionPool.close(connection.get());
                try {
                    connection.get().close();
                }catch(SQLException e){
                    throw new DaoException(e);
                }
            }
        }
        return user;
    }

    @Override
    public boolean create(User user) throws DaoException{
        boolean result = false;
        if(user!=null) {
            Optional<Connection> connection = ConnectionPool.getInstance().getConnection();
            if(connection.isPresent()) {
                try {
                    if (!connection.get().createStatement().executeQuery(SQL_SELECT_BY_CRITERIA + "login = '" + user.getLogin() + "'").next()) {
                        connection.get().createStatement().executeUpdate(SQL_INSERT + "'" + user.getLogin() + "','" + user.getPassword() + "','" + user.getName()
                                + "','" + user.getEmail() + "'," + UserRole.getIdByUserRole(UserRole.CLIENT) + "," + UserStatus.getIdByUserStatus(UserStatus.AVAILABLE) + ")");
                        result = true;
                        logger.info(user+" successfully created!");
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    throw new DaoException(e);
                } finally {
                    ConnectionPool connectionPool = ConnectionPool.getInstance();
                    connectionPool.close(connection.get());
                    try {
                        connection.get().close();
                    }catch(SQLException e){
                        throw new DaoException(e);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean delete(Integer id) throws DaoException{
        Optional<Connection> connection = ConnectionPool.getInstance().getConnection();
        boolean result = false;
        if(connection.isPresent()) {
            try {
                if (findEntityById(id).isPresent()) {
                    logger.info(findEntityById(id).get()+" successfully deleted!");
                    connection.get().createStatement().executeUpdate(SQL_DELETE + id);
                    result = true;
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DaoException(e);
            } finally {
                ConnectionPool connectionPool = ConnectionPool.getInstance();
                connectionPool.close(connection.get());
                try {
                    connection.get().close();
                }catch(SQLException e){
                    throw new DaoException(e);
                }
            }
        }
        return result;
    }

    @Override
    public Optional<User> update(User user) throws DaoException {
        Optional<User> userOptional = Optional.empty();
        Optional<Connection> connection = ConnectionPool.getInstance().getConnection();
        if(connection.isPresent()) {
            try {
                connection.get().createStatement().executeUpdate(SQL_UPDATE + "name = '" + user.getName()
                        + "', email = '" + user.getEmail() + "', password = '" + user.getPassword() + "' WHERE id = " + user.getId() + " AND login = '" + user.getLogin() + "'");
                userOptional = findEntityById(user.getId());
                logger.info(user +" successfully updated!");
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new DaoException(e);
            } finally {
                ConnectionPool connectionPool = ConnectionPool.getInstance();
                connectionPool.close(connection.get());
                try {
                    connection.get().close();
                }catch(SQLException e){
                    throw new DaoException(e);
                }
            }
        }
        return userOptional;
    }

    @Override
    public List<User> findAllUsersByCriteria(UserCriteria userCriteria) throws DaoException{
        List<User> list = new ArrayList<>();
            if(userCriteria.getName() != null) {
                list = chooseAllByPredicate(i -> ((User)i).getEmail().equals(userCriteria.getName()));
            }else{
                if(userCriteria.getStatus()!=null){
                    list = chooseAllByPredicate(i -> ((User)i).getStatus() == userCriteria.getStatus());
                }else{
                    if(userCriteria.getRole()!=null){
                        list = chooseAllByPredicate(i -> ((User)i).getRole() == userCriteria.getRole());
                    }
                }
            }
        return list;
    }

    @Override
    public Optional<User> findUserByCriteria(UserCriteria userCriteria) throws DaoException{
        Optional<User> user = Optional.empty();
        if(userCriteria.getEmail() != null){
            user = chooseByPredicate(i -> ((User)i).getEmail().equals(userCriteria.getEmail()));
        }else{
            if(userCriteria.getLogin() != null){
                user = chooseByPredicate(i -> ((User)i).getLogin().equals(userCriteria.getLogin()));
            }else{
                if(userCriteria.getPassword() != null){
                    user = chooseByPredicate(i -> ((User)i).getPassword().equals(userCriteria.getPassword()));
                }
            }
        }
        return user;
    }

    private Optional<User> chooseByPredicate(Predicate predicate) throws DaoException {
        Optional<User> user = findAllEntities()
                .stream()
                .filter(predicate)
                .findAny();
        return user;
    }

    private List<User> chooseAllByPredicate(Predicate predicate) throws DaoException {
        List<User> list = new ArrayList<>();
        findAllEntities()
                .stream()
                .filter(predicate)
                .forEach(i -> list.add((User)i));
        return list;
    }

    private Optional<User> getUser(ResultSet resultSet) throws DaoException {
        Optional<User> user = Optional.empty();
        try {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            UserStatus userStatus = null;
            if (UserStatus.extractUserStatusById(resultSet.getInt("status_fk")).isPresent()) {
                userStatus = UserStatus.extractUserStatusById(resultSet.getInt("status_fk")).get();
            }
            UserRole userRole = null;
            if (UserRole.extractUserRolebyId(resultSet.getInt("role_fk")).isPresent()) {
                userRole = UserRole.extractUserRolebyId(resultSet.getInt("role_fk")).get();
            }
            if (userRole != null && userStatus != null) {
                user = Optional.of(new User(login, password, email, name, id, userStatus, userRole));
            }
        }catch(SQLException e){
            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return user;
    }
}
