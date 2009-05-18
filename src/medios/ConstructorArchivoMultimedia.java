package medios;

/**
 * Archivo que abstrae la construcción de diferentes tipos de medios
 * @author gentoo
 *
 */
public abstract class ConstructorArchivoMultimedia {
	protected Archivo archivo;

	public void crearArchivo(String rutaArchivo) {
		archivo = new Archivo();
		archivo.setNombreArchivo(rutaArchivo);
		iniciarCargaMetadatos();
	}

	/**
	 * Devuelve el archivo creado
	 * @return
	 */
	public Archivo getArchivo() {
		return archivo;
	}

	public void buildId() {

	}

	public void buildTamanioKB() {

	}

	public void buildNombreArchivo() {

	}

	public void buildRuta() {

	}

	public void buildExtension() {

	}

	public void buildEtiquetas() {

	}

	public void buildFechaAdicion() {

	}

	public abstract void buildDimensiones();

	public abstract void buildLongitud();

	public abstract void buildCodec();

	public abstract void buildArtista();

	public abstract void buildAlbumDisco();

	public abstract void buildGenero();
	public abstract void iniciarCargaMetadatos();

}