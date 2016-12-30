package ch.ffhs.easyleecher.tvdb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Dies ist ein Model der TVDBAPI
 * 
 * @author thierry baumann, pascal bieri
 */
public class Banners implements Serializable {

	// Default serial UID
	private static final long serialVersionUID = 1L;
	private int seriesId = 0;
	private List<Banner> seriesList = new ArrayList<Banner>();
	private List<Banner> seasonList = new ArrayList<Banner>();
	private List<Banner> posterList = new ArrayList<Banner>();
	private List<Banner> fanartList = new ArrayList<Banner>();

	public int getSeriesId() {
		return seriesId;
	}

	public List<Banner> getSeriesList() {
		return seriesList;
	}

	public List<Banner> getSeasonList() {
		return seasonList;
	}

	public List<Banner> getPosterList() {
		return posterList;
	}

	public List<Banner> getFanartList() {
		return fanartList;
	}

	public void setSeriesId(int seriesId) {
		this.seriesId = seriesId;
	}

	public void setSeriesList(List<Banner> seriesList) {
		this.seriesList = seriesList;
	}

	public void addSeriesBanner(Banner banner) {
		this.seriesList.add(banner);
	}

	public void setSeasonList(List<Banner> seasonList) {
		this.seasonList = seasonList;
	}

	public void addSeasonBanner(Banner banner) {
		this.seasonList.add(banner);
	}

	public void setPosterList(List<Banner> posterList) {
		this.posterList = posterList;
	}

	public void addPosterBanner(Banner banner) {
		this.posterList.add(banner);
	}

	public void setFanartList(List<Banner> fanartList) {
		this.fanartList = fanartList;
	}

	public void addFanartBanner(Banner banner) {
		this.fanartList.add(banner);
	}

	public void addBanner(Banner banner) {
		if (banner != null) {
			if (seriesId == 0) {
				seriesId = banner.getId();
			}

			if (banner.getBannerType() == BannerListType.SERIES) {
				addSeriesBanner(banner);
			} else if (banner.getBannerType() == BannerListType.SEASON) {
				addSeasonBanner(banner);
			} else if (banner.getBannerType() == BannerListType.POSTER) {
				addPosterBanner(banner);
			} else if (banner.getBannerType() == BannerListType.FANART) {
				addFanartBanner(banner);
			}
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
