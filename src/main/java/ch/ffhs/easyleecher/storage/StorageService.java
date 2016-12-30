package ch.ffhs.easyleecher.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.logging.Logger;

import ch.ffhs.easyleecher.main.Logging;
import ch.ffhs.easyleecher.storage.datamanager.DataManagerMarshal;
import ch.ffhs.easyleecher.storage.datamanager.DataManagerUnmarshal;
import ch.ffhs.easyleecher.storage.datamanager.EasyLeecherData;
import ch.ffhs.easyleecher.storage.model.Episode;
import ch.ffhs.easyleecher.storage.model.GlobalSettings;
import ch.ffhs.easyleecher.storage.model.NZBProvider;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;

/**
 * Diese Klasse wird verwendet um den Storage aus anderen Klassen zu verenden
 * 
 * @author thierry baumann, pascal bieri
 */
public class StorageService {
	private static ArrayList<Serie> series;
	private static ArrayList<Episode> episodes;
	private static ArrayList<Season> seasons;
	private static ArrayList<NZBProvider> providers;
	private static GlobalSettings globalsettings;
	private static final String EASYLEECHER_XML = "/apps/easyleecher-data.xml";
	private static StorageService instance = new StorageService();

	private StorageService() {
		series = new ArrayList<Serie>();
		seasons = new ArrayList<Season>();
		episodes = new ArrayList<Episode>();
		providers = new ArrayList<NZBProvider>();
		globalsettings = new GlobalSettings();

		init();
	}

	private void init() {
		Logger log = Logger.getLogger(this.getClass().getName());

		File f = new File(EASYLEECHER_XML);
		if (f.exists()) {
			log.info("FILE FOUND DUUUFFFTE");

			loaddata();
		} else {
			log.info("NO FILE FUUUU");

			// default values
			globalsettings.setSearchInterval(4);
			globalsettings.setDownloadFolder("");
			globalsettings.setNzbBlackholeFolder("");
			globalsettings.setSeriesRootFolder("");
			globalsettings.setTvdbAPIKey("");
		}
	}

	/**
	 * @return instance
	 */
	public static StorageService getInstance() {
		if (instance == null) {
			instance = new StorageService();
		}

		return instance;
	}

	/**
	 * @return series
	 */
	public ArrayList<Serie> getSeries() {
		return series;
	}

	/**
	 * @return seasons
	 */
	public ArrayList<Season> getSeasons() {
		return seasons;
	}

	/**
	 * @return episodes
	 */
	public ArrayList<Episode> getEpisodes() {
		return episodes;
	}

	/**
	 * @return providers
	 */
	public ArrayList<NZBProvider> getNZBProviders() {
		return providers;
	}

	/**
	 * @return globalsettings
	 */
	public GlobalSettings getGlobalsettings() {
		return globalsettings;
	}

	private void loaddata() {
		DataManagerUnmarshal unmashaler = new DataManagerUnmarshal(
				EASYLEECHER_XML);

		series = unmashaler.getSeries();
		seasons = unmashaler.getSeasons();
		episodes = unmashaler.getEpisodes();
		providers = unmashaler.getNZBProviders();
		globalsettings = unmashaler.getGlobalSettings();
	}

	/**
	 * @param providers
	 */
	public void setNZBProviders(ArrayList<NZBProvider> providers) {
		StorageService.providers = providers;
		saveEasyLeecherData();
	}

	/**
	 * @param provider
	 */
	public void addNZBProvider(NZBProvider provider) {
		System.out.println("Providerdata:" + provider.getProviderName() + "url"
				+ provider.getProviderURL() + "API"
				+ provider.getProviderAPIKey() + "SIZE OF PROVIDERS:"
				+ providers.size());
		// providers.add(provider);
		providers.add(new NZBProvider());
		saveEasyLeecherData();
	}

	/**
	 * @param serie
	 */
	public void addSerie(Serie serie) {
		series.add(serie);
		saveEasyLeecherData();
	}

	/**
	 * @param serie
	 */
	public void removeSerie(Serie serie) {
		ArrayList<Season> seasons = getSerieSeasons(serie);
		for (Season season : seasons) {
			removeSeason(season);
		}

		series.remove(serie);
		saveEasyLeecherData();
	}

