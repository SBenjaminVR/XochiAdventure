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
public class DBFunctions {
    
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
    
    public static void loadLevelFromDB(int level, Game game) throws SQLException {
        
        // Array of result set where it will be inserted the info of the levels
        ResultSet results[] = new ResultSet[10];
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
            results[0] = myStmt.executeQuery("select * from Levelg where levelID =" + level);
            results[1] = myStmt.executeQuery("select * from Fountain where levelID =" + level);
            results[2] = myStmt.executeQuery("select * from Chile where levelID =" + level);
            results[3] = myStmt.executeQuery("select * from Platform where levelID =" + level);
            results[4] = myStmt.executeQuery("select * from Comida where levelID =" + level);
            results[5] = myStmt.executeQuery("select * from Player where levelID =" + level);
            //myRs[6] = myStmt.executeQuery("select * from Pico where levelID =" + level);
            results[6] = myStmt.executeQuery("select * from Letrero where nivel =" + level);
               
      int posX;
      int posY;
      int iWidth;
      int iHeight;
      int speedX;
      int playerPlat;
      int lives;
      int left;
      int right;
      int direction;
      String tipo;
      // poner donde va a estar la fuente
      
      direction = 0;
      
      results[0].next();
      //game. = results[0].getInt("width");
      levelHeight = results[0].getInt("height");

      results[1].next();
      fuente.x = results[1].getInt("posX");
      fuente.y = results[1].getInt("posY");

      // chiles
      while (results[2].next()) {
        posX = results[2].getInt("posX");
        posY = results[2].getInt("posY");
        iWidth = results[2].getInt("width");
        iHeight = results[2].getInt("height");
        speedX = results[2].getInt("speedX");
        left = results[2].getInt("leftLimit");
        right = results[2].getInt("rightLimit");

        chiles.add(new Enemy(posX, posY, iWidth, iHeight, direction, speedX, left, right, this));
      }

      // plataformas
      while (results[3].next()) {
        posX = results[3].getInt("posX");
        posY = results[3].getInt("posY");
        iWidth = results[3].getInt("width");
        iHeight = results[3].getInt("height");

        platforms.add(new Platform(posX, posY, iWidth, iHeight, this));
      }

      int i = 1;
      // comida
      while (results[4].next()) {
        posX = results[4].getInt("posX");
        posY = results[4].getInt("posY");
        iWidth = results[4].getInt("width");
        iHeight = results[4].getInt("height");

        comidas.add(new Comida(posX, posY, iWidth, iHeight, i, this));
        i++;
      }

      // player
      results[5].next();
      posX = results[5].getInt("posX");
      posY = results[5].getInt("posY");
      iWidth = results[5].getInt("width");
      iHeight = results[5].getInt("height");
      speedX = results[5].getInt("speedX");
      lives = results[5].getInt("lives");

      player = new Player(posX, posY, iWidth, iHeight, speedX, lives, platforms.get(0), this);

      // pico
      while (results[6].next()) {
        posX = results[6].getInt("posX");
        posY = results[6].getInt("posY");
        tipo =  results[6].getString("dir");

        picos.add(new Pico(posX, posY, iWidth, iHeight, tipo, this));
      }

      // letreros
      while (results[7].next()) {
        posX = results[7].getInt("posX");
        posY = results[7].getInt("posY");
        lives = results[7].getInt("tipo");

        letreros.add(new Letrero(posX, posY, iWidth, iHeight, (lives == 1), this));
      }


        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myRs != null) {
                for (int i = 0; i < 7; i++) {
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
    }
}
