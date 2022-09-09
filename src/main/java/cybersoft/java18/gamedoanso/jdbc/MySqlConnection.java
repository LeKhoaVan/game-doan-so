package cybersoft.java18.gamedoanso.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
    private static String URL = "jdbc:mysql://localhost:3306/gamedoanso";
    private static String USERNAME = "root";
    private static String PASSWORD = "1234";

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(String.format("error while connect database: ",e));
        }
    }
}
