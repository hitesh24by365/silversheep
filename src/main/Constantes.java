package main;

/**
 * Esta clase contiene las variables que son constantes o que deben ser globales
 * mientras el sistema esté en funcionamiento
 * 
 */
public interface Constantes {
	// imagenes 30x30
	public static final String IMG_REPRODUCIR_30 = "/img/30x30/reproducir.png";
	public static final String IMG_PAUSAR_30 = "/img/30x30/pausar.png";
	public static final String IMG_SIGUIENTE_30 = "/img/30x30/siguiente.png";
	public static final String IMG_ANTERIOR_30 = "/img/30x30/anterior.png";
	public static final String IMG_DETENER_30 = "/img/30x30/parar.png";
	public static final String IMG_CERRAR_30 = "/img/30x30/cerrar.png";
	public static final String IMG_SALIR_30 = "/img/30x30/salir.png";
	public static final String IMG_FULLSCREEN_30 = "/img/30x30/fullscreen.png";
	public static final String IMG_VOLUMEN_MUTE_30 = "/img/30x30/volumen-mute.png";
	public static final String IMG_VOLUMEN_CERO_30 = "/img/30x30/volumen-cero.png";
	public static final String IMG_VOLUMEN_BAJO_30 = "/img/30x30/volumen-bajo.png";
	public static final String IMG_VOLUMEN_MEDIO_30 = "/img/30x30/volumen-medio.png";
	public static final String IMG_VOLUMEN_ALTO_30 = "/img/30x30/volumen-alto.png";
	public static final String IMG_SUBIR_VOLUMEN_30 = "/img/30x30/subir-volumen.png";
	public static final String IMG_BAJAR_VOLUMEN_30 = "/img/30x30/bajar-volumen.png";
	public static final String IMG_RANDOM_30 = "/img/30x30/random.png";
	public static final String IMG_LIMPIAR_30 = "/img/30x30/limpiar.png";
	public static final String IMG_REFRESCAR_30 = "/img/30x30/actualizar.png";
	public static final String IMG_HERRAMIENTAS_30 = "/img/30x30/herramientas.png";
	// imagenes 16x16
	public static final String IMG_REPRODUCIR_16 = "/img/16x16/reproducir.png";
	public static final String IMG_PAUSAR_16 = "/img/16x16/pausar.png";
	public static final String IMG_SIGUIENTE_16 = "/img/16x16/siguiente.png";
	public static final String IMG_ANTERIOR_16 = "/img/16x16/anterior.png";
	public static final String IMG_CERRAR_16 = "/img/16x16/cerrar.png";
	public static final String IMG_SALIR_16 = "/img/16x16/salir.png";
	public static final String IMG_REPRODUCIR_PRESS_16 = "/img/16x16/reproducir_pressed.png";
	public static final String IMG_PAUSAR_PRESS_16 = "/img/16x16/pausar_pressed.png";
	public static final String IMG_SIGUIENTE_PRESS_16 = "/img/16x16/siguiente_pressed.png";
	public static final String IMG_ANTERIOR_PRESS_16 = "/img/16x16/anterior_pressed.png";
	public static final String IMG_CERRAR_PRESS_16 = "/img/16x16/cerrar_pressed.png";
	public static final String IMG_SONIDO_16 = "/img/16x16/sonido.png";
	public static final String IMG_IMAGEN_16 = "/img/16x16/imagen.png";
	public static final String IMG_MEDIO_16 = "/img/16x16/medio.png";
	public static final String IMG_BIBLIO_16 = "/img/16x16/biblioteca.png";
	public static final String IMG_VIDEO_16 = "/img/16x16/video.png";
	public static final String IMG_OVEJA_16 = "/img/16x16/sheep.png";
	public static final String IMG_ANIADIR_16 = "/img/16x16/aniadir.png";
	public static final String IMG_REMOVER_RUTA_16 = "/img/16x16/remover.png";
	public static final String IMG_REFRESCAR_16 = "/img/16x16/refrescar.png";
	public static final String IMG_BUSCAR_16 = "/img/16x16/buscar.png";
	public static final String IMG_LIMPIAR_16 = "/img/16x16/limpiar.png";
	public static final String IMG_COLAPSAR_16 = "/img/16x16/colapsar.png";
	public static final String IMG_EXPANDIR_16 = "/img/16x16/expandir.png";
	public static final String IMG_HERRAMIENTAS_16 = "/img/16x16/herramientas.png";
	public static final String IMG_AYUDA_16 = "/img/16x16/ayuda.png";
	// otras imagenes
	public static final String IMG_AUDIO = "/img/misc/audio.png";
	public static final String IMG_IMAGEN = "/img/misc/imagen.png";
	public static final String IMG_VIDEO = "/img/misc/video.png";
	public static final String IMG_LOGO = "/img/misc/logo.jpg";
	public static final String IMG_BIBLIO = "/img/misc/biblioteca.png";
	public static final String IMG_OVEJA_MUERTA = "/img/misc/oveja-muerta.png";
	// formatos soportados
	public static final String EXTENSIONES_AUDIO = "mp3-wav-";
	public static final String EXTENSIONES_VIDEO = "mpg-mpeg-avi-wmv-";
	public static final String EXTENSIONES_IMAGEN = "jpg-gif-png-";
	public static final String EXTENSIONES_TODAS = EXTENSIONES_AUDIO+EXTENSIONES_VIDEO+EXTENSIONES_IMAGEN;
	public static final int NUMERO_EXTENSIONES_SOPORTADAS = EXTENSIONES_AUDIO
			.length()
			+ EXTENSIONES_VIDEO.length() + EXTENSIONES_IMAGEN.length();
	// tipos de datos en la BD
	public static final String BD_ALBUM = "album";
	public static final String BD_ARCHIVO = "archivo";
	public static final String BD_ETIQUETA = "etiqueta";
	// otras cosas
	public static boolean DEBUG = false;
	public static final String VERSION = "0.1";
	// titulos pestanias
	public static final String TAB_IMAGEN = "Gestor de imágenes";
	public static final String TAB_AUDIO = "Gestor de m\u00fasica";
	public static final String TAB_VIDEO = "Gestor de videos";
	public static final String TAB_BIBLIO = "Gestor de la biblioteca";
	// Variables para la conexion a la base de datos SQLite
	public static final String BD_RUTA_CONTROLADOR_JDBC = "org.sqlite.JDBC";
	public static final String DB_NOMBRE_BASE_DATOS = "jdbc:sqlite:silversheep.db";
	//acciones de los botones en las pestanias
	public static final String ACC_BTN_PESTANIA_CERRAR = "CERRAR";
	public static final String ACC_BTN_PESTANIA_REPRODUCIR = "REPRODUCIR";
	public static final String ACC_BTN_PESTANIA_SIGUIENTE = "SIGUIENTE";
	public static final String ACC_BTN_PESTANIA_ANTERIOR = "ANTERIOR";
	public static final String COMILLAS_DOBLES = "COmiLLas_DOblES";
}
