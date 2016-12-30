package ch.ffhs.easyleecher.tvdb.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Dies ist ein Model der TVDBAPI
 * 
 * @author thierry baumann, pascal bieri
 */
public class EpisodeUpdate implements Serializable {

	// Default serial UID
	private static final long serialVersionUID = 1L;
	private String id;
	private String series;
	private String time;

	public String getId() {
		return id;
	}

	public String getSeries() {
		return series;
	}

	public String getTime() {
		return time;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}