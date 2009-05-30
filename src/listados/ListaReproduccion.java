package listados;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import main.Constantes;

public class ListaReproduccion extends JPanel implements Constantes {
	private static final long serialVersionUID = -8338766233305367920L;
	private JTable listado;

	public ListaReproduccion() {
		super(new GridLayout(1, 0));
		crearListado();
	}

	private void crearListado() {

		listado = new JTable((TableModel) new MiModeloTabla());
		listado.setPreferredScrollableViewportSize(new Dimension(500, 70));
		listado.setFillsViewportHeight(true);
		listado.setAutoCreateRowSorter(true);
		initColumnSizes(listado);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(listado);

		// Add the scroll pane to this panel.
		add(scrollPane);
	}

	class MiModeloTabla extends AbstractTableModel {
		private static final long serialVersionUID = 4728231179048500607L;
		private String[] nombreColumnas = { "T\u00edtulo", "Artista",
				"\u00c1lbum", "Longitud", "G\u00e9nero", "Ubicaci\u00f3n",
				"Nombre archivo", "Contador" };
		private boolean[] columnasActivas = new boolean[nombreColumnas.length];
		private Object[][] datos = { { "Satan my master", "Dimmu Borgir", "In sorte diaboli",
				"6:66", "Death Metal", "/home/hell/music", "satan-my-master.mp3", new Integer(5)} };

		public int getColumnCount() {
			return nombreColumnas.length;
		}

		public int getRowCount() {
			return datos.length;
		}

		public String getColumnName(int col) {
			return nombreColumnas[col];
		}

		public Object getValueAt(int fila, int col) {
			return datos[fila][col];
		}

		/*
		 * JTable uses this method to determine the default renderer/ editor for
		 * each cell. If we didn't implement this method, then the last column
		 * would contain text ("true"/"false"), rather than a check box.
		 */
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		public boolean isCellEditable(int fila, int col) {
			return false;
		}

		private void columnasVisibles(){
			//columnasActivas[fila];
		}
		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		public void setValueAt(Object valor, int fila, int col) {
			if (DEBUG) {
				System.out.println("Setting value at " + fila + "," + col
						+ " to " + valor + " (an instance of "
						+ valor.getClass() + ")");
			}

			datos[fila][col] = valor;
			fireTableCellUpdated(fila, col);

			if (DEBUG) {
				System.out.println("New value of data:");
				printDebugData();
			}
		}

		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();

			for (int i = 0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j = 0; j < numCols; j++) {
					System.out.print("  " + datos[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}
	private void initColumnSizes(JTable table) {
		MiModeloTabla model = (MiModeloTabla)table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.datos[0];
        TableCellRenderer headerRenderer =
            table.getTableHeader().getDefaultRenderer();

        for (int i = 0; i < 5; i++) {
            column = table.getColumnModel().getColumn(i);

            comp = headerRenderer.getTableCellRendererComponent(
                                 null, column.getHeaderValue(),
                                 false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;

            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                             getTableCellRendererComponent(
                                 table, longValues[i],
                                 false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;

            if (false) {
                System.out.println("Initializing width of column "
                                   + i + ". "
                                   + "headerWidth = " + headerWidth
                                   + "; cellWidth = " + cellWidth);
            }

            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }
}