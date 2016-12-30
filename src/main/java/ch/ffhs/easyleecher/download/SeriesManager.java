package ch.ffhs.easyleecher.download;

import java.util.Date;
import java.util.UUID;

import ch.ffhs.easyleecher.storage.StorageService;
import ch.ffhs.easyleecher.storage.model.Episode;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;
import ch.ffhs.easyleecher.tvdb.model.TvDbEpisode;
import ch.ffhs.easyleecher.tvdb.model.TvDbSeries;

/**
 * Diese Klasse wird verwendet um neue Serien, Seasons und Episoden hinzuzufügen
 * 
 * @author thierry baumann
 */
public class SeriesManager {
	private StorageService storageService;

	public SeriesManager() {
		storageService = StorageService.getInstance();
	}

	/**
	 * Zentrale Funktion um eine neue Serie hinzuzufügen
	 * 
	 * @param tvDbSeries, serie objekt von tbdb api
	 * @return serie
	 */
	public Serie addSerie(TvDbSeries tvDbSeries) {
		Serie serie = new Serie();

		serie.setSerieID(UUID.randomUUID());
		serie.setImdbId(tvDbSeries.getImdbId());
		serie.setTbdbId(tvDbSeries.getId());

		serie.setSerieDateAdded(new Date());
		serie.setSerieDescription(tvDbSeries.getOverview());
		serie.setSerieName(tvDbSeries.getSeriesName());
		serie.setSerieStatus(tvDbSeries.getStatus());

		serie.setAirsDayOfWeek(tvDbSeries.getAirsDayOfWeek());
		serie.setBanner(tvDbSeries.getBanner());

		storageService.addSerie(serie);

		return serie;
	}

	/**
	 * Zentrale Funktion um eine neue Season hinzuzufügen
	 * 
	 * @param seasonNumber
	 * @param serie
	 * @return season
	 */
	public Season addSeason(String seasonNumber, Serie serie) {
		Season season = new Season();

		season.setSeasonID(UUID.randomUUID());
		season.setSerieID(serie.getSerieID());
		season.setSeasonName(seasonNumber);

		storageService.addSeason(season);

		return season;
	}

	/**
	 * Zentrale Funktion um eine neue Episoe hinzuzufügen
	 * 
	 * @param tvDbEpisod
	 *            , episoden objekt von tbdb api
	 * @param season
	 * @return episode
	 */
	public Episode addEpisode(TvDbEpisode tvDbEpisode, Season season) {
		Episode episode = new Episode();

		episode.setEpisodeID(UUID.randomUUID());
		episode.setTvdbId(tvDbEpisode.getId());

		episode.setEpisodeSeasonID(season.getSeasonID());
		episode.setEpisodeName(tvDbEpisode.getEpisodeName());
		episode.setEpisodeDescription(tvDbEpisode.getOverview());
		episode.setEpisodeEpisode(tvDbEpisode.getEpisodeNumber());
		episode.setEpisodeEpisode(tvDbEpisode.getEpisodeNumber());

		storageService.addEpisode(episode);

		return episode;
	}

}