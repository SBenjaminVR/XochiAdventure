/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Alberto García Viegas A00822649 | Melba Geraldine Consuelos Fernández A01410921
 */
public class ReadFile {
//    private Game game;
//    
//    public ReadFile(Game game) {
//        this.game = game;
//    }
    
    public void readFile(Game game) {
        // Fichero del que queremos leer
        File file = new File("src/breakingbad/archivo.sf");
        Scanner s = null;
        
        int x, y, width, height, dirX, dirY, type, score, lives, endGame;
        
        /*
        player
        bricks
        ball
        powerUps
        game
        */
        try {
            // Leemos el contenido del fichero
            System.out.println("... Leemos el contenido del fichero ...");
            s = new Scanner(file);
            String linea = s.nextLine();
            
            //player
            x = Integer.parseInt(linea);
            width = Integer.parseInt(linea);
            height = Integer.parseInt(linea);
            
            game.getPlayer().setX(x);
            game.getPlayer().setWidth(width);
            System.out.println(game.getPlayer().getX() + " " + game.getPlayer().getWidth() +  "\n");

            //bricks
            for (int i = 0; i < game.getBricks().size(); i++) {
                x = Integer.parseInt(s.nextLine());
                if (x == 1) {
                    game.getBricks().get(i).setDestroyed(true);
                } else {
                    game.getBricks().get(i).setDestroyed(false);
                }
                System.out.println(""+ game.getBricks().get(i).isDestroyed());
            }

            //ball
            
            x = Integer.parseInt(linea);
            y = Integer.parseInt(linea);
            width = Integer.parseInt(linea);
            height = Integer.parseInt(linea);
            dirX = Integer.parseInt(linea);
            dirY = Integer.parseInt(linea);
            /*
            game.getBall().setX(x);
            game.getBall().setY(y);
            game.getBall().setWidth(width);
            game.getBall().setHeight(height);
            game.getBall().setDirectionX(dirX);
            game.getBall().setDirectionY(dirY);
            System.out.println(game.getBall().getX() + " " + game.getBall().getY() + " " + game.getBall().getWidth() + " " + game.getBall().getHeight() + " " + game.getBall().getDirectionX() + " " + game.getBall().getDirectionY() + " " + "\n");
            
            //powerUps
            /*
            for (int i = 0; i < game.getPowerUps().size(); i++) {
                x = Integer.parseInt(linea);
                y = Integer.parseInt(linea);
                width = Integer.parseInt(linea);
                height = Integer.parseInt(linea);
                type = Integer.parseInt(linea);
                
                game.getPowerUps().get(i).setX(x);
                game.getPowerUps().get(i).setY(y);
                game.getPowerUps().get(i).setWidth(width);
                game.getPowerUps().get(i).setHeight(height);
                game.getPowerUps().get(i).setType(type);
                System.out.println(game.getPowerUps().get(i).getX() + " " + game.getPowerUps().get(i).getY() + " " + game.getPowerUps().get(i).getWidth() + " " + game.getPowerUps().get(i).getHeight() + " " + game.getPowerUps().get(i).getType() + " " + "\n");
            }
            
            //pollos
            for (int i = 0; i < game.getPollos().size(); i++) {
                x = Integer.parseInt(linea);
                y = Integer.parseInt(linea);
                width = Integer.parseInt(linea);
                height = Integer.parseInt(linea);
                type = Integer.parseInt(linea);
                
                game.getPollos().get(i).setX(x);
                game.getPollos().get(i).setY(y);
                game.getPollos().get(i).setWidth(width);
                game.getPollos().get(i).setHeight(height);
                game.getPollos().get(i).setType(type);
            }
            */
            //game
            score = Integer.parseInt(linea);
            lives = Integer.parseInt(linea);
            endGame = Integer.parseInt(linea);
            
            game.setScore(score);
            game.setLives(lives);
            if (x == 1) {
                    game.setEndGame(true);
                } else {
                    game.setEndGame(false);
                }
            System.out.println(game.getScore() + " " + game.getLives() + " " + (game.isEndGame() ? "1" : "0") + "\n");

            // Leemos linea a linea el fichero
//            while (s.hasNextLine()) {
//                String linea = s.nextLine(); 	// Guardamos la linea en un String
//                System.out.println(linea);      // Imprimimos la linea
//            }

        } catch (Exception ex) {
            System.out.println("Mensaje: " + ex.getMessage());
        } finally {
            // Cerramos el fichero tanto si la lectura ha sido correcta o no
            try {
                if (s != null)
                s.close();
            } catch (Exception ex2) {
                System.out.println("Mensaje 2: " + ex2.getMessage());
            }
        }
    }
}
