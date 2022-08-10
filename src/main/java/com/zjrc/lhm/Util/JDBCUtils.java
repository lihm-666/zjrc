package com.zjrc.lhm.Util;

import com.zjrc.lhm.Interface.Constants;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtils {

    //配置对象
    private static Properties prop = new Properties();
    /*
     * jdbc变量
     */
    static String driver = null;
    static String url = null;
    static String user = null;
    static String password = null;
    //thread封装Connection对象
    static ThreadLocal<Connection> thread = new ThreadLocal<Connection>();
    //唯一空构造器私有化，不允许外部创建对象
    private JDBCUtils () {}
    /**
     * 静态代码块为jdbc变量赋值
     * 因为静态代码块最先执行，所以调用getConnection()方法时，
     * 该方法内部的jdbc变量就完成了赋值操作
     */
    static {
        try {
            //动态获取配置文件的路径
            InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("Jdbc.properties");
            prop.load(in);//加载键值对信息
            /*
             * Constants常量接口中保存了很多常量，这些常量的值就是配置文件k-v数据的键
             *
             */
            driver = prop.getProperty(Constants.JDBC_DRIVER);
            url = prop.getProperty(Constants.JDBC_URL);
            user = prop.getProperty(Constants.JDBC_USER);
            password = prop.getProperty(Constants.JDBC_PASSWORD);
            /*
             * 加载驱动，静态代码块只执行一次，驱动只加载一次（加载驱动很耗性能的）
             */
            Class.forName(driver);//加载驱动
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 通过本方法客获取一个MySQL数据库的Connection对象
     *
     * @return Connection对象
     */
    public static Connection getConnection() {
        Connection connection = thread.get();
        if(connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                thread.set(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;//返回jdbc连接
    }
    //释放资源
    public static void close(Statement statement, Connection connection){
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (connection != null){
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        if (resultSet != null){
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (connection != null){
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static <T> List<T>  executeQuery(String sql,T t,Object...objs){
        //声明jdbc变量
        List<T> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            //给占位符赋值
            if(objs != null) {
                for(int i = 0;i<objs.length;i++) {
                    preparedStatement.setObject((i+1), objs[i]);
                }
            }
            //执行sql语句
            resultSet = preparedStatement.executeQuery();
            //获取结果集中字段的所有信息
            ResultSetMetaData rm = resultSet.getMetaData();
            int columnCount = rm.getColumnCount();//获取字段数
            //遍历结果集
            while(resultSet.next()) {
                Class<? extends Object> cla = t.getClass();//获取类对象
                T newInstance = (T) cla.newInstance();//获取类的对象
                //一个for循环封装一条记录的所有值
                for(int i = 1;i<=columnCount;i++) {
                    String columnName = rm.getColumnName(i);//获取字段名
                    //获取字段对应的setter方法
                    String methodName="set"+columnName.substring(0, 1).toUpperCase()+columnName.substring(1);
                    String columnClassName = rm.getColumnClassName(i);//获取字段java类型的完全限定名
                    //创建方法对象
                    Method method = cla.getDeclaredMethod(methodName, Class.forName(columnClassName));
                    method.invoke(newInstance,resultSet.getObject(columnName));//调用setter方法，执行对象属性赋值
                }
                list.add(newInstance);//将对象加入集合
            }
        } catch (Exception  e) {
            e.printStackTrace();
        }finally {
            //关流
            JDBCUtils.close(resultSet,preparedStatement,connection);
        }
        return list;

    }
}
