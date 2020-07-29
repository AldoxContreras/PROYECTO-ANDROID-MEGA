package com.example.project;


import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMysql {
    String ip="192.168.100.197:3306";
    String bd="saos";
    String usuariobd="Aldo";
    String passbd="123456789";
    String url ="jdbc:mysql://" + ip+ "/" + bd;


    public Connection Conectar(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url,usuariobd,passbd);

        } catch (SQLException se) {
            Log.e("Error",se.getMessage());

        } catch (ClassNotFoundException e) {
            Log.e("ERROR",e.getMessage());
        }
        catch (Exception e){
            Log.e("Error",e.getMessage());
        }
        return conn;
    }


}