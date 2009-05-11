package medios;

/**
 * Clase que dirige el orden en que se construyen los datos de cada uno de los archivos
 *
 */
public class DirectorMedios {
	private ArchivoMultimedia archivoMultimedia;

	public void setArchivoMultimedia(ArchivoMultimedia archivoMultimedia) {
		this.archivoMultimedia = archivoMultimedia;
	}

	public Archivo getArchivo() {
		return archivoMultimedia.getArchivo();
	}

	/**
	 * Construir archivo
	 * @param rutaArchivo
	 */
	public void buildArchivo(String rutaArchivo){
		archivoMultimedia.crearArchivo(rutaArchivo);
		archivoMultimedia.buildTamanioKB();
		archivoMultimedia.buildNombreArchivo();
		archivoMultimedia.buildRuta();
		archivoMultimedia.buildExtension();
		archivoMultimedia.buildEtiquetas();
		archivoMultimedia.buildFechaAdicion();
		archivoMultimedia.buildDimensiones();
		archivoMultimedia.buildLongitud();
		archivoMultimedia.buildCodec();
		archivoMultimedia.buildArtista();
		archivoMultimedia.buildAlbumDisco();
		archivoMultimedia.buildGenero();
	}
}