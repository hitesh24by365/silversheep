package medios;

import java.io.File;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;

import main.Constantes;

public class ConstructorArchivoImagen extends ConstructorArchivoMultimedia
		implements Constantes {
	// Referencia directa al archivo
	private File archivoTrabajo;

	@Override
	public void buildAlbumDisco() {
	}

	@Override
	public void buildArtista() {
	}

	@Override
	public void buildCodec() {
	}

	@Override
	public void buildDimensiones() {
		ImageIcon imagen = new ImageIcon(archivo.getNombreArchivo());
		archivo.setAncho(imagen.getIconWidth());
		archivo.setAlto(imagen.getIconHeight());
	}

	@Override
	public void buildGenero() {
	}

	@Override
	public void buildLongitud() {
	}

	@Override
	public void iniciarCargaMetadatos() {
		// Obtener datos
		try {
			archivoTrabajo = new File(archivo.getNombreArchivo());
			archivo.setTamanioKB(archivoTrabajo.length());
		} catch (Exception e) {
			System.err.println("Error mientras se procesaba el archivo "
					+ archivo.getNombreArchivo());
			if (DEBUG)
				e.printStackTrace();
		} finally {
			archivo.setFechaAdicion(new GregorianCalendar());
		}
	}
}
