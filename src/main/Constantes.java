package main;

/**
 * Esta clase contiene las variables que son constantes o que deben ser globales
 * mientras el sistema esté en funcionamiento
 * 
 */
public class Constantes {
	// imagenes 50x50
	public static final String IMG_REPRODUCIR_50 = "../img/50x50/reproducir.png";
	public static final String IMG_PAUSAR_50 = "../img/50x50/pausar.png";
	public static final String IMG_SIGUIENTE_50 = "../img/50x50/siguiente.png";
	public static final String IMG_ANTERIOR_50 = "../img/50x50/anterior.png";
	public static final String IMG_DETENER_50 = "../img/50x50/parar.png";
	public static final String IMG_CERRAR_50 = "../img/50x50/cerrar.png";
	public static final String IMG_SALIR_50 = "../img/50x50/salir.png";
	public static final String IMG_FULLSCREEN_50 = "../img/50x50/fullscreen.png";
	// imagenes 30x30
	public static final String IMG_REPRODUCIR_30 = "../img/30x30/reproducir.png";
	public static final String IMG_PAUSAR_30 = "../img/30x30/pausar.png";
	public static final String IMG_SIGUIENTE_30 = "../img/30x30/siguiente.png";
	public static final String IMG_ANTERIOR_30 = "../img/30x30/anterior.png";
	public static final String IMG_DETENER_30 = "../img/30x30/parar.png";
	public static final String IMG_CERRAR_30 = "../img/30x30/cerrar.png";
	public static final String IMG_SALIR_30 = "../img/30x30/salir.png";
	public static final String IMG_FULLSCREEN_30 = "../img/30x30/fullscreen.png";
	// imagenes 16x16
	// TODO redimensionar las imágenes presionadas
	public static final String IMG_REPRODUCIR_16 = "../img/16x16/reproducir.png";
	public static final String IMG_PAUSAR_16 = "../img/16x16/pausar.png";
	public static final String IMG_SIGUIENTE_16 = "../img/16x16/siguiente.png";
	public static final String IMG_ANTERIOR_16 = "../img/16x16/anterior.png";
	public static final String IMG_CERRAR_16 = "../img/16x16/cerrar.png";
	public static final String IMG_REPRODUCIR_PRESS_16 = "../img/16x16/reproducir_pressed.png";
	public static final String IMG_PAUSAR_PRESS_16 = "../img/16x16/pausar_pressed.png";
	public static final String IMG_SIGUIENTE_PRESS_16 = "../img/16x16/siguiente_pressed.png";
	public static final String IMG_ANTERIOR_PRESS_16 = "../img/16x16/anterior_pressed.png";
	public static final String IMG_CERRAR_PRESS_16 = "../img/16x16/cerrar_pressed.png";
	public static final String IMG_SONIDO_16 = "../img/16x16/sonido.png";
	public static final String IMG_IMAGEN_16 = "../img/16x16/imagen.png";
	public static final String IMG_VIDEO_16 = "../img/16x16/video.png";
	public static final String IMG_OVEJA_16 = "../img/16x16/sheep.png";
	// otras imagenes
	public static final String IMG_AUDIO = "../img/misc/audio.png";
	public static final String IMG_IMAGEN = "../img/misc/imagen.png";
	public static final String IMG_VIDEO = "../img/misc/video.png";
	public static final String IMG_LOGO = "../img/misc/logo.jpg";
	// formatos soportados
	public static final String EXTENSIONES_AUDIO = "mp3-wav-";
	public static final String EXTENSIONES_VIDEO = "mpg-mpeg-avi-wmv-";
	public static final String EXTENSIONES_IMAGEN = "jpg-gif-png-";
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
	public static final String TAB_AUDIO = "Gestor de música";
	public static final String TAB_VIDEO = "Gestor de videos";
	// Variables para la conexión a la base de datos SQLite
	public static final String BD_RUTA_CONTROLADOR_JDBC = "org.sqlite.JDBC";
	public static final String DB_NOMBRE_BASE_DATOS = "jdbc:sqlite:silversheep.db";
	//acciones de los botones en las pestañas
	public static final String ACC_BTN_PESTANIA_CERRAR = "CERRAR";
	public static final String ACC_BTN_PESTANIA_REPRODUCIR = "REPRODUCIR";
	public static final String ACC_BTN_PESTANIA_SIGUIENTE = "SIGUIENTE";
	public static final String ACC_BTN_PESTANIA_ANTERIOR = "ANTERIOR";
}
