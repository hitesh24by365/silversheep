package medios;

/**
 * Clase que dirige el orden en que se construyen los datos de cada uno de los archivos
 *
 */
public class DirectorMedios {
	private ConstructorArchivoMultimedia constructorArchivoMultimedia;

	public void setArchivoMultimedia(ConstructorArchivoMultimedia constructorArchivoMultimedia) {
		this.constructorArchivoMultimedia = constructorArchivoMultimedia;
	}

	public Archivo getArchivo() {
		return constructorArchivoMultimedia.getArchivo();
	}

	/**
	 * Construir archivo
	 * @param rutaArchivo
	 */
	public void buildArchivo(String rutaArchivo){
		constructorArchivoMultimedia.crearArchivo(rutaArchivo);
		constructorArchivoMultimedia.buildTamanioKB();
		constructorArchivoMultimedia.buildNombreArchivo();
		constructorArchivoMultimedia.buildRuta();
		constructorArchivoMultimedia.buildExtension();
		constructorArchivoMultimedia.buildEtiquetas();
		constructorArchivoMultimedia.buildFechaAdicion();
		constructorArchivoMultimedia.buildDimensiones();
		constructorArchivoMultimedia.buildLongitud();
		constructorArchivoMultimedia.buildCodec();
		constructorArchivoMultimedia.buildArtista();
		constructorArchivoMultimedia.buildAlbumDisco();
		constructorArchivoMultimedia.buildGenero();
	}
}