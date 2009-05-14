package reproductor;

import main.Ventana;
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
	public Reproductor crearReproductor(Ventana padre) {
		return new CargarImagenes(padre);
	}

}
