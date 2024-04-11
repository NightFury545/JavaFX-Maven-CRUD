package org.nightfury.persistence.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.nightfury.persistence.entity.GenericEntity;
import org.nightfury.persistence.repository.exception.AlreadyExistsException;
import org.nightfury.persistence.repository.mapper.RowMapper;

public abstract class GenericRepository<T extends GenericEntity> implements Repository<T> {

    protected Connection connection;
    private String tableName;
    private RowMapper<T> rowMapper;

    public GenericRepository(Connection connection, String tableName, RowMapper<T> rowMapper) {
        this.tableName = tableName;
        this.connection = connection;
        this.rowMapper = rowMapper;
    }

    @Override
    public void save(T entity) throws SQLException, AlreadyExistsException {
        List<Object> values = tableValues(entity);
        if (!existsByUuid(entity.getUuid())) {
            insert(values);
        } else {
            throw new AlreadyExistsException("Такий запис уже існує!");
        }

    }

    private void insert(List<Object> values) throws SQLException {
        List<String> attributes = tableAttributes();
        String attributesString = String.join(", ", attributes);
        String placeholders = Stream.generate(() -> "?")
            .limit(attributes.size())
            .collect(Collectors.joining(", "));
        String sql =
            "INSERT INTO " + tableName + " (" + attributesString + ") VALUES (" + placeholders
                + ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : values) {
                try {
                    preparedStatement.setObject(index, value);
                    index++;
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            preparedStatement.executeUpdate();
        }
    }

    private boolean existsByUuid(UUID uuid) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, uuid);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }

    @Override
    public List<T> findAll() throws SQLException {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(rowMapper.mapRow(resultSet));
                }
            }
        }
        return list;
    }

    @Override
    public T findByID(int id) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " LIMIT 1 OFFSET ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,
                id - 1);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return rowMapper.mapRow(resultSet);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public T findByName(String name) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return rowMapper.mapRow(resultSet);
                } else {
                    return null;
                }
            }
        }
    }

    protected abstract List<String> tableAttributes();

    protected abstract List<Object> tableValues(T entity);
}
