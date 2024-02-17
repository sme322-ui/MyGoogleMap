package com.pcschool.mygooglemap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBOpenHelper {
    private static String diver = "com.mysql.cj.jdbc.Driver";

    private static String url = "jdbc:mysql://192.168.1.104:3306/temp?characterEncoding=utf-8";
    private static String user = "root";
    private static String password = "Sme322820827";

    //連接數據庫
    public static Connection getConn(){
        Connection conn = null;
        try{
            Class.forName(diver);
            conn = (Connection) DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


}
