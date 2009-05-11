package reproductor;

import javax.swing.JFrame;
import javax.swing.JPanel;

import medios.ArchivoMultimedia;

public class FabricaImagenes implements FabricaReproductores {

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

	@Override
	public JPanel crearReproductor(JFrame padre) {
		return new CargarImagenes(padre);
	}

}
