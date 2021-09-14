package xochiadventure;

import java.util.LinkedList;
import java.awt.Rectangle;
import java.awt.Graphics;

public class GameLevel {
  // Game logic variables
  private Player player;                     // to use a player
  private Rectangle rec;                     // to store the rectangle that checks which sprites are going to be drawn
  private Rectangle fuente;                  // to store the position of the fuente
  private int width;                    // to store the width of the level
  private int height;                   // to store the height of the level

  // Linked lists
  private LinkedList<Platform> platforms;    // to store all the platforms
  private LinkedList<Enemy> chiles;          // to store all the enemies
  private LinkedList<PowerUps> powerups;     // to store all the powerups
  private LinkedList<Comida> comidas;        // to store all the food
  private LinkedList<Comida> recolectado;    // to store the recolected food
  private LinkedList<Shot> disparos;         // to store all the shot bubbles
  private LinkedList<Pico> picos;            // to store all the spikes
  private LinkedList<Letrero> letreros;      // to store all letreros

  // UI
  private int playerX;                       // to store the position in which the player will be drawn
  private int playerY;                       // to store the position in which the player will be drawn

  private int nivel;

  private Game game;

  public GameLevel(Game game) {
    // Initialization on linked lists
    chiles = new LinkedList<Enemy>();
    powerups = new LinkedList<PowerUps>();
    platforms = new LinkedList<Platform>();
    comidas = new LinkedList<Comida>();
    recolectado = new LinkedList<Comida>();
    disparos = new LinkedList<Shot>();
    picos = new LinkedList<Pico>();
    letreros = new LinkedList<Letrero>();

    // rec = new Rectangle(0, 0, getWidth(), getHeight());
    rec = new Rectangle(0, 0, game.getWidth(), game.getHeight());
    fuente = new Rectangle(0, 0, 300, 300);

    nivel = game.getNivel();

    this.game = game;
  }

  public Rectangle getRec() {
    return rec;
  }

  public int getNivel() {
    return nivel;
  }

  public int getWidth() {
    return width;
  }
  public int getHeight() {
    return height;
  }


