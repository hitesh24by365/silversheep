package reproductor;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Ventana;
import medios.ArchivoMultimedia;

public interface FabricaReproductores {
	public Reproductor crearReproductor(Ventana padre);
	public Reproductor crearReproductor(String[] archivos);
	public Reproductor crearReproductor(ArchivoMultimedia[] archivos);
}
