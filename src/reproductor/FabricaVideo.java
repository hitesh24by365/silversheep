package reproductor;

import main.Ventana;
import medios.ConstructorArchivoMultimedia;


public class FabricaVideo implements FabricaReproductores {

	@Override
	public Reproductor crearReproductor(Ventana padre) {
		return new CargarVideos(padre);
	}

	@Override
	public Reproductor crearReproductor(String[] archivos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reproductor crearReproductor(ConstructorArchivoMultimedia[] archivos) {
		// TODO Auto-generated method stub
		return null;
	}
}