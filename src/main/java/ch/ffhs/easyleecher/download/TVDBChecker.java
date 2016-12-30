package ch.ffhs.easyleecher.download;

import java.util.ArrayList;
import java.util.List;

import ch.ffhs.easyleecher.main.Logging;
import ch.ffhs.easyleecher.storage.StorageService;
import ch.ffhs.easyleecher.storage.model.Episode;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;
import ch.ffhs.easyleecher.tvdb.TheTVDBApi;
import ch.ffhs.easyleecher.tvdb.model.TvDbEpisode;

/**
 * Diese Klasse prüft ob eine neue Episode erschienen ist
 * 
 * @author thierrybaumann
 */
public class TVDBChecker implements Runnable {
	private TheTVDBApi tvDbApi;
	private StorageService storageService;
	private SeriesManager seriesManager;

	private ArrayList<Serie> series;

	public TVDBChecker() {
		storageService = StorageService.getInstance();
		tvDbApi = new TheTVDBApi(storageService.getGlobalsettings()
				.getTvdbAPIKey());
		seriesManager = new SeriesManager();

		series = new ArrayList<Serie>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		Logging.logMessage("TVDBChecker:: called");

		series = storageService.getSeries();

		for (Serie serie : series) {
			processSerie(serie);
		}

		Logging.logMessage("TVDBChecker:: done");
	}

	private void processSerie(Serie serie) {
		Logging.logMessage("TVDBChecker:: looking for new episodes "
				+ serie.getSerieName());

		List<TvDbEpisode> episodes = tvDbApi.getAllEpisodes(serie.getTbdbId(),
				"en");

		for (TvDbEpisode tvDbEpisode : episodes) {

			Integer seasonNumber = tvDbEpisode.getSeasonNumber();
			if (seasonNumber > 0) {
				// check new season
				Season season = storageService.getSerieSeasonByName(serie,
						seasonNumber.toString());
				if (season == null) {
					// add new season
					season = seriesManager.addSeason(seasonNumber.toString(),
							serie);
					Logging.logMessage("TVDBChecker:: New season found "
							+ season.getSeasonName());
				}

				// check new episode
				Episode episode = storageService.getEpisodeByTvDbId(tvDbEpisode
						.getId());
				if (episode == null) {
					// add new episode
					seriesManager.addEpisode(tvDbEpisode, season);
					Logging.logMessage("TVDBChecker:: New episode found "
							+ tvDbEpisode.getEpisodeNumber());
				}
			}
		}
	}
}