package ch.ffhs.easyleecher.gui.table;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import ch.ffhs.easyleecher.storage.StorageService;
import ch.ffhs.easyleecher.storage.model.NZBProvider;

/**
 * Diese Tabelle ermöglicht das Editieren der NZB Provider
 * 
 * @author pascal bieri
 */
/**
 * @author thierrybaumann
 * 
 */
@SuppressWarnings("serial")
public class NZBProviderTable extends AbstractTableModel {
	public static final int NAME_INDEX = 0;
	public static final int URL_INDEX = 1;
	public static final int API_INDEX = 2;
	public static final int HIDDEN_INDEX = 3;
	private ArrayList<NZBProvider> nzbProviders;
	protected String[] columnNames;
	protected Vector<NZBProvider> dataVector;
	StorageService storageService;

	/**
	 * @param columnNames
	 */
	public NZBProviderTable(String[] columnNames) {
		this.columnNames = columnNames;

		storageService = StorageService.getInstance();
		nzbProviders = storageService.getNZBProviders();
		dataVector = new Vector<NZBProvider>();

		if (nzbProviders != null && !nzbProviders.isEmpty()) {
			for (NZBProvider nzbprovider : nzbProviders) {
				dataVector.add(nzbprovider);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		return columnNames[column];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int column) {
		if (column == HIDDEN_INDEX)
			return false;
		else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	public Class getColumnClass(int column) {
		switch (column) {
		case NAME_INDEX:
		case URL_INDEX:
		case API_INDEX:
			return String.class;
		default:
			return Object.class;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int column) {
		NZBProvider provider = (NZBProvider) dataVector.get(row);
		switch (column) {
		case NAME_INDEX:
			return provider.getProviderName();
		case URL_INDEX:
			return provider.getProviderURL();
		case API_INDEX:
			return provider.getProviderAPIKey();
		default:
			return new Object();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
	 * int, int)
	 */
	public void setValueAt(Object value, int row, int column) {
		NZBProvider provider = (NZBProvider) dataVector.get(row);
		switch (column) {
		case NAME_INDEX:
			provider.setProviderName((String) value);
			break;
		case URL_INDEX:
			provider.setProviderURL((String) value);
			break;
		case API_INDEX:
			provider.setProviderAPIKey((String) value);
			break;
		default:
			System.out.println("invalid index");
		}
		// storageService.addNZBProvider(provider);
		nzbProviders.clear();
		for (int i = 0; i < getRowCount(); i++) {
			System.out.println("saving");
			if (((NZBProvider) dataVector.get(i)).getProviderName().length() == 0) {
				System.out.println("Ignoring");
			} else {
				nzbProviders.add((NZBProvider) dataVector.get(i));
			}
		}
		storageService.setNZBProviders(nzbProviders);

		fireTableCellUpdated(row, column);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return dataVector.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	public boolean hasEmptyRow() {
		if (dataVector.size() == 0)
			return false;
		NZBProvider provider = (NZBProvider) dataVector
				.get(dataVector.size() - 1);
		if (provider.getProviderName().trim().equals("")
				&& provider.getProviderURL().trim().equals("")
				&& provider.getProviderAPIKey().trim().equals("")) {
			return true;
		} else
			return false;
	}

	public void addEmptyRow() {
		dataVector.add(new NZBProvider());
		fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
	}
}