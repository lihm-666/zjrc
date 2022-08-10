package com.zjrc.lhm.JDBC;

import com.zjrc.lhm.Server.Entry.Student;
import com.zjrc.lhm.Util.JDBCUtils;

import java.sql.*;
import java.util.List;

public class DatabaseConfig {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        /*
        //1.注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取数据库连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.44.128:3306/Maven_lhm", "new_user", "12345678");
        //4.定义sql语句
        String sql = "select * from Student where id = 1";
        //5.获取执行sql的对象Statement
        Statement statement = connection.createStatement();
        //6.执行sql
        ResultSet resultSet = statement.executeQuery(sql);
        Student student = new Student();
        while(resultSet.next()){
            student.setId(resultSet.getInt(1));
            student.setName(resultSet.getString(2));
            student.setAge(resultSet.getInt(3));
            student.setSex(resultSet.getString(4));
            student.setAddress(resultSet.getString(5));
            student.setEmail(resultSet.getString(6));
            student.setHobby(resultSet.getString(7));
            student.setPhone(resultSet.getString(8));
            student.setScore(resultSet.getInt(9));
        }
        System.out.println(student);
        //7.释放资源
        statement.close();
        connection.close();
         */
        String sql = "select * from Student where id = 1";
        List<Student> list = JDBCUtils.executeQuery(sql,new Student());
        System.out.println(list.get(0));
    }
}
