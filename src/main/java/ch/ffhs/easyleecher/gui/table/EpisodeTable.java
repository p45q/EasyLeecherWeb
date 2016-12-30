package ch.ffhs.easyleecher.gui.table;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import ch.ffhs.easyleecher.storage.model.Episode;

/**
 * Dies ist das Model für die Episoden Tabelle, wird verwendet um alle Episoden
 * einer Season anzuzeigen
 * 
 * @author thierry baumann
 */
@SuppressWarnings("serial")
public class EpisodeTable extends AbstractTableModel {
	private CopyOnWriteArrayList<Episode> episodes;
	private Vector<TableModelListener> listeners;

	public EpisodeTable() {
		super();

		episodes = new CopyOnWriteArrayList<Episode>();
		listeners = new Vector<TableModelListener>();
	}

	/**
	 * @param episodes
	 */
	public void addEpisodes(ArrayList<Episode> episodes) {
		clearTable();

		for (Episode episode : episodes) {
			addEpisode(episode);
		}
	}

	/**
	 * @param episode
	 */
	public void addEpisode(Episode episode) {
		int index = episodes.size();

		// neues episode zur arrayliste hinzufügen
		episodes.add(0, episode);

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
	public int getColumnCount() {
		return 3;
	}

	/*
	 * die anzahl episoden
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return episodes.size();
	}

	/*
	 * die titel der einzelnen columns
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Episode";
		case 1:
			return "Title";
		case 2:
			return "Status";

		default:
			return null;
		}
	}

	/**
	 * @param rowIndex
	 * @return
	 */
	public Episode getEpisodeAt(int rowIndex) {
		return (Episode) episodes.get(rowIndex);
	}

	/*
	 * der wert der telle (rowIndex, columnIndex)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Episode episode = (Episode) episodes.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return episode.getEpisodeEpisode();
		case 1:
			return episode.getEpisodeName();
		case 2:
			switch (episode.getEpisodeStatus()) {
			case 0:
				return "Wanted";
			case 1:
				return "Snatched";
			case 2:
				return "Downloaded";
			default:
				return "not found";
			}
		default:
			return null;
		}
	}

	/*
	 * Definition welcher typ von objekten in den columns angezeigt werden soll
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	public Class<String> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		default:
			return null;
		}
	}

	public void clearTable() {
		episodes.clear();
		fireAllListeners(null);
	}

	/**
	 * @param event
	 */
	private void fireAllListeners(TableModelEvent event) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			((TableModelListener) listeners.get(i)).tableChanged(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.AbstractTableModel#addTableModelListener(javax.swing
	 * .event.TableModelListener)
	 */
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
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
	 * int, int)
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}
}