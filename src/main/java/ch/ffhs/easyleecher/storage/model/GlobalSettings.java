package ch.ffhs.easyleecher.storage.model;

/**
 * Dies ist das Model für die Einstellungen
 * 
 * @author pascal bieri
 */
public class GlobalSettings {
	private String seriesRootFolder;
	private String nzbBlackholeFolder;
	private String downloadFolder;
	private int searchInterval;
	private String tvdbAPIKey;

	public GlobalSettings() {

	}

	/**
	 * @return seriesRootFolder
	 */
	public String getSeriesRootFolder() {
		return seriesRootFolder;
	}

	/**
	 * @param seriesRootFolder
	 */
	public void setSeriesRootFolder(String seriesRootFolder) {
		this.seriesRootFolder = seriesRootFolder;
	}

	/**
	 * @return nzbBlackholeFolder
	 */
	public String getNzbBlackholeFolder() {
		return nzbBlackholeFolder;
	}

	/**
	 * @param nzbBlackholeFolder
	 */
	public void setNzbBlackholeFolder(String nzbBlackholeFolder) {
		this.nzbBlackholeFolder = nzbBlackholeFolder;
	}

	/**
	 * @return downloadFolder
	 */
	public String getDownloadFolder() {
		return downloadFolder;
	}

	/**
	 * @param downloadFolder
	 */
	public void setDownloadFolder(String downloadFolder) {
		this.downloadFolder = downloadFolder;
	}

	/**
	 * @return searchInterval
	 */
	public int getSearchInterval() {
		return searchInterval;
	}

	/**
	 * @param searchInterval
	 */
	public void setSearchInterval(int searchInterval) {
		this.searchInterval = searchInterval;
	}

	/**
	 * @return tvdbAPIKey
	 */
	public String getTvdbAPIKey() {
		return tvdbAPIKey;
	}

	/**
	 * @param tvdbAPIKey
	 */
	public void setTvdbAPIKey(String tvdbAPIKey) {
		this.tvdbAPIKey = tvdbAPIKey;
	}

}
