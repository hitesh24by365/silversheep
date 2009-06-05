package medios;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

import main.Constantes;

import org.tritonus.share.sampled.file.TAudioFileFormat;

/**
 * Esta clase proporciona los métodos necesarios para obtener metadatos de los
 * diferentes tipos de archivos de audio
 * 
 */
public class ConstructorArchivoAudio extends ConstructorArchivoMultimedia implements Constantes {
	// Objeto para obtener el formato del archivo
	private AudioFileFormat formatoArchivoAudio = null;
	// Referencia directa al archivo
	private File archivoTrabajo;
	// Mapa de propiedades del archivo
	@SuppressWarnings("unchecked")
	private Map propiedades;
	// Datos temporales del archivo
	private String strTemporal = "";

	// TODO probar con archivos OGG
	@Override
	public void buildArtista() {
		strTemporal = (String) propiedades.get("author");
		archivo.setArtista(strTemporal == null ? "Desconocido" : strTemporal);
	}

	@Override
	public void buildAlbumDisco() {
		strTemporal = (String) propiedades.get("album");
		archivo
				.setAlbumDisco(strTemporal == null ? "Desconocido"
						: strTemporal);
	}

	@Override
	public void buildGenero() {
		strTemporal = (String) propiedades.get("mp3.id3tag.genre");
		archivo.setGenero(strTemporal == null ? "Desconocido" : strTemporal);
	}

	@Override
	public void buildLongitud() {
		int intTemporal = ((Long) propiedades.get("duration")).intValue();
		archivo.setLongitud(strTemporal == null ? 0 : intTemporal);
	}

	@Override
	public void buildCodec() {
		archivo.setCodec((String) propiedades.get("mp3.version.encoding"));
	}

	@Override
	public void buildDimensiones() {
	}

	/**
	 * Inicia el proceso de cargar de metadatos. Obtiene el tamaño del archivo y
	 * los tags IDE MP3
	 */
	@Override
	public void iniciarCargaMetadatos() {
		// Obtener datos
		try {
			archivoTrabajo = new File(archivo.getNombreArchivo());
			archivo.setTamanioKB(archivoTrabajo.length());
			formatoArchivoAudio = AudioSystem
					.getAudioFileFormat(archivoTrabajo);
			if (formatoArchivoAudio instanceof TAudioFileFormat){
				propiedades = formatoArchivoAudio.properties();
			}
			else
				System.err.println("ERROR LEYENDO ARCHIVO");
		} catch (Exception e) {
			System.err.println("Error mientras se procesaba el archivo "
					+ archivo.getNombreArchivo());
			archivo.setSoportado(false);
			if (DEBUG)
				e.printStackTrace();
		} finally {
			archivo.setFechaAdicion(new GregorianCalendar());
		}
	}

}