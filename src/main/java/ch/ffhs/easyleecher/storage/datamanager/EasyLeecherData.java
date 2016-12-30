package ch.ffhs.easyleecher.storage.datamanager;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import ch.ffhs.easyleecher.storage.model.Episode;
import ch.ffhs.easyleecher.storage.model.GlobalSettings;
import ch.ffhs.easyleecher.storage.model.NZBProvider;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;

/**
 * Diese Klasse definiert die Daten des EasyLeecher
 * 
 * @author thierry baumann, pascal bieri
 * 
 */
@XmlRootElement(namespace = "storage")
public class EasyLeecherData {
	private ArrayList<Serie> series;
	private ArrayList<Episode> episodes;
	private ArrayList<Season> seasons;
	private GlobalSettings globalSettings;
	private ArrayList<NZBProvider> nzbProviders;

	public EasyLeecherData() {
		series = new ArrayList<Serie>();
		episodes = new ArrayList<Episode>();
		seasons = new ArrayList<Season>();
		nzbProviders = new ArrayList<NZBProvider>();
	}

	/**
	 * @return series
	 */
	public ArrayList<Serie> getSeries() {
		return series;
	}

	/**
	 * @param series
	 */
	public void setSeries(ArrayList<Serie> series) {
		this.series = series;
	}

	/**
	 * @param serie
	 */
	public void addSerie(Serie serie) {
		this.series.add(serie);
	}

	/**
	 * @return episodes
	 */
	public ArrayList<Episode> getEpisodes() {
		return episodes;
	}

	/**
	 * @param episodes
	 */
	public void setEpisodes(ArrayList<Episode> episodes) {
		this.episodes = episodes;
	}

	/**
	 * @param episode
	 */
	public void addEpisode(Episode episode) {
		this.episodes.add(episode);
	}

	/**
	 * @return seasons
	 */
	public ArrayList<Season> getSeasons() {
		return seasons;
	}

	/**
	 * @param seasons
	 */
	public void setSeasons(ArrayList<Season> seasons) {
		this.seasons = seasons;
	}

	/**
	 * @param season
	 */
	public void addSeason(Season season) {
		this.seasons.add(season);
	}

	/**
	 * @return globalSettings
	 */
	public GlobalSettings getGlobalSettings() {
		return this.globalSettings;
	}

	/**
	 * @param globalSettings
	 */
	public void setGlobalSettings(GlobalSettings globalSettings) {
		this.globalSettings = globalSettings;
	}

	/**
	 * @return NZB Provider
	 */
	public ArrayList<NZBProvider> getNzbProviders() {
		return nzbProviders;
	}

	/**
	 * @param nzbProviders
	 */
	public void setNzbProviders(ArrayList<NZBProvider> nzbProviders) {
		this.nzbProviders = nzbProviders;
	}
}
