package com.pcschool.mygooglemap;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlCon {
    String url="192.168.1.106",db_user="root",db_password="root";
    public void insertData(String stringdata) {
       try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
           String data = null;
           String sql = "INSERT INTO `test` (`name`) VALUES ('" + data + "')";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            st.close();
            Log.v("DB", "寫入資料完成：" + data);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB", "寫入資料失敗");
            Log.e("DB", e.toString());
        }
    }

    public String getData() {
        return null;
    }

}



