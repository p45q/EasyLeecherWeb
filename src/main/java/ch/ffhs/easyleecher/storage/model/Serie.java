package ch.ffhs.easyleecher.storage.model;

import java.util.Date;
import java.util.UUID;

/**
 * Dies ist das Model für die Serien
 * 
 * @author thierry baumann
 *
 */
public class Serie {
	private UUID serieID;
	private String imdbId;
	private String tbdbId;

	private String serieName;
	private String serieDescription;

	private Date serieDateAdded;
	private String serieStatus;
	private int serieNumEpisodes;
	private int serieDownloadedEpisodes;
	private String airsDayOfWeek;
	private String banner;

	/**
	 * @return imdbId
	 */ 
	public String getImdbId() {
		return imdbId;
	}

	/**
	 * @param imdbId
	 */
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	/**
	 * @return tbdbId
	 */
	public String getTbdbId() {
		return tbdbId;
	}

	/**
	 * @param tbdbId
	 */
	public void setTbdbId(String tbdbId) {
		this.tbdbId = tbdbId;
	}

	/**
	 * @return airsDayOfWeek
	 */
	public String getAirsDayOfWeek() {
		return airsDayOfWeek;
	}

	/**
	 * @param airsDayOfWeek
	 */
	public void setAirsDayOfWeek(String airsDayOfWeek) {
		this.airsDayOfWeek = airsDayOfWeek;
	}

	/**
	 * @return banner
	 */
	public String getBanner() {
		return banner;
	}

	/**
	 * @param banner
	 */
	public void setBanner(String banner) {
		this.banner = banner;
	}

	/**
	 * @return serieID
	 */
	public UUID getSerieID() {
		return serieID;
	}

	/**
	 * @param serieID
	 */
	public void setSerieID(UUID serieID) {
		this.serieID = serieID;
	}

	/**
	 * @return serieName
	 */
	public String getSerieName() {
		return serieName;
	}

	/**
	 * @param serieName
	 */
	public void setSerieName(String serieName) {
		this.serieName = serieName;
	}

	/**
	 * @return serieDateAdded
	 */
	public Date getSerieDateAdded() {
		return serieDateAdded;
	}

	/**
	 * @param serieDateAdded
	 */
	public void setSerieDateAdded(Date serieDateAdded) {
		this.serieDateAdded = serieDateAdded;
	}

	/**
	 * @return serieStatus
	 */
	public String getSerieStatus() {
		return serieStatus;
	}

	/**
	 * @param serieStatus
	 */
	public void setSerieStatus(String serieStatus) {
		this.serieStatus = serieStatus;
	}

	/**
	 * @return serieDescription
	 */
	public String getSerieDescription() {
		return serieDescription;
	}

	/**
	 * @param serieDescription
	 */
	public void setSerieDescription(String serieDescription) {
		this.serieDescription = serieDescription;
	}

	/**
	 * @return serieNumEpisodes
	 */
	public int getSerieNumEpisodes() {
		return serieNumEpisodes;
	}

	/**
	 * @param serieNumEpisodes
	 */
	public void setSerieNumEpisodes(int serieNumEpisodes) {
		this.serieNumEpisodes = serieNumEpisodes;
	}

	/**
	 * @return serieDownloadedEpisodes
	 */
	public int getSerieDownloadedEpisodes() {
		return serieDownloadedEpisodes;
	}

	/**
	 * @param serieDownloadedEpisodes
	 */
	public void setSerieDownloadedEpisodes(int serieDownloadedEpisodes) {
		this.serieDownloadedEpisodes = serieDownloadedEpisodes;
	}

}
