package com.example.studentmanager.DAO;

import com.example.studentmanager.Model.Classroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassroomDAO {
    private final Connection connection;

    private final String SELECT_ALL_CLASSROOM = "select * from classroom";
    private final String SELECT_CLASSROOM_BY_ID = "select * from classroom where id = ?";

    public ClassroomDAO() {
        connection = MyConnection.getConnection();
    }

    public List<Classroom> findAll() {
        List<Classroom> classrooms = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLASSROOM);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                classrooms.add(new Classroom(resultSet.getInt(1),
                        resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classrooms;
    }

    public Classroom findById(int id) {
        Classroom classroom = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLASSROOM_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classroom = new Classroom(resultSet.getInt(1),
                        resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classroom;
    }


}
