/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xochiadventure;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

/**
 *
 * @author antoniomejorado
 */
public class UsandoSonido extends JPanel implements Runnable, ActionListener {
    private static final int WIDTH = 400;	//Ancho del JFrame
    private static final int HEIGHT = 200;	//Altura del JFrame
    private boolean play;	//Control de la música de fondo
    private Image junglaImage;	//Imágen de fondo del JFrame
    private ImageIcon playImage;	//Imagen del botón Play
    private ImageIcon stopImage;	//Imagen del botón Stop
    private ImageIcon sonido1Image;	//Imagen del botón Sonido1
    private ImageIcon sonido2Image;	//Imagen del botón Sonido2
    private SoundClip rioClip;	//Sonido de fondo
    private SoundClip junglaClip;	//Sonido de la jungla
    private SoundClip monoClip;	//Sonido de mono
    private JButton playStopBtn;	//Botón Play y Stop
    private JButton sonido1Btn;	//Botón Sonido1
    private JButton sonido2Btn;	//Botón Sonido2
    private JPanel areaPlay;	//Área para el botón PlayStop

    /*
     * Método constructor de la clase SoundClipTest
     * Inicializa variables, carga imágenes y sonidos,
     * crea los componentes de la interface y crea e
     * inicializa el thread de la aplicación.
     */
    public UsandoSonido() {
            play = true;	//Control de la música inicializado en true
            setOpaque(false);	//Hace transparente el fondo del contenedor
            setLayout(new FlowLayout(FlowLayout.RIGHT));	//Alineación de los componentes
            //Carga la imagen de fondo
            junglaImage = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("jungla.jpg"));
            //Carga la imagen del botón de play
            Image playImg = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("play.png"));
            playImage = new ImageIcon(playImg);
            //Carga la imagen del botón de stop
            Image stopImg = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("stop.png"));
            stopImage = new ImageIcon(stopImg);
            //Carga la imagen de los botones de sonido
            Image sonidoImg = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("sound.png"));
            sonido1Image = new ImageIcon(sonidoImg);
            sonido2Image = new ImageIcon(sonidoImg);
            //Crea el botón de play/stop
            playStopBtn = creaBoton(playImage, "Play/Stop");
            playStopBtn.setIcon(stopImage);
            //Crea los botones de sonido
            sonido1Btn = creaBoton(sonido1Image, "Jungla");
            sonido2Btn = creaBoton(sonido2Image, "Mono");
            //Crea el contenedor para el botón play/stop
            areaPlay = new JPanel();
            areaPlay.setOpaque(false);
            areaPlay.add(playStopBtn);
            //Agrega los botones al contenedor
            add(areaPlay);
            add(sonido1Btn);
            add(sonido2Btn);
            //Carga los clips de sonido
            rioClip = new SoundClip("rio.wav");
            monoClip = new SoundClip("mono.wav");
            junglaClip = new SoundClip("jungla.wav");
            //Activa la repetición del clip
            rioClip.setLooping(true);
            //Reproduce el clip
            rioClip.play();
            // Declaras un hilo
            Thread t = new Thread (this);
            // Empieza el hilo
            t.start ();
    }

    /**
     * Metodo run sobrescrito de la clase Thread.
     * En este metodo se ejecuta el hilo, es un ciclo indefinido.
     */
    public void run() {
        while (true) {
            try	{
                // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException ex)	{
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /*
     * Método creaBoton crea JButtons
     * imagen es la imagen del botón y toolTip
     * es el texto descriptivo del botón
     */
    public JButton creaBoton(ImageIcon imagen, String toolTip) {
        //Crea un nuevo cursor para el botón
        Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        //Crea un nuevo botón
        JButton boton = new JButton(imagen);
        boton.addActionListener(this);	//Añade un ActionListener al botón
        boton.setIgnoreRepaint(true);	//Ignora a repaint()
        boton.setFocusable(false);	//El botón no atrae el foco
        boton.setToolTipText(toolTip);	//Establece el texto descriptivo
        boton.setBorder(null);	//Botón sin borde
        boton.setBorderPainted(false);	//Botón sin borde pintado
        boton.setOpaque(false);	//Fondo del bóton transparente
        boton.setContentAreaFilled(false);	//Botón sin relleno
        boton.setCursor(cursor);	//Establece el cursor para el botón
        boton.setFocusPainted(false);
        boton.setVisible(true);		//Hace visible al botón
        return boton;
    }

    /**
     * Metodo actionPerformed sobrescrito de la interface ActionListener.
     * En este metodo maneja el evento que se genera al realizar una acción.
     * e es el evento que se genera en al realizar una acción.
     */
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();	//Captura la acción realizada
        if (origen == playStopBtn)
        {
            if (play) {
                play = false;	//Control de la música en false
                rioClip.stop();	//La música se detiene
                playStopBtn.setIcon(playImage);	//Cambia la imagen del botón
            }
            else {
                play = true;	//Control de la música en true
                rioClip.play();	//Reproduce el sonido
                playStopBtn.setIcon(stopImage);	//Cambia la imagen del botón
            }
        }
        else if (origen == sonido1Btn) {
            junglaClip.play();	//Reproduce el sonido
        }
        else if (origen == sonido2Btn) {
            monoClip.play();	//Reproduce el sonido
        }
    }

    /**
     * Metodo paint sobrescrito de la clase Applet,
     * heredado de la clase Container.
     * En este metodo se dibuja la imagen de fondo y los botones de acción,
     * g es el objeto grafico usado para dibujar.
     */
    public void paint(Graphics g) {
        //Dibuja la imágen de fondo
        g.drawImage(junglaImage, 0, 0, getSize().width, getSize().height, this);
        //Dibuja los componentes
        super.paint(g);
    }

    /**
 * Metodo principal
 *
 * args es un arreglo de tipo String de linea de comandos
 */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame sonidos = new JFrame();	//Crea un nuevo JFrame
        UsandoSonido sct = new UsandoSonido();	//Crea un nuevo objeto de la clase SoundClipTest
        sonidos.setTitle("Sonidos de la Jungla");	//Título del JFrame
        sonidos.setSize(WIDTH, HEIGHT);	//Tamaño del JFrame
        sonidos.setResizable(false);	//Fija el tamaño
        sonidos.add(sct);	//Añade el objeto de la clase SOundClipTest al JFrame
        sonidos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Establece la operación de cierre
        sonidos.setVisible(true);	//Hace visible el JFrame
    }
}
