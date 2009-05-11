package reproductor;

import medios.ArchivoMultimedia;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FabricaAudio implements FabricaReproductores {

	@Override
	public JPanel crearReproductor(JFrame padre) {
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