package ch.ffhs.easyleecher.storage.model;

import java.util.UUID;

/**
 * Dies ist das Model für die Seasons
 * 
 * @author thierry baumann
 */
public class Season {
	private UUID seasonID;
	private String seasonName;
	private UUID serieID;

	/**
	 * @return seasonID
	 */
	public UUID getSeasonID() {
		return seasonID;
	}

	/**
	 * @param seasonID
	 */
	public void setSeasonID(UUID seasonID) {
		this.seasonID = seasonID;
	}

	/**
	 * @return seasonName
	 */
	public String getSeasonName() {
		return seasonName;
	}

	/**
	 * @param seasonName
	 */
	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
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
}
