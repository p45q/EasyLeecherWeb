package ch.ffhs.easyleecher.web;

import ch.ffhs.easyleecher.download.SeriesManager;
import ch.ffhs.easyleecher.storage.StorageService;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;
import ch.ffhs.easyleecher.tvdb.TheTVDBApi;
import ch.ffhs.easyleecher.tvdb.model.TvDbEpisode;
import ch.ffhs.easyleecher.tvdb.model.TvDbSeries;
import org.primefaces.context.RequestContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by thierry on 30/12/2016.
 */
@ViewScoped
@ManagedBean
public class SeriesBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private TheTVDBApi tvDbApi;
    private SeriesManager seriesManager;

    private String txt1;


    public SeriesBean() {
        tvDbApi = new TheTVDBApi(StorageService.getInstance()
                .getGlobalsettings().getTvdbAPIKey());

        seriesManager = new SeriesManager();


    }

    public void save() {
        List<TvDbSeries> list = tvDbApi.searchSeries(txt1, "en");

        TvDbSeries serieSelected = list.get(0);


        TvDbSeries serieOri = tvDbApi.getSeries(serieSelected.getId(), "en");


        TvDbSeries tvDbSeries = tvDbApi.getSeries(serieSelected.getId(), "en");
        Serie serie = seriesManager.addSerie(tvDbSeries);

        List<TvDbEpisode> episodes = tvDbApi.getAllEpisodes(serieSelected.getId(), "en");


        Map<Integer, Season> seasonsTemp = new HashMap<Integer, Season>();
        int done = 0;
        for (TvDbEpisode tvDbEpisode : episodes) {
            done++;
            Integer seasonNumber = tvDbEpisode.getSeasonNumber();
            if (seasonNumber > 0) {
                Season season = seasonsTemp.get(seasonNumber);

                if (season == null) {
                    season = seriesManager.addSeason(seasonNumber.toString(),
                            serie);
                    seasonsTemp.put(seasonNumber, season);
                }

                seriesManager.addEpisode(tvDbEpisode, season);

            }
        }
    }

    public List<String> completeText(String query) {
        List<String> series = new ArrayList<String>();

        List<TvDbSeries> list = tvDbApi.searchSeries(query, "en");

        for (TvDbSeries serie : list) {
            series.add(serie.getSeriesName());
        }
        return series;
    }


    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }



}