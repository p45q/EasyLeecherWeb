package ch.ffhs.easyleecher.gui.worker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import ch.ffhs.easyleecher.download.SeriesManager;
import ch.ffhs.easyleecher.gui.GuiMain;
import ch.ffhs.easyleecher.storage.StorageService;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;
import ch.ffhs.easyleecher.tvdb.TheTVDBApi;
import ch.ffhs.easyleecher.tvdb.model.TvDbEpisode;
import ch.ffhs.easyleecher.tvdb.model.TvDbSeries;

/**
 * Diese Klasse wird verwendet um nach Serien zu suchen und ermöglicht das
 * hinzufügen von Serien
 * 
 * @author thierry baumann
 * 
 */
@SuppressWarnings("rawtypes")
public class AddSerieFrameTvDbWorker extends SwingWorker {
	private TheTVDBApi tvDbApi;
	private SeriesManager seriesManager;

	private int cmd;

	private JPanel searchResultWrapper;
	private JTextField searchField;
	private JButton addButton;
	private ButtonGroup buttonGroup;
	private String serieId;
	private GuiMain gui;
	private boolean test;

	/**
	 * @param searchResultWrapper
	 * @param searchField
	 * @param addButton
	 * @param buttonGroup
	 */
	public AddSerieFrameTvDbWorker(JPanel searchResultWrapper,
			JTextField searchField, JButton addButton, ButtonGroup buttonGroup) {
		this(1, searchResultWrapper, searchField, addButton, buttonGroup, null,
				false);
	}

	/**
	 * @param serieId
	 */
	public AddSerieFrameTvDbWorker(String serieId) {
		this(2, null, null, null, null, serieId, false);
	}

	/**
	 * @param serieId
	 * @param test
	 */
	public AddSerieFrameTvDbWorker(String serieId, boolean test) {
		this(2, null, null, null, null, serieId, test);
	}

	/**
	 * @param cmd
	 * @param searchResultWrapper
	 * @param searchField
	 * @param addButton
	 * @param buttonGroup
	 * @param serieId
	 * @param test
	 */
	public AddSerieFrameTvDbWorker(int cmd, JPanel searchResultWrapper,
			JTextField searchField, JButton addButton, ButtonGroup buttonGroup,
			String serieId, boolean test) {
		tvDbApi = new TheTVDBApi(StorageService.getInstance()
				.getGlobalsettings().getTvdbAPIKey());
		seriesManager = new SeriesManager();
		this.cmd = cmd;
		this.searchResultWrapper = searchResultWrapper;
		this.searchField = searchField;
		this.addButton = addButton;
		this.buttonGroup = buttonGroup;
		this.serieId = serieId;
		this.test = test;

		if (!test) {
			gui = GuiMain.getInstance();
		} else {
			gui = null;
		}
	}

	@Override
	protected AddSerieFrameTvDbWorker doInBackground() throws Exception {
		switch (cmd) {
		// search
		case 1:
			searchSerie();
			break;

		// add complete series
		case 2:
			addCompleteSerie();
			break;
		}
		return null;
	}

	private void searchSerie() {
		String searchString = searchField.getText();

		addButton.setVisible(false);
		searchResultWrapper.removeAll();

		if (searchString.length() > 2) {
			List<TvDbSeries> list = tvDbApi.searchSeries(searchString, "en");

			if (!list.isEmpty()) {
				showSearchResult(list);
				addButton.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "No result",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(),
					"Enter at least three letters", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void showSearchResult(List<TvDbSeries> list) {
		for (TvDbSeries serie : list) {
			System.out.println(serie.getSeriesName() + " loop");

			JRadioButton button = new JRadioButton(serie.getSeriesName());
			button.setActionCommand(serie.getId());

			buttonGroup.add(button);

			searchResultWrapper.add(button);
		}

		searchResultWrapper.revalidate();
		searchResultWrapper.repaint();
	}

	private void addCompleteSerie() {
		TvDbSeries serieOri = tvDbApi.getSeries(serieId, "en");

		updateProgress("Processing new serie " + serieOri.getSeriesName());

		TvDbSeries tvDbSeries = tvDbApi.getSeries(serieId, "en");
		Serie serie = seriesManager.addSerie(tvDbSeries);

		List<TvDbEpisode> episodes = tvDbApi.getAllEpisodes(serieId, "en");

		updateProgress("Fetching episodes for " + serieOri.getSeriesName()
				+ " 0/" + episodes.size());

		Map<Integer, Season> seasonsTemp = new HashMap<Integer, Season>();
		int done = 0;
		for (TvDbEpisode tvDbEpisode : episodes) {
			done++;
			Integer seasonNumber = tvDbEpisode.getSeasonNumber();
			if (seasonNumber > 0) {
				Season season = seasonsTemp.get(seasonNumber);

				if (season == null) {
					season = seriesManager.addSeason(seasonNumber.toString(),
							serie);
					seasonsTemp.put(seasonNumber, season);
				}

				seriesManager.addEpisode(tvDbEpisode, season);

				updateProgress("Fetching episodes for "
						+ serieOri.getSeriesName() + " " + done + "/"
						+ episodes.size());
			}
		}

		updateProgress("Successfully added " + serieOri.getSeriesName());

		if (!test) {
			gui.loadTree();
		}
	}

	private void updateProgress(final String progress) {
		if (!test) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					gui.setStatus(progress);
				}
			});
		}
	}
}