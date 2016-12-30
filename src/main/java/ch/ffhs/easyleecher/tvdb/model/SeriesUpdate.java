package ch.ffhs.easyleecher.tvdb.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Dies ist ein Model der TVDBAPI
 * 
 * @author thierry baumann, pascal bieri
 */
public class SeriesUpdate implements Serializable {

	// Default serial UID
	private static final long serialVersionUID = 1L;
	private String id;
	private String time;

	public String getId() {
		return id;
	}

	public String getTime() {
		return time;
	}

	public void setId(String id) {
		this.id = id;
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
