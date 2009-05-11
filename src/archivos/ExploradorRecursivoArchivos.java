package archivos;

import java.io.File;
import java.util.StringTokenizer;

import main.Constantes;
import medios.Archivo;
import medios.Audio;
import medios.DirectorMedios;
import medios.Imagen;
import medios.Video;
import almacenamiento.Biblioteca;
import almacenamiento.AlmacenarInfoBibliotecaRefinado;
import almacenamiento.TransaccionesSQLite;

public class ExploradorRecursivoArchivos {
	private String[] extensiones;
	private Biblioteca biblio;
	private TransaccionesSQLite sqlite;
	private Archivo medio;
	private Audio archivoAudio;
	private Video archivoVideo;
	private Imagen archivoImagen;
	private DirectorMedios directorMedios;

	public ExploradorRecursivoArchivos(String extensiones) {
		// iniciar interfaz de transacciones usando
		// el patron de disenio Bridge
		sqlite = new TransaccionesSQLite();
		biblio = new AlmacenarInfoBibliotecaRefinado(sqlite);
		StringTokenizer tokens = new StringTokenizer(extensiones, "-");
		int cont = 0;
		this.extensiones = new String[tokens.countTokens() + 1];
		while (tokens.hasMoreTokens()) {
			this.extensiones[cont] = tokens.nextToken();
			cont++;
		}
		directorMedios = new DirectorMedios();
		archivoAudio = new Audio();
		archivoVideo = new Video();
		archivoImagen = new Imagen();
	}

	public void iniciarExploracion(String ruta) {
		File directorio = new File(ruta);
		if (directorio.isDirectory() && directorio.exists())
			explorar(ruta);
	}

	public void explorar(String ruta) {
		File directorio = new File(ruta);
		String[] listado = directorio.list();
		for (int i = 0; i < listado.length; i++) {
			File archivo = new File(ruta + "/" + listado[i]);
			if (archivo.isDirectory()) {
				if (archivo.canRead())
					explorar(archivo.getPath());
			} else {
				for (int j = 0; j < extensiones.length - 1; j++)
					if (listado[i].toLowerCase().endsWith(extensiones[j])) {
						//audio?
						if (esDeTipo(Constantes.EXTENSIONES_AUDIO,listado[i])){
							if(biblio.noEsta((ruta.endsWith("/")?ruta:ruta+"/") + listado[i], Constantes.BD_ARCHIVO)){
								directorMedios.setArchivoMultimedia(archivoAudio);
								directorMedios.buildArchivo((ruta.endsWith("/")?ruta:ruta+"/") + listado[i]);
								medio = directorMedios.getArchivo();
								biblio.aniadirArchivo(medio);
							}
							else if(Constantes.DEBUG)
								System.out.println("NO hago nada porque ya estas: " +
										ruta + listado[i]);
						}
						//imagen?
						else if (esDeTipo(Constantes.EXTENSIONES_IMAGEN,listado[i]))
							directorMedios.setArchivoMultimedia(archivoVideo);
						//video?
						else if (esDeTipo(Constantes.EXTENSIONES_VIDEO,listado[i]))
							directorMedios.setArchivoMultimedia(archivoImagen);
					}
			}
		}
	}

	private boolean esDeTipo(String extensiones, String archivo) {
		StringTokenizer tokens = new StringTokenizer(extensiones, "-");
		while(tokens.hasMoreTokens()){
			if(archivo.endsWith(tokens.nextToken()))
				return true;
		}
		return false;
	}
}