package medios;

import java.util.Formatter;
import java.util.GregorianCalendar;

import main.Etiqueta;

public class Archivo {
	private int id, alto, ancho, longitud;
	private long tamanio;
	private String nombreArchivo, extension, codec, artista, genero,
			albumDisco;
	private boolean esSoportado = true;
	private Etiqueta[] etiquetas;
	private GregorianCalendar fechaAdicion;

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getAlbumDisco() {
		return albumDisco;
	}

	public void setAlbumDisco(String albumDisco) {
		this.albumDisco = albumDisco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTamanio() {
		return tamanio;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public String getCodec() {
		return codec;
	}

	public void setCodec(String codec) {
		this.codec = codec;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public void setTamanioKB(long tamanio) {
		this.tamanio = tamanio;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Etiqueta[] getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(Etiqueta[] etiquetas) {
		this.etiquetas = etiquetas;
	}

	public GregorianCalendar getFechaAdicion() {
		return fechaAdicion;
	}

	public void setFechaAdicion(GregorianCalendar fechaAdicion) {
		this.fechaAdicion = fechaAdicion;
	}

	public void setSoportado(boolean esSoportado) {
		this.esSoportado = esSoportado;
	}

	public boolean esSoportado() {
		return esSoportado;
	}

	public String getFechaAdicionSQL() {
		return new Formatter().format("%1$tY-%1$tm-%1$td", fechaAdicion).toString();
	}

}
