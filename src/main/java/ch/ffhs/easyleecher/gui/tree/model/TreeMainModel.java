package ch.ffhs.easyleecher.gui.tree.model;

import java.util.ArrayList;
import java.util.UUID;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import ch.ffhs.easyleecher.main.Logging;
import ch.ffhs.easyleecher.storage.StorageService;
import ch.ffhs.easyleecher.storage.model.Episode;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;

/**
 * Dies ist das Modell zum Main Tree
 * 
 * @author pascal bieri
 */
public class TreeMainModel implements TreeModel {
	// We specify the root directory when we create the model.
	private String root;
	private ArrayList<Serie> series;
	private ArrayList<Season> seasons;
	private ArrayList<Episode> episodes;
	private ArrayList<TreeModelListener> listeners = new ArrayList<TreeModelListener>();

	public TreeMainModel() {
		loadData();
	}

	private void loadData() {
		StorageService storageService = StorageService.getInstance();
		series = storageService.getSeries();
		seasons = storageService.getSeasons();
		episodes = storageService.getEpisodes();
		root = "Series";
	}

	/*
	 * The model knows how to return the root object of the tree
	 * 
	 * @see javax.swing.tree.TreeModel#getRoot()
	 */
	public Object getRoot() {
		return "Series";
	}

	/*
	 * Tell JTree whether an object in the tree is a leaf
	 * 
	 * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
	 */
	public boolean isLeaf(Object node) {
		if (node.getClass() == Episode.class) {
			return true;
		}
		return false;

	}

	/*
	 * Tell JTree how many children a node has
	 * 
	 * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
	 */
	public int getChildCount(Object parent) {
		Logging.logMessage("Get Child Count");
		if (parent == root) {
			return series.size();
		}
		if (parent.getClass() == Episode.class) {
			Logging.logMessage("-- Parent is ROOT");
			return 0;
		} else if (parent.getClass() == Serie.class) {
			Logging.logMessage("-- Parent is Serie");
			// NASTY
			Serie serie1 = (Serie) parent;
			UUID serieID = serie1.getSerieID();
			int count = 0;
			for (Season s : seasons) {
				UUID seasonSerieID = s.getSerieID();
				if (seasonSerieID != null && serieID != null) {
					if (seasonSerieID.compareTo(serieID) == 0) {
						count++;
					}
				}
			}
			return count;
		}
		if (parent.getClass() == Season.class) {
			Logging.logMessage("-- Parent is Season");
			// NASTY
			Season season1 = (Season) parent;
			UUID seasonID = season1.getSeasonID();
			int count = 0;
			for (Episode s : episodes) {
				Logging.logMessage("-- Looping trough episode: "
						+ s.getEpisodeName());
				Logging.logMessage("-- Episode has season uuid: "
						+ s.getEpisodeSeasonID() + "And season has: "
						+ seasonID);
				UUID episodeSeasonID = s.getEpisodeSeasonID();
				if (episodeSeasonID != null && season1 != null) {
					if (episodeSeasonID.compareTo(seasonID) == 0) {
						count++;
					}
				}

			}
			Logging.logMessage("-- Returning count " + count);
			return count;
		} else {
			return 0;
		}
	}

	/*
	 * Fetch any numbered child of a node for the JTree. Our model returns File
	 * objects for all nodes in the tree. The JTree displays these by calling
	 * the File.toString() method.
	 * 
	 * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
	 */
	public Object getChild(Object parent, int index) {
		if (parent == root) {
			Logging.logMessage("Parent is root");
			return series.get(index);
		} else if (parent.getClass() == Serie.class) {
			// NOT TRUE
			Logging.logMessage("Parent is serie");
			Serie serie1 = (Serie) parent;
			UUID serieID = serie1.getSerieID();
			int count = 0;
			for (Season s : seasons) {
				UUID seasonSerieID = s.getSerieID();
				if (seasonSerieID != null && serieID != null) {
					if (seasonSerieID.compareTo(serieID) == 0) {
						if (index == count)
							return s;
						count++;
					}
				}
			}
			return -1;
		} else if (parent.getClass() == Season.class) {
			// NOT TRUE
			Logging.logMessage("Parent is season");
			Season season1 = (Season) parent;
			UUID seasonID = season1.getSeasonID();
			int count = 0;
			for (Episode s : episodes) {
				UUID episodeSeasonID = s.getEpisodeSeasonID();
				Logging.logMessage("Comparing: " + episodeSeasonID + " AND "
						+ season1);
				if (episodeSeasonID != null && seasonID != null) {
					if (episodeSeasonID.compareTo(seasonID) == 0) {
						if (index == count)
							return s;
						count++;
					}
				}
			}
			return -1;
		} else {
			return -1;
		}

	}

	/*
	 * Figure out a child's position in its parent node.
	 * 
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object,
	 * java.lang.Object)
	 */
	public int getIndexOfChild(Object parent, Object child) {
		if (parent == root) {
			return series.indexOf(child);
		} else if (parent.getClass() == Serie.class) {
			// NOT TRUE
			return seasons.indexOf(child);
		} else if (parent.getClass() == Season.class) {
			// NOT TRUE
			return episodes.indexOf(child);
		} else {
			return -1;
		}
	}

	/*
	 * This method is invoked by the JTree only for editable trees. This
	 * TreeModel does not allow editing, so we do not implement this method. The
	 * JTree editable property is false by default.
	 * 
	 * @see
	 * javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath,
	 * java.lang.Object)
	 */
	public void valueForPathChanged(TreePath path, Object newvalue) {
	}

	public void reloadData() {
		seasons.clear();
		episodes.clear();
		series.clear();
		loadData();
		fireTreeStructureChanged();
	}

	private void fireTreeStructureChanged() {
		Object[] o = { root };
		TreeModelEvent e = new TreeModelEvent(this, o);
		for (TreeModelListener l : listeners) {
			l.treeStructureChanged(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.
	 * TreeModelListener)
	 */
	public void addTreeModelListener(TreeModelListener l) {
		listeners.add(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.
	 * TreeModelListener)
	 */
	public void removeTreeModelListener(TreeModelListener l) {
		listeners.remove(l);
	}
}
