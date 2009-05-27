package almacenamiento;

import medios.Archivo;
import medios.Etiqueta;

/**
 * La interfaz Biblioteca abstrae todos los métodos que debería proporcionar
 * cualquier componente que guarde datos de los medios de manera persistente. Es
 * parte del patrón de diseño Bridge usado para la persistencia de los datos.
 * 
 */
public interface Biblioteca {
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
	 * Método que nos permite determinar si cierto componente de la biblioteca
	 * ya se encuentra registrado o no.
	 * 
	 * @param nombre
	 * @param tipo
	 * @return
	 */
	public boolean noEsta(String nombre, String tipo);

	/**
	 * Método que nos permite almacenar un par opción-valor
	 * 
	 * @param nombre
	 * @param valor
	 */
	public void aniadirOpcion(String nombre, String valor);

	/**
	 * Método que nos permite quitar un par opción-valor
	 * 
	 * @param nombre
	 * @param valor
	 */
	public void quitarOpcion(String nombre, String valor);

	/**
	 * Método que nos permite quitar un par opción-valor
	 * 
	 * @param nombre
	 */
	public void quitarOpcionesPorNombre(String nombre);

	/**
	 * Este metodo devueleve un arreglo con las opciones que tengan el nombre
	 * proporcionado
	 * 
	 * @param nombre
	 * @return
	 */
	public String[] opcionesPorNombre(String nombre);
}