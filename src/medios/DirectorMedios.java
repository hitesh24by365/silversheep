package medios;

public class DirectorMedios {
	private ArchivoMultimedia archivoMultimedia;

	public void setArchivoMultimedia(ArchivoMultimedia archivoMultimedia) {
		this.archivoMultimedia = archivoMultimedia;
	}

	public Archivo getArchivo() {
		return archivoMultimedia.getArchivo();
	}
	
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
