package reproductor;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.FloatControl;

import listados.ListaReproduccion;
import main.Constantes;

/**
 * Play.java
 * <p>
 * A simple class that plays audio from given file names.
 * <p>
 * Uses the Java Sound SourceDataLine interface to stream the sound. Converts
 * compressed encodings (ALAW, ULAW, MP3) to PCM.
 * 
 * @author Dan Becker, beckerdo@io.com
 */
public class ReproducirArchivoAudio implements Runnable, Constantes {
	private boolean reproducir = false;
	private boolean reproduciendo;
	private String rutaMedio;
	private Thread thread;
	private boolean pausado = false;
	private ListaReproduccion lista;
	private FloatControl volumen;

	public ReproducirArchivoAudio(String ruta) {
		rutaMedio = ruta;
	}

	public ReproducirArchivoAudio() {
	}

	/**
	 * Reproduce un archivo de audio
	 */
	public void reproducirAudio(String nombreArchivo) {
		File archivoSonido = new File(nombreArchivo);

		try {
			// Crea un stream desde el archivo especificado.
			// Puede lanzar excepciones IOException o
			// UnsupportedAudioFileException
			AudioInputStream flujoEntradaAudio = AudioSystem
					.getAudioInputStream(archivoSonido);
			reproducirFlujoAudio(flujoEntradaAudio);
		} catch (Exception e) {
			System.err.println("Problem with file " + nombreArchivo + ":");
			if (DEBUG)
				e.printStackTrace();
		}
	}

	/**
	 * Reproduce el flujo de audio obtenido del archivo
	 * 
	 * @param flujoEntradaAudio
	 */
	public void reproducirFlujoAudio(AudioInputStream flujoEntradaAudio) {
		reproduciendo = true;
		// El flujo de entrada contiene informacion como el rate, tamanio,
		// canales, etc.
		AudioFormat formatoAudio = flujoEntradaAudio.getFormat();
		if (DEBUG)
			System.out.println("Reproduciendo el formato de archivo: "
					+ formatoAudio);

		// Convertir datos comprimidos del audio en formato PCM sin comprimir.
		if (formatoAudio.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
			AudioFormat nuevoFormato = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED, formatoAudio
							.getSampleRate(), 16, formatoAudio.getChannels(),
					formatoAudio.getChannels() * 2, formatoAudio
							.getSampleRate(), false);
			if (DEBUG)
				System.out.println("Convirtiendo datos a formato "
						+ nuevoFormato);
			AudioInputStream nuevoFlujo = AudioSystem.getAudioInputStream(
					nuevoFormato, flujoEntradaAudio);
			formatoAudio = nuevoFormato;
			flujoEntradaAudio = nuevoFlujo;
		}

