package ch.ffhs.easyleecher.tvdb.model;

import java.io.Serializable;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * Dies ist ein Model der TVDBAPI
 * 
 * @author thierry baumann, pascal bieri
 */
public class TVDBUpdates implements Serializable {

	// Default serial UID
	private static final long serialVersionUID = 1L;
	private String time;
	private List<SeriesUpdate> seriesUpdates;
	private List<EpisodeUpdate> episodeUpdates;
	private List<BannerUpdate> bannerUpdates;

	public String getTime() {
		return time;
	}

	public List<SeriesUpdate> getSeriesUpdates() {
		return seriesUpdates;
	}

	public List<EpisodeUpdate> getEpisodeUpdates() {
		return episodeUpdates;
	}

	public List<BannerUpdate> getBannerUpdates() {
		return bannerUpdates;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setSeriesUpdates(List<SeriesUpdate> seriesUpdates) {
		this.seriesUpdates = seriesUpdates;
	}

	public void setEpisodeUpdates(List<EpisodeUpdate> episodeUpdates) {
		this.episodeUpdates = episodeUpdates;
	}

	public void setBannerUpdates(List<BannerUpdate> bannerUpdates) {
		this.bannerUpdates = bannerUpdates;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
