package ch.ffhs.easyleecher.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import ch.ffhs.easyleecher.gui.table.NZBProviderTable;

/**
 * Dieses Frame ermögicht das Bearbeiten der NZB Provider
 * 
 * @author pascal bieri
 */
@SuppressWarnings("serial")
public class EditNZBProvidersFrame extends JFrame {
	public static final String[] columnNames = { "ProviderName", "ProviderURL",
			"ProviderAPIKey", "" };
	protected JTable table;
	protected JScrollPane scroller;
	protected NZBProviderTable tableModel;

	public EditNZBProvidersFrame() {
		initComponent();
		table.setSurrendersFocusOnKeystroke(true);
	}

	public void initComponent() {
		tableModel = new NZBProviderTable(columnNames);
		tableModel
				.addTableModelListener(new InteractiveTableModelListener());
		table = new JTable();
		table.setModel(tableModel);
		table.setSurrendersFocusOnKeystroke(true);
		if (!tableModel.hasEmptyRow()) {
			tableModel.addEmptyRow();
		}

		scroller = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(new java.awt.Dimension(500,
				300));
		TableColumn hidden = table.getColumnModel().getColumn(
				NZBProviderTable.HIDDEN_INDEX);
		hidden.setMinWidth(2);
		hidden.setPreferredWidth(2);
		hidden.setMaxWidth(2);
		hidden.setCellRenderer(new InteractiveRenderer(
				NZBProviderTable.HIDDEN_INDEX));

		setLayout(new BorderLayout());
		add(scroller, BorderLayout.CENTER);
	}

	/**
	 * @param row
	 */
	public void highlightLastRow(int row) {
		int lastrow = tableModel.getRowCount();
		if (row == lastrow - 1) {
			table.setRowSelectionInterval(lastrow - 1, lastrow - 1);
		} else {
			table.setRowSelectionInterval(row + 1, row + 1);
		}

		table.setColumnSelectionInterval(0, 0);
	}

	class InteractiveRenderer extends DefaultTableCellRenderer {
		protected int interactiveColumn;

		public InteractiveRenderer(int interactiveColumn) {
			this.interactiveColumn = interactiveColumn;
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			if (column == interactiveColumn && hasFocus) {
				if ((EditNZBProvidersFrame.this.tableModel.getRowCount() - 1) == row
						&& !EditNZBProvidersFrame.this.tableModel.hasEmptyRow()) {
					EditNZBProvidersFrame.this.tableModel.addEmptyRow();
				}

				highlightLastRow(row);
			}

			return c;
		}
	}

	public class InteractiveTableModelListener implements TableModelListener {
		public void tableChanged(TableModelEvent evt) {
			if (evt.getType() == TableModelEvent.UPDATE) {
				int column = evt.getColumn();
				int row = evt.getFirstRow();
				System.out.println("row: " + row + " column: " + column);
				table.setColumnSelectionInterval(column + 1, column + 1);
				table.setRowSelectionInterval(row, row);
			}
		}
	}

}