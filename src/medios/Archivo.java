package medios;

import java.util.Formatter;
import java.util.GregorianCalendar;
import medios.Etiqueta;

/**
 * La clase archivo proporciona todos los atributos más los getters y setters
 * que puede tener un archivo de medios. Hace parte del patrón de diseño
 * Builder, usado para abstraer los diferentes tipos de archivos de medio.
 * 
 */
public class Archivo {
	// define el ID en la base de datos, el alto y ancho si es imagen o video, y
	// la longitud si es video o audio
	private int id, alto, ancho, longitud;
	// define el tamanio del archivo
	private long tamanio;
	// define los datos que son cadenas de texto del archivo
	private String nombreArchivo, codec, artista, genero,
			albumDisco;
	// Un flag que permite saber si se posee o no el codec para reproducir un
	// medio específico
	private boolean esSoportado = true;
	// Arreglo de etiquetas que posee un archivo
	private Etiqueta[] etiquetas;
	// este objeto nos permite determinar la fecha actual
	private GregorianCalendar fechaAdicion;

	/**
	 * Devuelve el género de la canción
	 * @return
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * Asigna un género a la canción
	 * @param genero
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * Devuelve el álbum del disco
	 * @return
	 */
	public String getAlbumDisco() {
		return albumDisco;
	}

	/**
	 * Asigna el álbum del disco
	 * @param albumDisco
	 */
	public void setAlbumDisco(String albumDisco) {
		this.albumDisco = albumDisco;
	}

	/**
	 * Devuelve el ID del archivo de la BD
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Asigna el ID del archivo de la BD
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Devuelve el tamanio del archivo
	 * @return
	 */
	public long getTamanio() {
		return tamanio;
	}

	/**
	 * Devuelve el alto del video o imagen
	 * @return
	 */
	public int getAlto() {
		return alto;
	}
	
	/**
	 * 	Asigna el alto del video o imagen
	 * @param alto
	 */
	public void setAlto(int alto) {
		this.alto = alto;
	}

	/**
	 * Devuelve el ancho del video o imagen
	 * @return
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * Asigna el ancho del video o imagen
	 * @param ancho
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	/**
	 * Devuelve la longitud del video o audio
	 * @return
	 */
	public int getLongitud() {
		return longitud;
	}

	/**
	 * Asigna la longitud del video o audio
	 * @param longitud
	 */
	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	/**
	 * Devuelve el codec del archivo multimedia
	 * @return
	 */
	public String getCodec() {
		return codec;
	}

	/**
	 * Asigna el codec al archivo multimedia
	 * @param codec
	 */
	public void setCodec(String codec) {
		this.codec = codec;
	}

	/**
	 * Deveulve el artista del audio
	 * @return
	 */
	public String getArtista() {
		return artista;
	}

	/**
	 * Asigna el artista del audio
	 * @param artista
	 */
	public void setArtista(String artista) {
		this.artista = artista;
	}

	/**
	 * Asigna el tamanio del archivo
	 * @param tamanio
	 */
	public void setTamanioKB(long tamanio) {
		this.tamanio = tamanio;
	}

	/**
	 * Devuelve el nombre del archivo
	 * @return
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * Asigna el nombre del archivo
	 * @param nombreArchivo
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	/**
	 * Devuelve las etiquetas de un archivo
	 * @return
	 */
	public Etiqueta[] getEtiquetas() {
		return etiquetas;
	}

	/**
	 * Asigna las etiquetas a un archivo
	 * @param etiquetas
	 */
	public void setEtiquetas(Etiqueta[] etiquetas) {
		this.etiquetas = etiquetas;
	}

	/**
	 * Devuelve la fecha en que fue creado el archivo
	 * @return
	 */
	public GregorianCalendar getFechaAdicion() {
		return fechaAdicion;
	}

	/**
	 * Asigna la fecha en que fue creado el archivo
	 * @param fechaAdicion
	 */
	public void setFechaAdicion(GregorianCalendar fechaAdicion) {
		this.fechaAdicion = fechaAdicion;
	}

	/**
	 * Asigna el flag que permite saber si se soporta o no el archivo
	 * @param esSoportado
	 */
	public void setSoportado(boolean esSoportado) {
		this.esSoportado = esSoportado;
	}

	/**
	 * Devuelve el flag que permite saber si se soporta o no el archivo
	 * @return
	 */
	public boolean esSoportado() {
		return esSoportado;
	}

	/**
	 * Devuelve la fecha de adición en formato AAAA-MM-DD
	 * @return
	 */
	public String getFechaAdicionSQL() {
		return new Formatter().format("%1$tY-%1$tm-%1$td", fechaAdicion)
				.toString();
	}

}
