package medios;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

import main.Constantes;

import org.tritonus.share.sampled.file.TAudioFileFormat;

public class Audio extends ArchivoMultimedia {
	private AudioFileFormat formatoArchivoAudio = null;
	private File archivoTrabajo;
	private Map propiedades;
	private String strTemporal = "";
	private int intTemporal = 0;

	@Override
	public void buildArtista() {
		strTemporal = (String)propiedades.get("author");
		archivo.setArtista(strTemporal==null?"Desconocido":strTemporal);
	}
	@Override
	public void buildAlbumDisco() {
		strTemporal = (String)propiedades.get("album");
		archivo.setAlbumDisco(strTemporal==null?"Desconocido":strTemporal);
	}
	@Override
	public void buildGenero() {
		strTemporal = (String)propiedades.get("mp3.id3tag.genre");
		archivo.setGenero(strTemporal==null?"Desconocido":strTemporal);
	}
	@Override
	public void buildLongitud() {
		intTemporal = ((Long)propiedades.get("duration")).intValue();
		archivo.setLongitud(strTemporal==null?0:intTemporal);
	}
	@Override
	public void buildCodec() {
		archivo.setCodec((String)propiedades.get("mp3.version.encoding"));
	}

	@Override
	public void buildDimensiones() {
	}

	public void iniciarCargaMetadatos(){
		try {
			archivoTrabajo = new File(archivo.getNombreArchivo());
			archivo.setTamanioKB(archivoTrabajo.length());
			formatoArchivoAudio = AudioSystem.getAudioFileFormat(archivoTrabajo);
			if (formatoArchivoAudio instanceof TAudioFileFormat)
				propiedades = formatoArchivoAudio.properties();
			else
				System.err.println("ERROR LEYENDO ARCHIVO");
		} catch (Exception e) {
			System.err.println("Error mientras se procesaba el archivo "+archivo.getNombreArchivo());
			archivo.setSoportado(false);
			if(Constantes.DEBUG)
				e.printStackTrace();
		}
		finally{
			archivo.setFechaAdicion(new GregorianCalendar());
		}
	}
}