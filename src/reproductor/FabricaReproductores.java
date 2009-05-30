package reproductor;

import main.Ventana;
import medios.Archivo;
import medios.ConstructorArchivoMultimedia;

public interface FabricaReproductores {
	public Reproductor crearReproductor(Ventana padre);
	public Reproductor crearReproductor(String[] archivos);
	public Reproductor crearReproductor(ConstructorArchivoMultimedia[] archivos);
}
