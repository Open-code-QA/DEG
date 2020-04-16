/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

 
public class DataBase {

    public void A() throws SQLException {


        try (Connection c = DriverManager.getConnection("jdbc:postgresql://10.10.0.142/deg_stable", "test", "test")) {
            try (PreparedStatement query
                    = c.prepareStatement("SELECT * FROM address")) {
                ResultSet res = query.executeQuery();
                
                while (res.next()) {
                    System.out.println(res.getString("street")
                            //+ "   "
                            /* + rs.getString("LOGIN") + "  "
                            + rs.getString("PASSWORD") + "  "
                            + rs.getString("FIO") + "   "
                             */
                    );
                }
                res.close();
            }
        } catch (SQLException ex) {
            System.out.println("Database connection failure: "
                    + ex.getMessage());
        }

    }
}
