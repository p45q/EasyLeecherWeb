package de.beyondjava.jsf.sample.carshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import ch.ffhs.easyleecher.storage.StorageService;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import java.util.logging.Logger;
@ManagedBean(name = "treeBasicView")
@ViewScoped
public class BasicView implements Serializable {

	private TreeNode root;

	@ManagedProperty("#{dynamicOptionBean}")
	private DynamicOptionBean dynamicOptions;

	@ManagedProperty("#{staticOptionBean}")
	private StaticOptionBean staticOptions;

	public StaticOptionBean getStaticOptions() {
		return staticOptions;
	}

	public void setStaticOptions(StaticOptionBean staticOptions) {
		this.staticOptions = staticOptions;
	}

	public DynamicOptionBean getDynamicOptions() {
		return dynamicOptions;
	}

	public void setDynamicOptions(DynamicOptionBean dynamicOptions) {
		this.dynamicOptions = dynamicOptions;
	}
	private StorageService storageService;
	@PostConstruct
	public void init() {
		root = new DefaultTreeNode("Root", null);
		TreeNode treeSeries = new DefaultTreeNode("Serien", root);

		storageService = StorageService.getInstance();
		Logger log = Logger.getLogger(this.getClass().getName());

		ArrayList<Serie> series = storageService.getSeries();

		for(Serie serie : series)
		{
			TreeNode b = new DefaultTreeNode(serie.getSerieName(), treeSeries);

			ArrayList<Season> seasons = storageService.getSerieSeasons(serie);

			for(Season season : seasons)
			{
				TreeNode t = new DefaultTreeNode(season.getSeasonName(), b);
			}
		}
	}

	public TreeNode getRoot() {
		return root;
	}
}