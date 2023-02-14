package com.example.studentmanager.DAO;

import com.example.studentmanager.Model.Classroom;
import com.example.studentmanager.Model.Student;
import com.example.studentmanager.Service.ClassroomService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private final Connection connection;

    private final ClassroomService classroomService;

    //các câu lệnh truy vấn tương ứng
    // ? : tham số cho các câu lệnh truy vấn, có vị trí tính từ 1 -> n
    private final String SELECT_ALL_STUDENT = "select * from student";
    private final String SELECT_STUDENT_BY_ID = "select * from student where id = ?";
    private final String INSERT_STUDENT = "insert into student(name, dateOfBirth, address,phoneNumber,email, classroom_id) value (?,?,?,?,?,?)";
    private final String UPDATE_STUDENT = "update student set name = ?, dateOfBirth = ?, address = ?,phoneNumber=?,email=?,classroom_id=? where id = ?";
    private final String DELETE_STUDENT = "delete from student where id = ?";
    private final String SEARCH_BY_NAME = "select * from student where name = ?";
    public StudentDAO() {
        connection = MyConnection.getConnection();
        classroomService = new ClassroomService();
    }


    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        //tạo đối tượng Statement: hỗ trợ truy vấn câu truy vấn tĩnh, không có tham số
        try (Statement statement = connection.createStatement();
             //tập hợp kết quả trả về được hứng bởi ResultSet
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_STUDENT)) {
            while (resultSet.next()) {
                Classroom classroom = classroomService.findById(resultSet.getInt(7));
                students.add(new Student(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3).toLocalDate(),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        classroom));
            }
            //bắt lỗi SQL vs SQLException
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student findById(int id) {
        Student student = null;
        //tạo đối tượng PreparedStatement: hỗ trợ thao tác với câu lệnh truy vấn động, có tham số
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID)) {
            //thêm tham số cho câu truy vấn với các hàm set giá trị tương ứng
            preparedStatement.setInt(1, id);
            //executeQuery: truy vấn đọc giữ liệu
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Classroom classroom = classroomService.findById(resultSet.getInt(5));
                student = new Student(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3).toLocalDate(),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        classroom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public void create(Student student) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setObject(2, student.getDateOfBirth());
            preparedStatement.setString(3, student.getAddress());
            preparedStatement.setString(4, student.getPhoneNumber());
            preparedStatement.setString(5, student.getEmail());
            preparedStatement.setInt(6, student.getClassroom().getId());
            //executeUpdate: truy vấn thay đổi dữ liệu. kết quả là số bản ghi được thay đổi
            int row1 = preparedStatement.executeUpdate();
            System.out.println("Create:" + row1);
//            preparedStatement.setString(1, "HelloWorld");
//            preparedStatement.setDouble(2, product.getPrice());
//            preparedStatement.setInt(3, product.getQuantity());
//            int row2 = preparedStatement.executeUpdate();
//            System.out.println("Create:" + row2);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void update(Student student) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setObject(2, student.getDateOfBirth());
            preparedStatement.setString(3, student.getAddress());
            preparedStatement.setString(4, student.getPhoneNumber());
            preparedStatement.setString(5, student.getEmail());
            preparedStatement.setInt(6, student.getClassroom().getId());
            preparedStatement.setInt(7, student.getId());
            int row = preparedStatement.executeUpdate();
            System.out.println("Update:" + row);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT)) {
            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();
            System.out.println("Delete:" + row);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Student> search(String name) {
        List<Student> students = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Classroom classroom = classroomService.findById(resultSet.getInt(5));
                students.add(new Student(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3).toLocalDate(),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        classroom));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
