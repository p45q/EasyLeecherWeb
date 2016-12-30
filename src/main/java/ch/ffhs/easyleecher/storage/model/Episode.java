package ch.ffhs.easyleecher.storage.model;

import java.util.UUID;

/**
 * Dies ist das Model für die Episoden
 * 
 * @author thierry baumann
 */
public class Episode {
	private UUID episodeID;
	private String tvdbId;

	private UUID episodeSeasonID;
	/*
	 * 0 = wanted, 1 = snatched, 2 = downloaded, 5 = notfound
	 */
	private int episodeStatus;

	private int episodeEpisode;
	private String episodeName;
	private String episodeDescription;

	public String getTvdbId() {
		return tvdbId;
	}

	/**
	 * @param tvdbId
	 */
	public void setTvdbId(String tvdbId) {
		this.tvdbId = tvdbId;
	}

	/**
	 * @return episodeStatus
	 */
	public int getEpisodeStatus() {
		return episodeStatus;
	}

	/**
	 * @param episodeStatus
	 */
	public void setEpisodeStatus(int episodeStatus) {
		this.episodeStatus = episodeStatus;
	}

	/**
	 * @return episodeID
	 */
	public UUID getEpisodeID() {
		return episodeID;
	}

	/**
	 * @param episodeSeasonID
	 */
	public void setEpisodeSeasonID(UUID episodeSeasonID) {
		this.episodeSeasonID = episodeSeasonID;
	}

	/**
	 * @param episodeID
	 */
	public void setEpisodeID(UUID episodeID) {
		this.episodeID = episodeID;
	}

	/**
	 * @return episodeSeasonID
	 */
	public UUID getEpisodeSeasonID() {
		return episodeSeasonID;
	}

	/**
	 * @param episodeSeasonID
	 */
	public void setEpisodeSerieID(UUID episodeSeasonID) {
		this.episodeSeasonID = episodeSeasonID;
	}

	/**
	 * @return episodeEpisode
	 */
	public int getEpisodeEpisode() {
		return episodeEpisode;
	}

	/**
	 * @param episodeEpisode
	 */
	public void setEpisodeEpisode(int episodeEpisode) {
		this.episodeEpisode = episodeEpisode;
	}

	/**
	 * @return episodeName
	 */
	public String getEpisodeName() {
		return episodeName;
	}

	/**
	 * @param episodeName
	 */
	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}

	/**
	 * @return episodeDescription
	 */
	public String getEpisodeDescription() {
		return episodeDescription;
	}

	/**
	 * @param episodeDescription
	 */
	public void setEpisodeDescription(String episodeDescription) {
		this.episodeDescription = episodeDescription;
	}
}
