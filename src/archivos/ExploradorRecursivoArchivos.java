package archivos;

import java.io.File;
import java.util.StringTokenizer;

import main.Constantes;
import medios.Archivo;
import medios.ConstructorArchivoAudio;
import medios.DirectorMedios;
import medios.ConstructorArchivoImagen;
import medios.ConstructorArchivoVideo;
import almacenamiento.Biblioteca;
import almacenamiento.AlmacenarInfoBibliotecaRefinado;
import almacenamiento.TransaccionesSQLite;

/**
 * Esta clase proporciona los métodos para recorrer un árbol de directorios
 * recursivamente, y extrae los archivos específicos de acuerdo a la extensión
 * 
 */
public class ExploradorRecursivoArchivos implements Constantes {
	// Arreglo de extensiones a buscar
	private String[] extensiones;
	// Objeto biblioteca para almacenar los datos
	private Biblioteca biblio;
	// Objeto para almacenar los datos en una base de datos
	private TransaccionesSQLite sqlite;
	// Clase que representa un archivo general
	private Archivo medio;
	// Clase que representa un archivo de audio
	private ConstructorArchivoAudio archivoAudio;
	// Clase que representa un archivo de video
	private ConstructorArchivoVideo archivoVideo;
	// Clase que representa un archivo de imagen
	private ConstructorArchivoImagen archivoImagen;
	// Clase que dirige la creacion de archivos
	private DirectorMedios directorMedios;
	// Objeto para partir las cadenas de texto que contienen extensiones
	private StringTokenizer tokens;

	public ExploradorRecursivoArchivos(String extensiones) {
		// iniciar interfaz de transacciones usando
		// el patron de disenio Bridge
		sqlite = new TransaccionesSQLite();
		// Iniciar la biblioteca de tal maner que use la implementación de
		// SQLite
		biblio = new AlmacenarInfoBibliotecaRefinado(sqlite);
		// Obtener las extensiones
		tokens = new StringTokenizer(extensiones, "-");
		int cont = 0;
		this.extensiones = new String[tokens.countTokens() + 1];
		while (tokens.hasMoreTokens()) {
			this.extensiones[cont] = tokens.nextToken();
			cont++;
		}
		// Iniciar el director de medios
		directorMedios = new DirectorMedios();
		// Iniciar los objetos de archivo específicos
		archivoAudio = new ConstructorArchivoAudio();
		archivoVideo = new ConstructorArchivoVideo();
		archivoImagen = new ConstructorArchivoImagen();
	}

	/**
	 * Este método inicia la exploración recursiva de directorios
	 * 
	 * @param ruta
	 */
	public void iniciarExploracion(String ruta) {
		File directorio = new File(ruta);
		if (directorio.isDirectory() && directorio.exists())
			explorar(ruta);
	}

	/**
	 * Exploración de archivos recursivamente
	 * 
	 * @param ruta
	 */
	public void explorar(String ruta) {
		// Directorio base
		File directorio = new File(ruta);
		// Listado de archivos que contiene
		String[] listado = directorio.list();
		// Recorrer cada archivo encontrado
		for (int i = 0; i < listado.length; i++) {
			File archivo = new File(ruta + "/" + listado[i]);
			// Si el archivo es otro directorio, vuelva a recorrerlo
			if (archivo.isDirectory()) {
				if (archivo.canRead())
					explorar(archivo.getPath());
			} else { // si es un archivo, verificar si tiene la extension
				// deseada
				for (int j = 0; j < extensiones.length - 1; j++)
					if (listado[i].toLowerCase().endsWith(extensiones[j])) {
						// Si es un archivo de audio, intentar recuperar
						// información
						if (esDeTipo(EXTENSIONES_AUDIO, listado[i]))
							// Verificar que el archivo no se encuentre
							// registrado
							if (biblio.noEsta((ruta.endsWith("/") ? ruta : ruta
									+ "/")
									+ listado[i], BD_ARCHIVO)) {
								// Escoger el tipo de archivo que se desea
								// construir
								directorMedios
										.setArchivoMultimedia(archivoAudio);
								// Construir el archivo
								directorMedios
										.buildArchivo((ruta.endsWith("/") ? ruta
												: ruta + "/")
												+ listado[i]);
								// Obtener objeto con los datos del archivo
								medio = directorMedios.getArchivo();
								// Enviar archivo al objeto que lo guardará
								// persistentemente
								biblio.aniadirArchivo(medio);
							} else if (DEBUG)// Si el archivo ya esta
								System.out
										.println("NO hago nada porque ya estas: "
												+ ruta + listado[i]);
							// imagen?
							else if (esDeTipo(EXTENSIONES_IMAGEN,
									listado[i]))
								// TODO capturar datos de la imagen
								if (biblio.noEsta((ruta.endsWith("/") ? ruta
										: ruta + "/")
										+ listado[i], BD_ARCHIVO)) {
									directorMedios
											.setArchivoMultimedia(archivoImagen);
									directorMedios.buildArchivo((ruta
											.endsWith("/") ? ruta : ruta + "/")
											+ listado[i]);
									medio = directorMedios.getArchivo();
									biblio.aniadirArchivo(medio);
								}
								// video?
								else if (esDeTipo(EXTENSIONES_VIDEO,
										listado[i]))
									directorMedios
											.setArchivoMultimedia(archivoVideo);
					}
			}
		}
	}

	/**
	 * Verificar de si un archivo es de un tipo en especial. Esto se logra
	 * comparando su extensión con la lista de extensiones de la variable
	 * EXTENSIONES_***
	 * 
	 * @param extensiones
	 * @param archivo
	 * @return
	 */
	private boolean esDeTipo(String extensiones, String archivo) {
		tokens = new StringTokenizer(extensiones, "-");
		while (tokens.hasMoreTokens()) {
			if (archivo.endsWith(tokens.nextToken()))
				return true;
		}
		return false;
	}
}