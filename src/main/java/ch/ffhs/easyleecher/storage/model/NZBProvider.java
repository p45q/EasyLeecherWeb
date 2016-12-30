package ch.ffhs.easyleecher.storage.model;

/**
 * Dies ist das Model für die NZB Provider
 * 
 * @author pascal bieri
 */
public class NZBProvider {
	private String providerName;
	private String providerURL;
	private String providerAPIKey = "";

	public NZBProvider() {
		providerAPIKey = "";
		providerName = "";
		providerURL = "";
	}

	/**
	 * @return providerName
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * @param providerName
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * @return providerURL
	 */
	public String getProviderURL() {
		return providerURL;
	}

	/**
	 * @param providerURL
	 */
	public void setProviderURL(String providerURL) {
		this.providerURL = providerURL;
	}

	/**
	 * @return providerAPIKey
	 */
	public String getProviderAPIKey() {
		return providerAPIKey;
	}

	/**
	 * @param providerAPIKey
	 */
	public void setProviderAPIKey(String providerAPIKey) {
		this.providerAPIKey = providerAPIKey;
	}

}
