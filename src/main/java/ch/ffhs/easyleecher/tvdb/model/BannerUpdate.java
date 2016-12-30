package ch.ffhs.easyleecher.tvdb.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Dies ist ein Model der TVDBAPI
 * 
 * @author thierry baumann, pascal bieri
 */
public class BannerUpdate implements Serializable {

	// Default serial UID
	private static final long serialVersionUID = 1L;
	private String seasonNum;
	private String series;
	private String format;
	private String language;
	private String path;
	private String time;
	private String type;

	public String getSeasonNum() {
		return seasonNum;
	}

	public String getSeries() {
		return series;
	}

	public String getFormat() {
		return format;
	}

	public String getLanguage() {
		return language;
	}

	public String getPath() {
		return path;
	}

	public String getTime() {
		return time;
	}

	public String getType() {
		return type;
	}

	public void setSeasonNum(String seasonNum) {
		this.seasonNum = seasonNum;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}