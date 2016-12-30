package ch.ffhs.easyleecher.gui.table.model;

/**
 * Diese Klasse ist das Model zur Season Tabelle
 * 
 * @author thierry baumann
 */
public class SeasonModel {

	private String seasonName;
	private int totalEpisodes;
	private int wanted;
	private int snatched;
	private int downloaded;
	private int notfound;

	public SeasonModel() {
	}

	/**
	 * @return
	 */
	public int getWanted() {
		return wanted;
	}

	/**
	 * @param wanted
	 */
	public void setWanted(int wanted) {
		this.wanted = wanted;
	}

	/**
	 * @return
	 */
	public int getSnatched() {
		return snatched;
	}

	/**
	 * @param snatched
	 */
	public void setSnatched(int snatched) {
		this.snatched = snatched;
	}

	/**
	 * @return
	 */
	public int getDownloaded() {
		return downloaded;
	}

	/**
	 * @param downloaded
	 */
	public void setDownloaded(int downloaded) {
		this.downloaded = downloaded;
	}

	/**
	 * @return
	 */
	public int getNotfound() {
		return notfound;
	}

	/**
	 * @param notfound
	 */
	public void setNotfound(int notfound) {
		this.notfound = notfound;
	}

	/**
	 * @return
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
	 * @return
	 */
	public int getTotalEpisodes() {
		return totalEpisodes;
	}

	/**
	 * @param totalEpisodes
	 */
	public void setTotalEpisodes(int totalEpisodes) {
		this.totalEpisodes = totalEpisodes;
	}

}