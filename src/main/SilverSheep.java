package main;

import javax.swing.JFrame;

/**
 * Clase principal del proyecto SilverSheep. Sí, ya se que no hace mucho, pero
 * es la principal y la respetas!
 * 
 */
public class SilverSheep {

	/**
	 * Método main. Si le pasas la opción -d o --debug se activa el modo
	 * depuración que mostrará bastante salida en la consola ;)
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Ventana ventana = new Ventana();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
