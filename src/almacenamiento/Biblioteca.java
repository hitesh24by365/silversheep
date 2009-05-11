package almacenamiento;

import main.Etiqueta;
import medios.Archivo;

public interface Biblioteca {
	public void aniadirArchivo(Archivo archivo);
	public void aniadirEtiqueta(Etiqueta etiqueta);
	public void aniadirAlbum(String nombre, String descripcion);
	public void aniadirAlbum(String nombre, String descripcion, Archivo[] archivos);
	public void aniadirArchivoAlbum(int id, Archivo[] archivo);
	public void quitarArchivo(int id);
	public void quitarEtiqueta(int id);
	public void quitarAlbum(int id);
	public void quitarArchivoAlbum(int id);
	public boolean noEsta(String nombre, String tipo);
}