package main;

public class Constantes {
	// imagenes 50x50
	public static final String IMG_REPRODUCIR_50 = "img/50x50/reproducir.png";
	public static final String IMG_PAUSAR_50 = "img/50x50/pausar.png";
	public static final String IMG_SIGUIENTE_50 = "img/50x50/siguiente.png";
	public static final String IMG_ANTERIOR_50 = "img/50x50/anterior.png";
	public static final String IMG_DETENER_50 = "img/50x50/parar.png";
	public static final String IMG_CERRAR_50 = "img/50x50/cerrar.png";
	public static final String IMG_SALIR_50 = "img/50x50/salir.png";
	public static final String IMG_FULLSCREEN_50 = "img/50x50/fullscreen.png";
	// imagenes 30x30
	public static final String IMG_REPRODUCIR_30 = "img/30x30/reproducir.png";
	public static final String IMG_PAUSAR_30 = "img/30x30/pausar.png";
	public static final String IMG_SIGUIENTE_30 = "img/30x30/siguiente.png";
	public static final String IMG_ANTERIOR_30 = "img/30x30/anterior.png";
	public static final String IMG_DETENER_30 = "img/30x30/parar.png";
	public static final String IMG_CERRAR_30 = "img/30x30/cerrar.png";
	public static final String IMG_SALIR_30 = "img/30x30/salir.png";
	public static final String IMG_FULLSCREEN_30 = "img/30x30/fullscreen.png";
	// otras imagenes
	public static final String IMG_AUDIO = "img/misc/audio.png";
	public static final String IMG_IMAGEN = "img/misc/imagen.png";
	public static final String IMG_VIDEO = "img/misc/video.png";
	public static final String IMG_LOGO = "img/misc/logo.jpg";
	// formatos soportados
	public static final String EXTENSIONES_AUDIO = "mp3-wav-";
	public static final String EXTENSIONES_VIDEO = "mpg-mpeg-avi-wmv-";
	public static final String EXTENSIONES_IMAGEN = "jpg-gif-png-";
	public static final int NUMERO_EXTENSIONES_SOPORTADAS = EXTENSIONES_AUDIO.length()
			+ EXTENSIONES_VIDEO.length() + EXTENSIONES_IMAGEN.length();
	// tipos de datos en la BD
	public static final String BD_ALBUM = "album";
	public static final String BD_ARCHIVO = "archivo";
	public static final String BD_ETIQUETA = "etiqueta";
	// otras cosas
	public static boolean DEBUG = false;
	public static final String VERSION = "0.1";
	//titulos pestanias
	public static final String TAB_IMAGEN = "Imágenes";
	public static final String TAB_AUDIO = "Música";
	public static final String TAB_VIDEO = "Videos";
}
