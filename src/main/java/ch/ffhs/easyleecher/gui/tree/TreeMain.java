package ch.ffhs.easyleecher.gui.tree;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import ch.ffhs.easyleecher.gui.tree.model.TreeMainModel;
import ch.ffhs.easyleecher.storage.model.Episode;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;

/**
 * Dies ist der Baum der im Main Gui angezeigt wird
 * 
 * @author pascal bieri
 */
@SuppressWarnings("serial")
public class TreeMain extends JTree {
	private TreeMainModel model;
	private JTree tree;

	public JTree getTree() {
		model = new TreeMainModel();
		// Create a JTree and tell it to display our model
		tree = new JTree();
		tree.setModel(model);
		tree.setRootVisible(true);
		tree.setCellRenderer(new FolderCellRenderer(tree.getCellRenderer()));
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		return tree;
	}

	public void reloadTree() {
		model.reloadData();
		tree.setModel(model);
	}

	static class FolderCellRenderer extends DefaultTreeCellRenderer {
		TreeCellRenderer renderer;
		private static final ImageIcon find = new ImageIcon("./icons/find.png");
		private static final ImageIcon add = new ImageIcon("./icons/add.png");
		private static final ImageIcon accept = new ImageIcon(
				"./icons/accept.png");
		private static final ImageIcon exclamation = new ImageIcon(
				"./icons/exclamation.png");

		public FolderCellRenderer(TreeCellRenderer renderer) {
			this.renderer = renderer;
		}

		/* (non-Javadoc)
		 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
		 */
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, selected, expanded,
					leaf, row, hasFocus);

			String newValue = "";

			if ((value != null) && (value instanceof Serie)) {
				newValue = ((Serie) value).getSerieName();
			} else if ((value != null) && (value instanceof Season)) {
				newValue = "Season " + ((Season) value).getSeasonName();
			} else if ((value != null) && (value instanceof Episode)) {
				Episode episode = ((Episode) value);

				newValue = episode.getEpisodeEpisode() + " - "
						+ episode.getEpisodeName();

				switch (episode.getEpisodeStatus()) {
				case 0:
					setIcon(find);
					break;
				case 1:
					setIcon(add);
					break;
				case 2:
					setIcon(accept);
					break;
				case 5:
					setIcon(exclamation);
					break;
				}
			} else {
				newValue = "Series";
			}

			setText(newValue);
			// renderer.getTreeCellRendererComponent(tree, newValue,
			// selected, expanded, leaf, row, hasFocus);

			return this;
		}
	}
}