	/**
	 * @param season
	 */
	public void addSeason(Season season) {
		seasons.add(season);
		saveEasyLeecherData();
	}

	/**
	 * @param season
	 */
	public void removeSeason(Season season) {
		ArrayList<Episode> episodes = getSeasonEpisodes(season);
		for (Episode episode : episodes) {
			removeEpisode(episode);
		}

		seasons.remove(season);
		saveEasyLeecherData();
	}

	/**
	 * @param episode
	 */
	public void addEpisode(Episode episode) {
		episodes.add(episode);
		saveEasyLeecherData();
	}

	/**
	 * @param episode
	 */
	public void removeEpisode(Episode episode) {
		episodes.remove(episode);
		saveEasyLeecherData();
	}

	/**
	 * @param globalsettings
	 */
	public void setGlobalsettings(GlobalSettings globalsettings) {
		StorageService.globalsettings = globalsettings;
		saveEasyLeecherData();
	}

	/**
	 * @param episode
	 * @return season
	 */
	public Season getEpisodeSeason(Episode episode) {
		for (Season season : seasons) {
			if (season.getSeasonID().equals(episode.getEpisodeSeasonID())) {
				return season;
			}
		}
		return null;
	}

	/**
	 * @param serie
	 * @param seasonName
	 * @return season
	 */
	public Season getSerieSeasonByName(Serie serie, String seasonName) {
		for (Season season : seasons) {
			if (season.getSerieID().equals(serie.getSerieID())
					&& season.getSeasonName().equals(seasonName)) {
				return season;
			}
		}
		return null;
	}

	/**
	 * @param season
	 * @return serie
	 */
	public Serie getSeasonSerie(Season season) {
		for (Serie serie : series) {
			if (serie.getSerieID().equals(season.getSerieID())) {
				return serie;
			}
		}
		return null;
	}

	/**
	 * @param episode
	 * @return serie
	 */
	public Serie getEpisodeSerie(Episode episode) {
		Season season = getEpisodeSeason(episode);
		if (season != null) {
			Serie serie = getSeasonSerie(season);
			return serie;
		}
		return null;
	}

	/**
	 * @param tvdbId
	 * @return episode
	 */
	public Episode getEpisodeByTvDbId(String tvdbId) {
		for (Episode episode : episodes) {
			if (episode.getTvdbId().equals(tvdbId)) {
				return episode;
			}
		}
		return null;
	}

	/**
	 * @param episodeId
	 * @return episode
	 */
	public Episode getEpisodeById(String episodeId) {
		UUID episodeUuId = UUID.fromString(episodeId);

		for (Episode episode : episodes) {
			if (episode.getEpisodeID().equals(episodeUuId)) {
				return episode;
			}
		}
		return null;
	}

	/**
	 * @param season
	 * @return episodes
	 */
	public ArrayList<Episode> getSeasonEpisodes(Season season) {
		ArrayList<Episode> seasonEpisodes = new ArrayList<Episode>();

		for (Episode episode : episodes) {
			if (episode.getEpisodeSeasonID().equals(season.getSeasonID())) {
				seasonEpisodes.add(episode);
			}
		}

		Collections.reverse(seasonEpisodes);

		return seasonEpisodes;
	}

	/**
	 * @param serie
	 * @return season
	 */
	public ArrayList<Season> getSerieSeasons(Serie serie) {
		ArrayList<Season> serieSeasons = new ArrayList<Season>();

		for (Season season : seasons) {
			if (season.getSerieID().equals(serie.getSerieID())) {
				serieSeasons.add(season);
			}
		}

		Collections.reverse(serieSeasons);

		return serieSeasons;
	}

	public void saveEasyLeecherData() {
		Logging.logMessage("SAVED");
		EasyLeecherData dataObj = new EasyLeecherData();
		dataObj.setSeries(series);
		dataObj.setSeasons(seasons);
		dataObj.setEpisodes(episodes);
		dataObj.setNzbProviders(providers);
		dataObj.setGlobalSettings(globalsettings);
		new DataManagerMarshal(EASYLEECHER_XML, dataObj).execute();
	}
}
