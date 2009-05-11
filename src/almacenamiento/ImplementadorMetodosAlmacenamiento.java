package almacenamiento;

import main.Etiqueta;
import medios.Archivo;

/**
 * Esta interfaz implementa todos los métodos de la interfaz
 * {@link Biblioteca}, y es instanciada por todos aquellos
 * componentes que realicen la persistencia de los datos
 * de la biblioteca de medios
 *
 */
public interface ImplementadorMetodosAlmacenamiento {
	/**
	 * Método para añadir un nuevo archivo a la biblioteca
	 * 
	 * @param archivo
	 */
	public void aniadirArchivo(Archivo archivo);

	/**
	 * Método para añadir una nueva etiqueta a la biblioteca
	 * 
	 * @param etiqueta
	 */
	public void aniadirEtiqueta(Etiqueta etiqueta);

	/**
	 * Método para añadir un nuevo album a la biblioteca, usando solo el nombre
	 * y la descripcion del mismo
	 * 
	 * @param nombre
	 * @param descripcion
	 */
	public void aniadirAlbum(String nombre, String descripcion);

	/**
	 * Método para añadir un nuevo álbum a la biblioteca, usando el nombre la
	 * descripción y los archivos a los que se les que contiene
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param archivos
	 */
	public void aniadirAlbum(String nombre, String descripcion,
			Archivo[] archivos);

	/**
	 * Método para añadir un archivo a un álbum específico
	 * 
	 * @param id
	 * @param archivo
	 */
	public void aniadirArchivoAlbum(int id, Archivo[] archivo);

	/**
	 * Método para eliminar un archivo de la biblioteca
	 * 
	 * @param id
	 */
	public void quitarArchivo(int id);

	/**
	 * Método para eliminar una etiqueta de la biblioteca
	 * 
	 * @param id
	 */
	public void quitarEtiqueta(int id);

	/**
	 * Método para eliminar un álbum de la biblioteca
	 * 
	 * @param id
	 */
	public void quitarAlbum(int id);

	/**
	 * Método para eliminar un archivo de un álbum
	 * 
	 * @param id
	 */
	public void quitarArchivoAlbum(int id);

	/**
	 * Método que nos permite determinar si cierto componente de la 
	 * biblioteca ya se encuentra registrado o no.
	 * @param nombre
	 * @param tipo
	 * @return
	 */
	public boolean noEsta(String nombre, String tipo);
}
