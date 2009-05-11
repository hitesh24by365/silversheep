package reproductor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class PanelImagen extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image imagen;

	public PanelImagen(String ruta) {
		cambiarImagen(ruta);
	}

	public PanelImagen() {
		cambiarImagen("");
	}

	public void cambiarImagen(String ruta) {
		imagen = Toolkit.getDefaultToolkit().getImage(ruta);
		this.repaint();
	}

	public void paint(Graphics g) {
		int altura = 0, anchura = 0, coordX = 0, coordY = 0;

		if (getSize().width / getSize().height >= 1) {
			if (getSize().width / getSize().height > imagen.getWidth(this)
					/ imagen.getHeight(this)) {
				altura = getSize().height;
				anchura = escalarAnchura(altura);
				coordX =  (getSize().width - anchura) / 2;
			}
			else{
				anchura = getSize().width;
				altura = escalarAltura(anchura);
				coordY =  (getSize().height - altura) / 2;
			} 
		} else {
			if (getSize().width / getSize().height < imagen.getWidth(this)
					/ imagen.getHeight(this)) {
				anchura = getSize().width;
				altura = escalarAltura(anchura);
				coordY =  (getSize().height - altura) / 2;
			}
			else{
				altura = getSize().height;
				anchura = escalarAnchura(altura);
				coordX =  (getSize().width - anchura) / 2;
			}
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getSize().width, getSize().height);
		g.drawImage(imagen, coordX, coordY, anchura, altura, this);
	}

	private int escalarAnchura(int altura) {
		int ancho = imagen.getWidth(this);
		int alto = imagen.getHeight(this);
		return altura * ancho / alto;
	}

	private int escalarAltura(int anchura) {
		int ancho = imagen.getWidth(this);
		int alto = imagen.getHeight(this);
		return anchura * alto / ancho;
	}
}
