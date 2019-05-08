/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
/**
 *
 * @author Hglez
 */
public class recetarioDB {
    
    public static String getFood(int myFoodId) throws SQLException {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        String user = "root";
        String name = "oop";

        try {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xochidb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, "");

            // 2. Create a statement
            myStmt = myConn.createStatement();

            // 3. Execute SQL query
            myRs = myStmt.executeQuery("select foodName from food where foodId = " + Integer.toString(myFoodId));
            // 4. Process the result set
            myRs.next();                
            name = myRs.getString("foodName");
            return name;           

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
            return name;
            
        }        
    }  
    
    public static void getIngredients(int myFoodId, LinkedList<String> myIngredients) throws SQLException {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        String user = "root";

        try {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xochidb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, "");

            // 2. Create a statement
            myStmt = myConn.createStatement();

            // 3. Execute SQL query
            myRs = myStmt.executeQuery("select * from ingredients i join recipes r on i.ingredientId = r.ingredientId join food f on r.foodId = f.foodId where r.foodId = "+ Integer.toString(myFoodId));

            // 4. Process the result set
            while (myRs.next()) {
                myIngredients.add(myRs.getString("ingredientName"));
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        }
    }    
}
