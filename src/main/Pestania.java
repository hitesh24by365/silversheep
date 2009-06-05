package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

import reproductor.Reproductor;

/**
 * Esta clase me permite definir un tipo de pestaña especial que contiene un
 * botón de cierre, muy bonito por cierto.
 * 
 */
public class Pestania extends JPanel implements ObservadorReproduccionPestania,
		Constantes {
	private static final long serialVersionUID = -5875306001746540561L;
	// Referencia al panel al que pertenece
	private final JTabbedPane panelPestanias;
	// Botones de acciones de la pestaña
	private BotonPestania btnCerrar, btnAnterior, btnSiguiente, btnIniciar;
	// se esta reproduciendo algo?
	private boolean reproduciendo = false;
	// para controlar el reproductor
	private Reproductor reproductor;

	/**
	 * Constructor que recibe una referencia al panel de pestañas, un
	 * reproductor y decide si se deben o no crear los controles de manejo
	 * 
	 * @param panelPestanias
	 * @param reproductor
	 * @param controles
	 */
	public Pestania(final JTabbedPane panelPestanias,
			final Reproductor reproductor, String icono, boolean controles) {
		this(panelPestanias, icono, false);

		// Añadir panel de controles si así se desea
		if (controles)
			aniadirPanelControles();

		aniadirBotonCerrar();

		this.reproductor = reproductor;
		this.reproductor.registrarObservador(this);

	}

	/**
	 * 
	 * @param panelPestanias
	 * @param icono
	 * @param cerrar
	 */
	public Pestania(final JTabbedPane panelPestanias, String icono,
			boolean cerrar) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		if (panelPestanias == null) {
			throw new NullPointerException(
					"No hay nada en la vaina de pestanias");
		}
		this.panelPestanias = panelPestanias;
		setOpaque(false);

		// hacer que el JLabel lea el titulo de la estania
		JLabel labelPestania = new JLabel() {
			private static final long serialVersionUID = -8414590737359814109L;

			public String getText() {
				int i = panelPestanias.indexOfTabComponent(Pestania.this);
				if (i != -1) {
					return panelPestanias.getTitleAt(i);
				}
				return null;
			}
		};
		labelPestania
				.setIcon(new ImageIcon(this.getClass().getResource(icono)));

		add(labelPestania);
		// aniadir espacio entre el label y el boton
		labelPestania.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		// añadir boton cerrar
		if (cerrar)
			aniadirBotonCerrar();
	}

	/**
	 * Añade el boton de cerrar a las pestañas.
	 */
	private void aniadirBotonCerrar() {
		// boton cerrar pestania
		btnCerrar = new BotonPestania(ACC_BTN_PESTANIA_CERRAR, IMG_CERRAR_16,
				IMG_CERRAR_PRESS_16, "Cerrar esta pestaña");
		add(btnCerrar);
		// aniadir mas espacio al boton
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

	}

	/**
	 * Añade el panel de controles a las pestañas deseadas
	 */
	private void aniadirPanelControles() {
		btnAnterior = new BotonPestania(ACC_BTN_PESTANIA_ANTERIOR,
				IMG_ANTERIOR_16, IMG_ANTERIOR_PRESS_16, "Anterior");
		btnAnterior.setEnabled(false);
		btnIniciar = new BotonPestania(ACC_BTN_PESTANIA_REPRODUCIR,
				IMG_REPRODUCIR_16, IMG_REPRODUCIR_PRESS_16, "Reproducir/Pausar");
		btnSiguiente = new BotonPestania(ACC_BTN_PESTANIA_SIGUIENTE,
				IMG_SIGUIENTE_16, IMG_SIGUIENTE_PRESS_16, "Siguiente");
		add(btnAnterior);
		add(btnIniciar);
		add(btnSiguiente);
	}

	/**
	 * Botón de cierre de la pestaña
	 * 
	 */
	private class BotonPestania extends JButton implements ActionListener {
		private static final long serialVersionUID = 4355024784783444908L;
		private String accion;
		private final int size = 20;

		public BotonPestania(String accion, String imagen,
				String imagenPresionada, String tooltip) {
			this.accion = accion;
			setIcon(new ImageIcon(this.getClass().getResource(imagen)));
			setPressedIcon(new ImageIcon(this.getClass().getResource(
					imagenPresionada)));
			setPreferredSize(new Dimension(size, size));
			setToolTipText(tooltip);
			// Usar boton de Laf
			setUI(new BasicButtonUI());
			// Hacerlo transparente
			setContentAreaFilled(false);
			// No necesita ser 'focusable'
			setFocusable(false);
			setBorder(BorderFactory.createLineBorder(Color.GRAY));
			setBorderPainted(false);
			// Hacer efecto Making rollover
			addMouseListener(buttonMouseListener);
			setRolloverEnabled(true);
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			if (accion.equals(ACC_BTN_PESTANIA_CERRAR)) {
				int i = panelPestanias.indexOfTabComponent(Pestania.this);
				if (i != -1) {
					panelPestanias.remove(i);
				}
			} else if (accion.equals(ACC_BTN_PESTANIA_ANTERIOR)) {
				reproductor.anterior();
			} else if (accion.equals(ACC_BTN_PESTANIA_SIGUIENTE)) {
				reproductor.siguiente();
			} else if (accion.equals(ACC_BTN_PESTANIA_REPRODUCIR)) {
				reproductor.reproducirMedio();
			}
		}

		public void updateUI() {
		}

	}

	/**
	 * Cambiar la apariencia del botón dependiendo de si el mouse está encima
	 * del mismo o no
	 */
	private final static MouseListener buttonMouseListener = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		public void mouseExited(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	};

	@Override
	public void cambioReproduccion(boolean repro) {
		reproduciendo = repro;
		if (reproduciendo)
			btnIniciar.setIcon(new ImageIcon(this.getClass().getResource(
					IMG_PAUSAR_16)));
		else
			btnIniciar.setIcon(new ImageIcon(this.getClass().getResource(
					IMG_REPRODUCIR_16)));
	}

	@Override
	public void cambioReproduccion(boolean cambiar, boolean directo){
		btnIniciar.setEnabled(cambiar);
	}

	@Override
	public void cambioSiguiente(boolean habilitado) {
		btnSiguiente.setEnabled(habilitado);

	}

	@Override
	public void cambioAnterior(boolean habilitado) {
		btnAnterior.setEnabled(habilitado);
	}
}