  public void tick(KeyManager keyManager) {
    // se tickea al jugador
    player.tick(keyManager);

    /**
     * Como el rec se encarga de ver que sprite se van a dibujar en la pantalla, este sigue al jugador,
     * por lo que hay que checar dos condiciones:
     * - que el jugador esté en las orillas del nivel
     * - que el jugador no esté en las orillas del nivel
     * En el caso que la primera condicional se cumpla, solo actualizamos la 'y' del rec para que se pueda dibujar todo
     * En el caso que no se actualizan la 'x' y la 'y' del rec para que así pueda seguir al jugador
     */
    if ((player.getX() < playerX || player.getX() + player.getWidth() > width - playerX) && player.getY() + player.getHeight() > height - 100 - playerY) {
      rec.setRect(rec.x, rec.y, getWidth(), getHeight());
    } else if (player.getX() < playerX || player.getX() + player.getWidth()> width - playerX) {
      rec.setRect(rec.x, player.getY() - playerY, getWidth(), getHeight());
    } else if (player.getY() + player.getHeight() > height - 100 - playerY) {
      rec.setRect(player.getX() - playerX, rec.y, getWidth(), getHeight());
    } else {
      rec.setRect(player.getX() - playerX, player.getY() - playerY, getWidth(), getHeight());
    }

    // checks if the player is in the fountain so it can regain water
    if (fuente.intersects(player.getPerimetro()) && player.getWater() < 100) {
      player.setWater(player.getWater() + 1);
    }

    // checks if the player collides with a spike
    for (int i = 0; i < picos.size(); i++) {
      Pico pi = picos.get(i);
      if (pi.intersectaJugador(player) && player.getContGotHit() == 0) {
        player.setLives(player.getLives() - 1);
        if (game.isEffectsOn())
          Assets.hurtSnd.play();
        player.setContGotHit(60);
      }
    }

    // checks if the player can shoot a bubble and if one the keyw for doing it was pressed
    if (player.getWater() > 0 && (keyManager.z || keyManager.o)) {
      if (game.isEffectsOn()) {
        Assets.shootSnd.play();
      }
      // checks in which direction the player is moving to know in which direction shoot the bubble
      if (player.getDirection() == 1) {
        // attack to the right
        disparos.add(new Shot(player.getX() + player.getWidth(), player.getY() + player.getHeight() / 2, 50, 50, 12, 1, this));
      } else {
        // attack to the left
        disparos.add(new Shot(player.getX(), player.getY() + player.getHeight() / 2, 50, 50, 12, -1, this));
      }
      player.setWater(player.getWater() - 10);
    }

    // bubbles are ticked
    for (int j = 0; j < disparos.size(); j++) {
      Shot disp = disparos.get(j);
      disp.tick(keyManager);
      if (disp.getX() + disp.getWidth() <= 0 || disp.getX() >= 3100) {
        disparos.remove(j);
      }
    }


    // chiles are ticke
    for (int i  = 0; i < chiles.size(); i++) {
      Enemy chile = chiles.get(i);
      chile.tick(keyManager);

      // se checa que los disparos colisionen con los chiles
      for (int j = 0; j < disparos.size(); j++) {
        Shot disp = disparos.get(j);

        // si colisionan elimina el chile y el disparo, aparte de checar si el chile soltará algún power up o no
        if (disp.intersectaChile(chile)) {
          chiles.remove(i);
          disparos.remove(j);
          int max = 100;
          int min = 0;
          double numerito = (Math.random() * ((max - min) + 1)) + min;
          if (numerito < 50) {
            powerups.add(new PowerUps(chile.getX(), chile.getY(), 50, 50, this));
          }
        }
      }

      if (chile.intersectaJugador(player) && player.getContGotHit() == 0) {
        // quitarle vida al jugador
        player.setLives(player.getLives() - 1);
        if (game.isEffectsOn())
          Assets.hurtSnd.play();
        player.setContGotHit(60);
      }
    }

    // se tickea a los powerups
    for (int i  = 0; i < powerups.size(); i++) {
      PowerUps power = powerups.get(i);
      power.tick(keyManager);
      if (power.intersectaJugador(player)) {
        switch (power.getType()) {
          case ATOLE:
            // Recover all of the player hp/lives
            player.setLives(player.getMaxLives());
            if (game.isEffectsOn())
              Assets.atoleSnd.play();
            powerups.remove(i);
            break;
          case AGUA:
            // Refill a little bit the players ammo
            player.setWater(player.getWater() + 25);
            if (player.getWater() > 100) {
              player.setWater(100);
            }
            powerups.remove(i);
            break;

          case DULCE:
            // Recover 1 life
            if (player.getLives() < player.getMaxLives())
              player.setLives(player.getLives() + 1);
            if (game.isEffectsOn())
              Assets.dulceSnd.play();
            powerups.remove(i);
            break;
          default:
            break;
        }
      }
    }

    // Checks if the player is in the air
    if (player.isInTheAir()) {
      for (int i  = 0; i < platforms.size(); i++) {
        Platform platf = platforms.get(i);

        // Checks if the player collides with a platform
        if (platf.intersectaJugador(player)) {

          // Checks if the player is above or under the platform
          // If it is above, it sets that platform as the platform the player is on top and the player stops falling
          // Else, the player stops going upwards
          if (player.getSpeedY() <= 0) {
            player.setInTheAir(false);
            player.setSpeedY(0);
            player.setPlat(platf);
            player.setY(player.getPlat().getY() - player.getHeight());
          } else {
            player.setSpeedY(0);
            player.setY(platf.getY() + platf.getHeight() + 1);
          }
        }
      }
    }

    // Ingredients are ticked
    for (int i = 0; i < comidas.size(); i++) {
      Comida comi = comidas.get(i);
      
      // If the player collects an ingredient, it is removed form the 
      // game and added to its collection of collected ingredients
      if (comi.intersectaJugador(player)) {
        comidas.remove(i);
        recolectado.add(comi);
      }
    }

    // Checks if the player fell of the level
    if (player.getY() >= height) {

      // Sets that the player no longer will be moving in the air
      player.setSpeedY(0);
      player.setInTheAir(false);

      // Checks if the player is in a state of vulnerability
      // If it is not, the player loses one life 
      if (player.getContGotHit() == 0) {
        player.setLives(player.getLives() - 1);
      }
      // Resets the player's position on top of the last platform it was on
      int dif = (player.getX() < player.getPlat().getX()) ? 0 : player.getPlat().getWidth() - player.getWidth();
      player.setX(player.getPlat().getX() + dif);
      player.setY(player.getPlat().getY() - player.getHeight());
      player.setContGotHit(60);
    }

    // Checks if the player has won or lost the game
    if (comidas.isEmpty() || player.getLives() == 0) {
      game.setEndGame(true);
    }
  }

