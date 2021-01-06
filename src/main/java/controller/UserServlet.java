package controller;

import model.User;
import service.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "User" ,urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;
    public void init(){
        userDAO=new UserDAO();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String choice=req.getParameter("action");
        if (choice==null){
            choice="";
        }
        switch (choice){
            case "create":
                showCreateUser(req,resp);
                break;
            case "edit":
                showEditUser(req,resp);
                break;
            case "delete":
                showDeleteUser(req,resp);
                break;
            default:
                showListUser(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String choice=req.getParameter("action");
        if (choice==null){
            choice="";
        }
        switch (choice){
            case "create":
                createUser(req,resp);
                break;
            case "edit":
                editUser(req,resp);
                break;
            case "delete":
                deleteUser(req,resp);
                break;
            default:
                showListUser(req, resp);
                break;
        }
    }

    private void showListUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        List<User> userList=userDAO.selectAllUsers();
        request.setAttribute("listUser",userList);
        RequestDispatcher dispatcher=request.getRequestDispatcher("Users/listUsers.jsp");
        dispatcher.forward(request,response);
    }
    private void showCreateUser(HttpServletRequest request,HttpServletResponse response){
        RequestDispatcher dispatcher=request.getRequestDispatcher("Users/CreateUser.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String country=request.getParameter("country");
        userDAO.insertUser(new User(name,email,country));

        request.setAttribute("message","Success create user");
        RequestDispatcher dispatcher=request.getRequestDispatcher("Users/CreateUser.jsp");
        dispatcher.forward(request,response);
    }

    private void showEditUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        User user=userDAO.selectUser(id);
        RequestDispatcher dispatcher;
        if (user==null){
            dispatcher=request.getRequestDispatcher("error-404.jsp");
        }else {
            dispatcher=request.getRequestDispatcher("Users/editUser.jsp");
            request.setAttribute("user",user);
            dispatcher.forward(request,response);
        }
    }
    private void editUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String country=request.getParameter("country");
        User user=userDAO.selectUser(id);
        RequestDispatcher dispatcher;
        if (user==null){
            dispatcher=request.getRequestDispatcher("error-404.jsp");
        }else {
            user.setName(name);
            user.setCountry(country);
            user.setEmail(email);
            try {
                userDAO.updateUser(user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            dispatcher=request.getRequestDispatcher("Users/editUser.jsp");
            request.setAttribute("message","Save success");
            request.setAttribute("user",user);
            dispatcher.forward(request,response);
        }
    }

    private void showDeleteUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        int id=Integer.parseInt(request.getParameter("id"));
        User user=userDAO.selectUser(id);
        if (user==null){
            dispatcher=request.getRequestDispatcher("error-404.jsp");
        }else {
            request.setAttribute("user",user);
            dispatcher=request.getRequestDispatcher("Users/showDeleteUser.jsp");
            dispatcher.forward(request,response);
        }
    }
    private void deleteUser(HttpServletRequest request,HttpServletResponse response){
        int id=Integer.parseInt(request.getParameter("id"));
        boolean result=false;
        String mess=null;
        RequestDispatcher dispatcher = request.getRequestDispatcher("Users/showDeleteUser.jsp");
        try {
             result=userDAO.deleteUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result){
            mess="Delete success";
        }else {mess="Delete  unsuccessful";}
        request.setAttribute("message",mess);
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
