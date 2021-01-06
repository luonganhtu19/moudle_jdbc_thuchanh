package service;

import model.User;
import service.IUserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private String jdbcURL="jdbc:mysql://localhost:3306/demo_jdbc?useSSL=false";
    private String jdbcUsername="root";
    private String jdbcPassword="Tuan@1993";

    private static final  String INSERT_USER_SQL  ="INSERT INTO users"+"(name,email,country)Values"+"(?,?,?)";
    private static final  String SELECT_USER_BY_ID="select id,name,email,country from users where id=?";
    private static final  String SELECT_ALL_USERS ="select *from users";
    private static final  String DELETE_USER_SQL  ="delete from users where id=?";
    private static final  String UPDATE_USER_SQL  ="update users set name=?,email=?,country=? where id=?;";

    protected Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    @Override
    public void insertUser(User user)  {
        System.out.println(INSERT_USER_SQL);
        try(Connection connection=getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(INSERT_USER_SQL);) {
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getCountry());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            printSQLException(e);
        }
    }


    @Override
    public User selectUser(int id) {
        User user=null;
        try(Connection connection=getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SELECT_USER_BY_ID);){
            preparedStatement.setInt(1,id);
            System.out.println(SELECT_USER_BY_ID);
            ResultSet rs=preparedStatement.executeQuery();

            while (rs.next()){
                String name=rs.getString("Name");
                String email=rs.getString("email");
                String country=rs.getString("country");
                user=new User(id,name,email,country);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> users=new ArrayList<>();
        try(Connection connection=getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs=preparedStatement.executeQuery();

            while (rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String email=rs.getString("email");
                String country=rs.getString("country");
                users.add(new User(id,name,email,country));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean result;
        try(Connection connection=getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(DELETE_USER_SQL);) {
            preparedStatement.setInt(1,id);
            result=preparedStatement.executeUpdate()>0;
        }
        return result;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean result;
        try(Connection connection=getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_USER_SQL);) {
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getCountry());
            preparedStatement.setInt(4,user.getId());
            result=preparedStatement.executeUpdate()>0;
        }
        return result;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e:ex){
            if (e instanceof SQLException){
                e.printStackTrace(System.err);
                System.err.println("SQLState" +((SQLException)e).getSQLState());
                System.err.println("Error Code" +((SQLException)e).getSQLState());
                System.err.println("Message" +e.getMessage());
                Throwable t=ex.getCause();
                while (t!=null){
                    System.out.println("Cause:"+t);
                    t=t.getCause();
                }
            }
        }
    }
}
