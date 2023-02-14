package com.example.studentmanager.Controller;

import com.example.studentmanager.Model.Student;
import com.example.studentmanager.Service.ClassroomService;
import com.example.studentmanager.Service.StudentService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "StudentServlet", value = "/students")
public class StudentServlet extends HttpServlet {
    //tạo đối tượng Service để thao tác với cấu trúc dữ liệu lưu đối tượng
    private final StudentService studentService;
    private final ClassroomService classroomService;

    public StudentServlet() {
        studentService = new StudentService();
        classroomService = new ClassroomService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request parameter Action: nhằm quy định về hành động đang đang yêu cầu của client
        String action = request.getParameter("action");
        //tránh lỗi khi không có parameter Action đi kèm
        if (action == null) {
            action = "";
        }

        //dùng câu lệnh điều kiện để điều hướng Action tương ứng về với phương thức tương ứng
        switch (action) {
            case "delete":
                deleteStudent(request, response);
                break;
            case "detail":
                detailStudent(request, response);
                break;
            case "create":
                createForm(request, response);
                break;
            case "update":
                updateForm(request, response);
                break;
            default:
                displayStudentList(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //tương tự ở doGet, có thể tách phương thức đoạn code này để tái sử dụng
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "search":
                search(request,response);
                break;
            case "create":
                create(request, response);
                break;
            case "update":
                update(request, response);
                break;
            default:
                displayStudentList(request, response);
        }
    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name =request.getParameter("search");
        RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
        request.setAttribute("students", studentService.search(name));
        rd.forward(request, response);
    }

    //hiển thị tất cả sản phẩm
    private void displayStudentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
        request.setAttribute("students", studentService.findAll());
        rd.forward(request, response);
    }

    //hiển thị chi tiết 1 sản phẩm
    private void detailStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        RequestDispatcher rd = request.getRequestDispatcher("detail.jsp");
        request.setAttribute("student", studentService.findById(id));
        rd.forward(request, response);
    }

    //mở form update với giá trị thuộc tính của sản phẩm tương ứng
    private void createForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("create.jsp");
        request.setAttribute("classrooms", classroomService.findAll());
        rd.forward(request, response);
    }


    //nhận dữ liệu của tạo mới
    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String dateOfBirth = request.getParameter("dateOfBirth");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateOfBirth, formatter);
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        Integer classroomId = Integer.parseInt(request.getParameter("classroom"));
        studentService.createStudent(new Student(name, localDate, address,phoneNumber,email, classroomService.findById(classroomId)));
        //xử lý lỗi duplicate dữ liệu trong khi tạo hoặc sửa: điều hướng với Servlet tương ứng
        response.sendRedirect("/students");
    }

    //mở form update với giá trị thuộc tính của sản phẩm tương ứng
    private void updateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        RequestDispatcher rd = request.getRequestDispatcher("update.jsp");
        request.setAttribute("student", studentService.findById(id));
        request.setAttribute("classrooms", classroomService.findAll());
        rd.forward(request, response);
    }

    //nhận dữ liệu của chỉnh sửa thông tin sản phẩm theo id
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String dateOfBirth = request.getParameter("dateOfBirth");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateOfBirth, formatter);
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        Integer classroomId = Integer.parseInt(request.getParameter("classroom"));
        studentService.updateStudent(new Student(id,name, localDate, address,phoneNumber,email, classroomService.findById(classroomId)));
        response.sendRedirect("/students");
    }

    //xóa sản phẩm theo id
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        studentService.deleteStudent(id);
        response.sendRedirect("/students");
    }
}
