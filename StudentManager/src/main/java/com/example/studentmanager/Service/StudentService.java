package com.example.studentmanager.Service;

import com.example.studentmanager.DAO.StudentDAO;
import com.example.studentmanager.Model.Student;

import java.util.List;

public class StudentService {

    //chuyển sang dùng DAO thay vì list cứng
    private final StudentDAO studentDAO;
    public StudentService() {
        //tạo list ảo chứa đối tượng tương tác, sau sẽ thêm Db xử lý
        //id được xử lý tăng tự động khi tạo mới, tương đương với Db sau này
        studentDAO = new StudentDAO();
    }

    public List<Student> findAll() {
        return studentDAO.findAll();
    }

    public Student findById(int id) {
        return studentDAO.findById(id);
    }

    public void createStudent(Student student) {
        studentDAO.create(student);
    }

    public void updateStudent(Student student) {
        studentDAO.update(student);
    }

    public void deleteStudent(int id) {
        studentDAO.delete(id);
    }
    public List<Student> search(String name) {
        return studentDAO.search(name);
    }

}
