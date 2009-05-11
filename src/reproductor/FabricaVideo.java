package reproductor;

import main.Ventana;
import medios.ArchivoMultimedia;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FabricaVideo implements FabricaReproductores {

	@Override
	public JPanel crearReproductor(Ventana padre) {
		return new CargarVideos(padre);
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
