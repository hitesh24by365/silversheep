package reproductor;

import main.ArchivoMultimedia;

public interface FabricaReproductores {
	public Reproductor crearReproductor();
	public Reproductor crearReproductor(String[] archivos);
	public Reproductor crearReproductor(ArchivoMultimedia[] archivos);
}
