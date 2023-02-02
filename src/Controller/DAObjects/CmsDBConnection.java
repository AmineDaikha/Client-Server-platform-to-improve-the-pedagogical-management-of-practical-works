package Controller.DAObjects;

import Controller.ErrEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by MedTaki on 29/03/2018.
 */
public class CmsDBConnection {
    private ErrEx error= new ErrEx();

    private String url = "jdbc:Mysql://localhost/lancmsdb";
    private String user = "root";
    private String password = "";

    //Connectiong to the database

    public Connection cmsDBConnecting() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");
            return connection;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            error.errMang(e);
            return null;
        }
    }
}