		// Abrir un data line para reproducir nuestro tipo de audio.
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				formatoAudio);
		if (!AudioSystem.isLineSupported(info)) {
			System.err
					.println("Play.playAudioStream no maneja este tipo de audio sobre este sistema.");
			return;
		}

		try {
			// Crear un SourceDataLine para reproducir (arroja un
			// LineUnavailableException en caso de problemas).
			SourceDataLine dataLine = (SourceDataLine) AudioSystem
					.getLine(info);
			// La linea adquiere recursos del sistema (throws
			// LineAvailableException).
			dataLine.open(formatoAudio);

			// Ajustar el volumen
			if (dataLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				volumen = (FloatControl) dataLine
						.getControl(FloatControl.Type.MASTER_GAIN);
				cambiarVolumen(lista.getPorcentajeVolumen());
			}

			// Iniciar el flujo de datos
			dataLine.start();

			// Crear un buffer para ir colocando los datos del stream.
			int tamanioBuffer = (int) formatoAudio.getSampleRate()
					* formatoAudio.getFrameSize();
			byte[] buffer = new byte[tamanioBuffer];

			// Mover los datos hasta terminar o hasta que haya un error
			try {
				int bytesLeidos = 0;
				while (bytesLeidos >= 0 && reproducir) {
					bytesLeidos = flujoEntradaAudio.read(buffer, 0,
							buffer.length);
					if (bytesLeidos >= 0)
						dataLine.write(buffer, 0, bytesLeidos);
					while (pausado)
						Thread.sleep(1);
				}
			} catch (IOException e) {
				System.err
						.println("Ocurrio un error con el stream que se intenta reproducir");
				if (DEBUG)
					e.printStackTrace();
			} catch (InterruptedException e) {
				System.err
						.println("Ocurrio un error con el hilo de reproduccion");
				if (DEBUG)
					e.printStackTrace();
			}
			if (DEBUG)
				System.out.println("Play.playAudioStream drenando linea.");
			// Continuar con la linea de datos de I/O hasta que su buffer sea
			// drenado
			dataLine.drain();
			if (DEBUG)
				System.out.println("Play.playAudioStream cerrando linea.");
			// Cerrar el data line, liberando los recursos como el dispositivo
			// de audio.
			dataLine.close();
		} catch (LineUnavailableException e) {
			System.err
					.println("Error con la tarjeta de sonido. Probablemente este en uso.");
			if (DEBUG)
				e.printStackTrace();
		}
		lista.setReproduciendo(false);
		lista.invertirControles();
		reproduciendo = false;
	}

	@Override
	public void run() {
		reproducir = true;
		reproducirAudio(rutaMedio);
	}

	/**
	 * Asignar la ruta que se desea reproducir
	 * 
	 * @param rutaMedio
	 */
	public void setRutaMedio(String rutaMedio) {
		this.rutaMedio = rutaMedio;
	}

	/**
	 * Obtener ruta
	 * 
	 * @return
	 */
	public String getRutaMedio() {
		return rutaMedio;
	}

	/**
	 * Reproducir un medio de audio
	 * 
	 * @param lista
	 */
	public void reproducir(ListaReproduccion lista) {
		this.lista = lista;
		// si esta pausado
		if (pausado) {
			if (DEBUG)
				System.out.println("Se encuentra pausado. Quitare la pausa.");
			pausado = false;
		} else if (!reproduciendo) {
			if (DEBUG)
				System.out
						.println("Se creara un nuevo hilo de reproduccion de musica.");
			thread = new Thread(this);
			thread.setName("reproductor");
			thread.start();
		}
	}

	public boolean isReproduciendo() {
		return reproduciendo;
	}

	public void setReproduciendo(boolean aunReproduciendo) {
		this.reproduciendo = aunReproduciendo;
	}

	/**
	 * Pausar el medio que se este reproduciendo
	 * 
	 * @param lista
	 */
	public void pausar(ListaReproduccion lista) {
		this.lista = lista;
		pausado = true;
	}

	/**
	 * Detener el flujo de audio
	 * 
	 * @param lista
	 */
	public void detener(ListaReproduccion lista) {
		this.lista = lista;
		pausado = false;
		reproducir = false;
	}

	/**
	 * Seleccionar la siguiente pista en la lista
	 * 
	 * @param lista
	 */
	public void siguiente(ListaReproduccion lista) {
		this.lista = lista;
		detener(lista);
		// Aumentar la pista seleccionada
		lista.seleccionarSiguientePista();
		while (reproduciendo) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lista.reproducirMedio();
	}

	/**
	 * Reproducir un medio despues de hacer doble clic sobre la lista de
	 * reproduccion
	 * 
	 * @param lista
	 */
	public void siguienteSaltado(ListaReproduccion lista) {
		this.lista = lista;
		detener(lista);
		while (reproduciendo) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				System.err.println("Error con el hilo de reproduccion.");
				if (DEBUG)
					e.printStackTrace();
			}
		}
		lista.reproducirMedio();
	}

	/**
	 * Reproducir lista anterior a la que esta siendo reproducida
	 * 
	 * @param lista
	 */
	public void anterior(ListaReproduccion lista) {
		this.lista = lista;
		detener(lista);
		lista.seleccionarAnteriorPista();
		while (reproduciendo) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lista.reproducirMedio();
	}

	public void cambiarVolumen(int vol) {
		float total = volumen.getMaximum() - volumen.getMinimum()/2;
		float equivalente = total*vol/100;
		volumen.setValue((volumen.getMinimum()/2 + equivalente)-0.1F);
	}
	public float minimoVolumen(){
		return volumen.getMinimum();
	}
}