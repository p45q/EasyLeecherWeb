package ch.ffhs.easyleecher.tvdb.model;

/**
 * Dies ist ein Model der TVDBAPI
 * 
 * @author thierry baumann, pascal bieri
 */
public enum BannerListType {

	SERIES, SEASON, POSTER, FANART;

	public static BannerListType fromString(String type) {
		if (type != null) {
			try {
				return BannerListType.valueOf(type.trim().toUpperCase());
			} catch (IllegalArgumentException ex) {
				throw new IllegalArgumentException("BannerListType " + type
						+ " does not exist", ex);
			}
		}
		throw new IllegalArgumentException("BannerListType is null");
	}

}