  public void render(Graphics g) {
    // BACKGROUND
    g.drawImage(Assets.background, 0, 0, game.getWidth(), game.getHeight(), null);

    // GAME

    // Draw the platforms
    for (int i = 0; i < platforms.size(); i++) {
      Platform platform = platforms.get(i);
      // System.out.println("platform" + i + " " + (rec.intersects(platform.getPerimetro())));
      if (rec.intersects(platform.getPerimetro())) {
        platform.render(g);
      }
    }

    // Draw the fountain

    
    g.drawImage(Assets.fuente, (fuente.x - rec.x), (fuente.y - rec.y), fuente.width, fuente.height, null);
    

    // dibujar los chiles
    for (int i = 0; i < chiles.size(); i++) {
      Enemy chile = chiles.get(i);
      if (rec.intersects(chile.getPerimetro())) {
        chile.render(g);
      }
    }

    for (int i = 0; i < picos.size(); i++) {
      Pico pi = picos.get(i);
      if (rec.intersects(pi.getPerimetro())) {
        pi.render(g);
      }
    }

    // dibujar los powerups
    for (int i = 0; i < powerups.size(); i++) {
      PowerUps powerup = powerups.get(i);
      if (rec.intersects(powerup.getPerimetro())) {
        powerup.render(g);
      }
    }

    // dibujar comidas
    for (int i = 0; i < comidas.size(); i++) {
      Comida comida = comidas.get(i);
      if (rec.intersects(comida.getPerimetro())) {
        comida.render(g);
      }
    }

    // Draw bubbles
    for (int i = 0; i < disparos.size(); i++) {
      Shot disp = disparos.get(i);
      if (rec.intersects(disp.getPerimetro())) {
        disp.render(g);
      }
    }

    // Draw signs
    for (int i = 0; i < letreros.size(); i++) {
      Letrero sign = letreros.get(i);
      if (rec.intersects(sign.getPerimetro())) {
        sign.render(g);
      }
    }

    // draw player
    player.render(g);

    // UI

    // lives
    for (int i = 0; i < player.getLives(); i++) {
      g.drawImage(Assets.heart, 0 + i * 60, 0, 50 , 50, null); // PLACEHOLDER
    }

    // bubbles
    for (int i = 0; i < 100 - (100 - player.getWater()); i+=10) {
      g.drawImage(Assets.shot, 0 + i * 6, 60, 50 , 50, null); // PLACEHOLDER
    }

    int iPosX = game.getWidth() - 55;
    int iPosY = 20;

    // dibujar ingredientes recolectados
    for (int i = 0; i < recolectado.size(); i++) {
      Comida recol = recolectado.get(i);
      recol.renderUI(g, iPosX, iPosY);
      iPosX -= 55;
    }

    if (game.isEndGame()) {
      // g.setFont(texto);
      if (comidas.isEmpty()) {
        // you won
        g.drawImage(Assets.ganado, 0, 0, game.getWidth(), game.getHeight(), null);
      } else if (player.getLives() == 0) {
        // you lost
        g.drawImage(Assets.perdido, 0, 0, game.getWidth(), game.getHeight(), null);
      }
    }
  }

  /**
   *  To unload a level
   */
  public void unloadLevel() {
    player = null;
    chiles.clear();
    platforms.clear();
    comidas.clear();
    powerups.clear();
    recolectado.clear();
    disparos.clear();
    picos.clear();
    letreros.clear();
  }

