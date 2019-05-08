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
    
    public static ResultSet[] loadLevelFromDB(int level) throws SQLException {
        
        // Array of result set where it will be inserted the info of the levels
        ResultSet myRs[] = null;
        myRs = new ResultSet[10];

        // Connection to DB stuff
        Connection myConn = null;
        Statement myStmt = null;

        String user = "root";

        try {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xochidb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, "");

            // 2. Create a statement
            myStmt = myConn.createStatement();

            // 3. Execute SQL query
            myRs[0] = myStmt.executeQuery("select * from LevelF where levelID =" + level);
            myRs[1] = myStmt.executeQuery("select * from Fountain where levelID =" + level);
            myRs[2] = myStmt.executeQuery("select * from Chile where levelID =" + level);
            myRs[3] = myStmt.executeQuery("select * from Platform where levelID =" + level);
            myRs[4] = myStmt.executeQuery("select * from Comida where levelID =" + level);
            myRs[5] = myStmt.executeQuery("select * from Player where levelID =" + level);
            myRs[6] = myStmt.executeQuery("select * from Pico where levelID =" + level);
            myRs[7] = myStmt.executeQuery("select * from Letrero where levelID =" + level);

            return myRs;

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myRs != null) {
                for (int i = 0; i < 8; i++) {
                    myRs[i].close();
                }
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        }
        return myRs;
    }
}
