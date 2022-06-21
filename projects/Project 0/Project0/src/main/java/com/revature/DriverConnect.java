package com.revature;

import com.revature.util.ConnectionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverConnect {

    //public static void main(String[] args){
    //changed from main to allow other classes to call
    public static void establishConnection(){

        try{
            Connection c = ConnectionUtil.getConnectionFromFile();
            System.out.println(c.getMetaData().getDriverName());
            c.close();
        }catch(SQLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