  public void loadLevel(int nivel) {
    switch(nivel) {
      case 1:
        fuente.x = 1400;
        fuente.y = 500;
        chiles.add(new Enemy(1350, 200, 50, 50, 1, 5, 1300, 1550, this));
        chiles.add(new Enemy(1750, 200, 50, 50, -1, 5, 1550, 1800, this));
        chiles.add(new Enemy(955, 1300, 50, 50, 1, 5, 950, 1450, this));
        chiles.add(new Enemy(2100, 1300, 50, 50, -1, 5, 1650, 2150, this));
        chiles.add(new Enemy(955, 1850, 50, 50, 1, 5, 950, 1550, this));
        chiles.add(new Enemy(2100, 1850, 50, 50, -1, 5, 1550, 2150, this));

        // grandes 3
        platforms.add(new Platform(0,     250, 500, 100, this));
        platforms.add(new Platform(1300,  250, 500, 100, this));
        platforms.add(new Platform(2600,  250, 500, 100, this));

        // chicas 2
        platforms.add(new Platform(650,   500, 500, 100, this));
        platforms.add(new Platform(1950,  500, 500, 100, this));

        // 2500 5
        platforms.add(new Platform(300,   800, 500, 100, this));
        platforms.add(new Platform(800,   800, 500, 100, this));
        platforms.add(new Platform(1300,  800, 500, 100, this));
        platforms.add(new Platform(1800,  800, 500, 100, this));
        platforms.add(new Platform(2300,  800, 500, 100, this));

        // chicas 3
        platforms.add(new Platform(0,     1100, 150, 30, this));
        platforms.add(new Platform(1475,  1100, 150, 30, this));
        platforms.add(new Platform(2950,  1100, 150, 30, this));

        // grandes 4
        platforms.add(new Platform(0,     1350, 500, 100, this));
        platforms.add(new Platform(950,   1350, 500, 100, this));
        platforms.add(new Platform(1650,  1350, 500, 100, this));
        platforms.add(new Platform(2600,  1350, 500, 100, this));

        // chicas 2
        platforms.add(new Platform(650,   1650, 150, 30, this));
        platforms.add(new Platform(2300,  1650, 150, 30, this));

        // grandes 5
        platforms.add(new Platform(0,     1900, 500, 100, this));
        platforms.add(new Platform(950,   1900, 500, 100, this));
        platforms.add(new Platform(1450,  1900, 500, 100, this));
        platforms.add(new Platform(1650,  1900, 500, 100, this));
        platforms.add(new Platform(2600,  1900, 500, 100, this));

        // comidas
        comidas.add(new Comida(225, 200, 50, 50, 0, this));
        comidas.add(new Comida(1525, 200, 50, 50, 1, this));
        comidas.add(new Comida(2925, 200, 50, 50, 2, this));
        comidas.add(new Comida(1525, 1050, 50, 50, 3, this));
        comidas.add(new Comida(225, 1850, 50, 50, 4, this));
        comidas.add(new Comida(1525, 1850, 50, 50, 5, this));
        comidas.add(new Comida(2925, 1850, 50, 50, 6, this));

        // letreros
        letreros.add(new Letrero(300, 730, 70, 70, false, this));
        letreros.add(new Letrero(2730, 730, 70, 70, false, this));
        letreros.add(new Letrero(430, 1830, 70, 70, true, this));
        letreros.add(new Letrero(950, 1830, 70, 70, true, this));
        letreros.add(new Letrero(2080, 1830, 70, 70, true, this));
        letreros.add(new Letrero(2600, 1830, 70, 70, true, this));

        player = new Player (1475, 650, 100, 100, 6, 4, platforms.get(0), this);
        width = 3100;
        height = 2100;

        break;

      case 2:

        fuente.x = 1400;
        fuente.y = 500;
        // Chiles
        
        chiles.add(new Enemy(10, 200, 50, 50, 1, 5, 0, 500, this));
        chiles.add(new Enemy(3040, 200, 50, 50, -1, 5, 2600, 3100, this));
        chiles.add(new Enemy(310, 750, 50, 50, 1, 5, 300, 800, this));
        chiles.add(new Enemy(2740, 750, 50, 50, -1, 5, 2300, 2800, this));
        chiles.add(new Enemy(810, 1050, 50, 50, 1, 5, 800, 1300, this));
        chiles.add(new Enemy(2240, 1050, 50, 50, -1, 5, 1800, 2300, this));
        chiles.add(new Enemy(10, 1650, 50, 50, 1, 5, 0, 400, this));
        chiles.add(new Enemy(3040, 1650, 50, 50, -1, 5, 2700, 3100, this));

        // Plataformas
        platforms.add(new Platform(0, 250, 500, 100, this));
        platforms.add(new Platform(1300, 250, 500, 100, this));
        platforms.add(new Platform(2600, 250, 500, 100, this));

        platforms.add(new Platform(650, 500, 500, 100, this));
        platforms.add(new Platform(1950, 500, 500, 100, this));

        platforms.add(new Platform(-400, 800, 500, 100, this));
        platforms.add(new Platform(300, 800, 500, 100, this));
        // platforms.add(new Platform(1000, 800, 1100, 100, this));
        platforms.add(new Platform(1000, 800, 500, 100, this));
        platforms.add(new Platform(1500, 800, 500, 100, this));
        platforms.add(new Platform(1600, 800, 500, 100, this));
        platforms.add(new Platform(2300, 800, 500, 100, this));
        platforms.add(new Platform(3000, 800, 500, 100, this));

        platforms.add(new Platform(800, 1100, 500, 100, this));
        platforms.add(new Platform(1800, 1100, 500, 100, this));

        platforms.add(new Platform(550, 1400, 150, 30, this));
        platforms.add(new Platform(1475, 1400, 150, 30, this));
        platforms.add(new Platform(2400, 1400, 150, 30, this));

        platforms.add(new Platform(0, 1700, 400, 100, this));
        platforms.add(new Platform(575, 1700, 300, 100, this));
        platforms.add(new Platform(1050, 1700, 300, 100, this));
        platforms.add(new Platform(1500, 1700, 100, 20, this));
        platforms.add(new Platform(1750, 1700, 300, 100, this));
        platforms.add(new Platform(2225, 1700, 300, 100, this));
        platforms.add(new Platform(2700, 1700, 400, 100, this));

        // Comidas

        comidas.add(new Comida(1525, 200, 50, 50, 0, this));
        comidas.add(new Comida(2900, 200, 50, 50, 1, this));
        comidas.add(new Comida(200, 825, 50, 50, 2, this));
        comidas.add(new Comida(2900, 825, 50, 50, 3, this));
        comidas.add(new Comida(50, 1650, 50, 50,  4, this));
        comidas.add(new Comida(1525, 1650, 50, 50, 5, this));

        // letreros
        letreros.add(new Letrero(30, 730, 70, 70, false, this));
        letreros.add(new Letrero(3000, 730, 70, 70, false, this));
        letreros.add(new Letrero(330, 1630, 70, 70, true, this));
        letreros.add(new Letrero(1280, 1630, 70, 70, true, this));
        letreros.add(new Letrero(1750, 1630, 70, 70, true, this));
        letreros.add(new Letrero(2700, 1630, 70, 70, true, this));

        // picos
        picos.add(new Pico(575, 1660, 50, 50, "u", this));
        picos.add(new Pico(825, 1660, 50, 50, "u", this));
        picos.add(new Pico(1175, 1660, 50, 50, "u", this));
        picos.add(new Pico(1875, 1660, 50, 50, "u", this));
        picos.add(new Pico(2225, 1660, 50, 50, "u", this));
        picos.add(new Pico(2475, 1660, 50, 50, "u", this));

        player = new Player (1475, 650, 100, 100, 6, 4, platforms.get(0),  this);
        width = 3100;
        height = 1900;
        break;

      case 3:
        // 3300
        fuente.x = 1500;
        fuente.y = 600;

        // chiles
        chiles.add(new Enemy(240, 200, 50, 50, 1, 5, 225, 825, this));
        chiles.add(new Enemy(1350, 200, 50, 50, -1, 5, 825, 1425, this));
        chiles.add(new Enemy(1890, 200, 50, 50, 1, 5, 1875, 2475, this));
        chiles.add(new Enemy(3060, 200, 50, 50, -1, 5, 2475, 3075, this));
        chiles.add(new Enemy(460, 850, 50, 50, 1, 5, 450, 950, this));
        chiles.add(new Enemy(2790, 850, 50, 50, -1, 5, 2350, 2850, this));
        chiles.add(new Enemy(2000, 1400, 50, 50, -1, 5, 1975, 2775, this));
        chiles.add(new Enemy(0, 1950, 50, 50, 1, 5, 0, 1000, this));
        chiles.add(new Enemy(3250, 1950, 50, 50, -1, 5, 2300, 3300, this));

        // plataformas
        platforms.add(new Platform(225, 250, 500, 100, this));
        platforms.add(new Platform(725, 250, 500, 100, this));
        platforms.add(new Platform(925, 250, 500, 100, this));
        platforms.add(new Platform(1875, 250, 500, 100, this));
        platforms.add(new Platform(2375, 250, 500, 100, this));
        platforms.add(new Platform(2575, 250, 500, 100, this));

        platforms.add(new Platform(1575, 500, 150, 30, this));

        platforms.add(new Platform(1300, 700, 150, 30, this));
        platforms.add(new Platform(1850, 700, 150, 30, this));

        
        platforms.add(new Platform(300, 950, 50, 50, this));
        platforms.add(new Platform(450, 900, 500, 100, this));
        platforms.add(new Platform(1150, 900, 50, 50, this));
        platforms.add(new Platform(1400, 900, 500, 100, this));
        platforms.add(new Platform(2100, 900, 50, 50, this));
        platforms.add(new Platform(2350, 900, 500, 100, this));
        platforms.add(new Platform(2950, 950, 50, 50, this));

        platforms.add(new Platform(1100, 1150, 150, 30, this));
        platforms.add(new Platform(2050, 1150, 150, 30, this));

        platforms.add(new Platform(50, 1500, 50, 50, this));
        platforms.add(new Platform(400, 1500, 50, 50, this));
        platforms.add(new Platform(575, 1450, 500, 100, this));
        platforms.add(new Platform(875, 1450, 500, 100, this));
        platforms.add(new Platform(1975, 1450, 800, 100, this));
        platforms.add(new Platform(2850, 1700, 150, 30, this));

        platforms.add(new Platform(1575, 1650, 150, 30, this));

        platforms.add(new Platform(0, 2000, 500, 100, this));
        platforms.add(new Platform(500, 2000, 500, 100, this));
        platforms.add(new Platform(1250, 2000, 50, 50, this));
        platforms.add(new Platform(1600, 2000, 100, 100, this));
        platforms.add(new Platform(2000, 2000, 50, 50, this));
        platforms.add(new Platform(2300, 2000, 500, 100, this));
        platforms.add(new Platform(2800, 2000, 500, 100, this));

        // comidas
        comidas.add(new Comida(800, 200, 50, 50, 0, this));
        comidas.add(new Comida(300, 750, 50, 50, 1, this));
        comidas.add(new Comida(2950, 750, 50, 50, 2, this));
        comidas.add(new Comida(500, 1950, 50, 50, 3, this));
        comidas.add(new Comida(2800, 1950, 50, 50, 4, this));

        comidas.add(new Comida(1625, 1950, 50, 50, 5, this));

        // letreros
        letreros.add(new Letrero(225, 180, 70, 70, false, this));
        letreros.add(new Letrero(3005, 180, 70, 70, false, this));
        letreros.add(new Letrero(450, 830, 70, 70, false, this));
        letreros.add(new Letrero(2780, 830, 70, 70, false, this));
        letreros.add(new Letrero(930, 1930, 70, 70, true, this));
        letreros.add(new Letrero(2300, 1930, 70, 70, true, this));

        // picos
        picos.add(new Pico(300, 910, 50, 50, "u", this));
        picos.add(new Pico(300, 990, 50, 50, "d", this));
        picos.add(new Pico(260, 950, 50, 50, "l", this));
        picos.add(new Pico(340, 950, 50, 50, "r", this));

        picos.add(new Pico(1150, 860, 50, 50, "u", this));
        picos.add(new Pico(1150, 940, 50, 50, "d", this));
        picos.add(new Pico(1110, 900, 50, 50, "l", this));
        picos.add(new Pico(1190, 900, 50, 50, "r", this));

        picos.add(new Pico(2100, 860, 50, 50, "u", this));
        picos.add(new Pico(2100, 940, 50, 50, "d", this));
        picos.add(new Pico(2060, 900, 50, 50, "l", this));
        picos.add(new Pico(2140, 900, 50, 50, "r", this));

        picos.add(new Pico(2950, 910, 50, 50, "u", this));
        picos.add(new Pico(2950, 990, 50, 50, "d", this));
        picos.add(new Pico(2910, 950, 50, 50, "l", this));
        picos.add(new Pico(2990, 950, 50, 50, "r", this));

        picos.add(new Pico(50, 1460, 50, 50, "u", this));
        picos.add(new Pico(50, 1540, 50, 50, "d", this));
        picos.add(new Pico(10, 1500, 50, 50, "l", this));
        picos.add(new Pico(90, 1500, 50, 50, "r", this));

        picos.add(new Pico(400, 1460, 50, 50, "u", this));
        picos.add(new Pico(400, 1540, 50, 50, "d", this));
        picos.add(new Pico(360, 1500, 50, 50, "l", this));
        picos.add(new Pico(440, 1500, 50, 50, "r", this));

        player = new Player (1600, 700, 100, 100, 6, 4, platforms.get(0), this);
        width = 3300;
        height = 2200;
        break;
    }

    playerX = game.getWidth() / 2 - player.getWidth() / 2;
    playerY = game.getHeight() / 2 - player.getHeight() / 2;
  }

}
