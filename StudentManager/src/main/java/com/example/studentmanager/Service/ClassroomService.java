package com.example.studentmanager.Service;

import com.example.studentmanager.DAO.ClassroomDAO;
import com.example.studentmanager.Model.Classroom;

import java.util.List;

public class ClassroomService {
    private final ClassroomDAO classroomDAO;

    public ClassroomService() {
        classroomDAO = new ClassroomDAO();
    }

    public List<Classroom> findAll() {
        return classroomDAO.findAll();
    }

    public Classroom findById(int id) {
        return classroomDAO.findById(id);
    }
//
//    public void createCategory(Classroom category) {
//        categoryDAO.create(category);
//    }
//
//    public void updateCategory(Classroom category) {
//        categoryDAO.update(category);
//    }

//    public void deleteCategory(Long id) {
//        categoryDAO.delete(id);
//    }
}
