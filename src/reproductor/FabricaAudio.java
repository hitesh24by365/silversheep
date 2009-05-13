package reproductor;

import main.Ventana;
import medios.ArchivoMultimedia;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FabricaAudio implements FabricaReproductores {

	@Override
	public Reproductor crearReproductor(Ventana padre) {
		return new CargarAudio(padre);
	}

	@Override
	public Reproductor crearReproductor(String[] archivos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reproductor crearReproductor(ArchivoMultimedia[] archivos) {
		// TODO Auto-generated method stub
		return null;
	}

}