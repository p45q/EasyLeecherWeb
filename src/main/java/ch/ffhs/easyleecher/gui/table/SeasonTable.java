package ch.ffhs.easyleecher.gui.table;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import ch.ffhs.easyleecher.gui.table.model.SeasonModel;

/**
 * Dies ist das Model für die Season Tabelle, wird verwendet um sämtliche
 * Seasons einer Serie aufzulisten
 * 
 * @author thierry baumann
 * 
 */
@SuppressWarnings("serial")
public class SeasonTable extends AbstractTableModel {
	private CopyOnWriteArrayList<SeasonModel> seasons;
	private Vector<TableModelListener> listeners;

	public SeasonTable() {
		super();

		seasons = new CopyOnWriteArrayList<SeasonModel>();
		listeners = new Vector<TableModelListener>();
	}

	/**
	 * @param Seasons
	 */
	public void addSeasons(ArrayList<SeasonModel> Seasons) {
		clearTable();

		for (SeasonModel season : seasons) {
			addSeason(season);
		}
	}

	/**
	 * @param season
	 */
	public void addSeason(SeasonModel season) {
		int index = seasons.size();

		// neues Season zur arrayliste hinzufügen
		seasons.add(0, season);

		// zuerst ein event, "neue row an der stelle index" herstellen
		TableModelEvent event = new TableModelEvent(this, index, index,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);

		// nun das event verschicken
		fireAllListeners(event);
	}

	/*
	 * die anzahl columns
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 6;
	}

	/*
	 * die anzahl seasons
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return seasons.size();
	}

	/*
	 * die titel der einzelnen columns
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Season";
		case 1:
			return "Episodes";
		case 2:
			return "Wanted";
		case 3:
			return "Snatched";
		case 4:
			return "Downloaded";
		case 5:
			return "Not found";
		default:
			return null;
		}
	}

	/**
	 * @param rowIndex
	 * @return
	 */
	public SeasonModel getSeasonAt(int rowIndex) {
		return seasons.get(rowIndex);
	}

	/*
	 * der wert der telle (rowIndex, columnIndex)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		SeasonModel season = seasons.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return season.getSeasonName();
		case 1:
			return season.getTotalEpisodes();
		case 2:
			return season.getWanted();
		case 3:
			return season.getSnatched();
		case 4:
			return season.getDownloaded();
		case 5:
			return season.getNotfound();
		}

		return null;
	}

	/*
	 * eine angabe, welchen typ von objekten in den columns angezeigt werden
	 * soll
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<String> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return String.class;
		case 5:
			return String.class;
		default:
			return null;
		}
	}

	public void clearTable() {
		seasons.clear();
		fireAllListeners(null);
	}

	/**
	 * @param event
	 */
	private void fireAllListeners(TableModelEvent event) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			listeners.get(i).tableChanged(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.AbstractTableModel#addTableModelListener(javax.swing
	 * .event.TableModelListener)
	 */
	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.AbstractTableModel#removeTableModelListener(javax.swing
	 * .event.TableModelListener)
	 */
	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
	 * int, int)
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}
}