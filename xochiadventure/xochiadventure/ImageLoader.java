/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author
 *      - Alberto García Viegas                 A00822649
 *      - Melba Geraldine Consuelos Fernández   A01410921
 *      - Humberto González Sánchez             A00822594
 *      - Benjamín Váldez Rodríguez             A00822027
 */
public class ImageLoader {
  /**
   * to get an image from the file path
   * @param path it is the path of the file
   * @return the <bold>BufferedImage</bold> object
   */
  public static BufferedImage loadImage(String path) {
    BufferedImage bi = null;
    try {
      bi = ImageIO.read(ImageLoader.class.getResource(path));
    } catch (IOException ioe) {
      System.out.println("Error loading image " + path + ioe.toString());
      System.exit(1);
    } catch (Exception ex ) {
    	System.out.println("Error loading image " + path + " " + ex.toString());
    	ex.printStackTrace();
    }
    return bi;
  }
}
