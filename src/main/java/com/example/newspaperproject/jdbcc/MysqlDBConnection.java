package com.example.newspaperproject.jdbcc;

import java.sql.*;
public class MysqlDBConnection
{
    public static Connection getMySQLDBConnection()
    {
        Connection con=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost/newspaperproject","root","rajneesh@358");
        }
        catch(Exception exp)
        {
            System.out.println(exp.toString());
        }
        return con;

    }
}
