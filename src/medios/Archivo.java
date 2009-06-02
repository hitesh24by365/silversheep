package medios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import main.Constantes;
import medios.Etiqueta;

/**
 * La clase archivo proporciona todos los atributos más los getters y setters
 * que puede tener un archivo de medios. Hace parte del patrón de diseño
 * Builder, usado para abstraer los diferentes tipos de archivos de medio.
 * 
 */
public class Archivo implements Constantes{
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
	//este es el contador de reproducciones
	private int contador;

	public Archivo(){
		
	}
	public Archivo(ResultSet resultados) {
		try {
			Integer temp = (Integer)resultados.getObject(1); 
			setId(temp==null ? 0 : temp.intValue());
			setNombreArchivo((String)resultados.getObject(2));
			String formatoFecha = (String)resultados.getObject(3);
			String[] a = formatoFecha.split("-");;
			setFechaAdicion(new GregorianCalendar(Integer.valueOf(a[0]).intValue(), Integer
					.valueOf(a[1]).intValue() - 1, Integer.valueOf(a[2]).intValue()));
			temp = (Integer)resultados.getObject(4);
			setTamanioKB(temp==null ? 0 : temp.intValue());
			temp = (Integer)resultados.getObject(5);
			setAlto(temp==null ? 0 : temp.intValue());
			temp = (Integer)resultados.getObject(6);
			setAncho(temp==null ? 0 : temp.intValue());
			temp = (Integer)resultados.getObject(7);
			setLongitud(temp==null ? 0 : temp.intValue());
			setCodec((String)resultados.getObject(8));
			setArtista((String)resultados.getObject(9));
			setGenero((String)resultados.getObject(10));
			setAlbumDisco((String)resultados.getObject(11));
			//es soportado es el indice 12
			setSoportado(true);
			temp = (Integer)resultados.getObject(13);
			setContador(temp==null ? 0 : temp.intValue());
		} catch (NumberFormatException e) {
			System.err.println("Error al convertir número desde la base de datos.");
			if(DEBUG)
				e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Error con la base de datos.");
			if(DEBUG)
				e.printStackTrace();
		}
	}
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

	public boolean esTipo(String tipo) {
		StringTokenizer tokens = new StringTokenizer(tipo, "-");
		while(tokens.hasMoreTokens()){
			if(getNombreArchivo().toLowerCase().endsWith("."+tokens.nextToken().toLowerCase()))
				return true;
		}
		return false;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
	public int getContador() {
		return contador;
	}
	public String getNombreCortoArchivo() {
		String nombre = getNombreArchivo().replace('\\', '/');
		return nombre.substring(nombre.lastIndexOf('/')+1);
	}
	public String toString(){
		return getNombreCortoArchivo().equals("") ? "Desconocido" : getNombreCortoArchivo();
	}
}
