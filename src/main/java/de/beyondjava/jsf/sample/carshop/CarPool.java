/**
 *  (C) 2013-2014 Stephan Rauh http://www.beyondjava.net
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.beyondjava.jsf.sample.carshop;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ch.ffhs.easyleecher.storage.StorageService;
import ch.ffhs.easyleecher.storage.model.Episode;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import static java.util.Comparator.comparing;
@ManagedBean
@SessionScoped
public class CarPool implements Serializable {
	private static final long serialVersionUID = 1L;

	private final static int SIZE_OF_INITIAL_CAR_POOL = 1000;
	private StorageService storageService;
	private TreeNode selectedNode;
	private String filterBrand = null;
	private String filterType = null;
	private Season selectedSeason;;
	private Serie selectedSerie;
	private ArrayList<Episode> vcp;
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

	@ManagedProperty("#{dynamicOptionBean}")
	private DynamicOptionBean dynamicOptions;

	private List<String> types;

	private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

	private List<Car> carPool;

	public List<Car> getCarPool() {
		return carPool;
	}
	public String getSerieImage(){
		if(selectedSerie==null)
		{
			return "resources/notfound.jpg";
		}
		return selectedSerie.getBanner();
	}
	public String getSerieDescription(){
		if(selectedSerie==null)
		{
			return "Welcome to EasyLeecher";
		}
		Logger log =
				Logger.getLogger(this.getClass().getName());
		log.warning("Description: "+selectedSerie.getSerieDescription());
		return selectedSerie.getSerieDescription();
	}
	public List<Episode> getVisibleCarPool() {
		storageService = StorageService.getInstance();
		Logger log =
				Logger.getLogger(this.getClass().getName());


		if(selectedSeason!=null) {
			vcp = storageService.getSeasonEpisodes(selectedSeason);

			Collections.sort(vcp, comparing(Episode::getEpisodeEpisode));

			log.warning("loading..." + selectedSeason.getSeasonName() + "with" + vcp.size());
			log.warning(("First episode is:"+vcp.get(0).getEpisodeEpisode()));

		}
		return vcp;

	}

	private List<Car> selectedCars;

	public List<Car> getSelectedCars() {
		return selectedCars;
	}

	/** This method is also used as actionListener */
	@PostConstruct
	public void initRandomCarPool() {
		types = dynamicOptions.getTypesToBrand(null);
		carPool = new ArrayList<Car>();
		for (int i = 0; i < SIZE_OF_INITIAL_CAR_POOL; i++) {
			carPool.add(getRandomCar());
		}
		selectedCars = carPool;

	}

	public void setCarPool(List<Car> carpool) {
		this.carPool = carpool;
	}

	private Car getRandomCar() {
		int typeIndex = (int) Math.floor(Math.random() * (types.size() - 1));
		String type = types.get(typeIndex + 1);
		String brand = dynamicOptions.getBrandToType(type);
		int year = (int) (Math.floor((currentYear - 1980) * Math.random())) + 1980;
		int age = currentYear - year;

		int price = 60000 / (1 + age) + (int) Math.floor(Math.random() * 10000);

		int mileage = (int) (Math.floor((age + 1) * 20000 * Math.random()));

		int colorIndex = (int) Math.floor(Math.random() * (staticOptions.getColors().size() - 1));
		String color = staticOptions.getColors().get(colorIndex + 1);

		int fuelIndex = (int) Math.floor(Math.random() * (staticOptions.getFuels().size() - 1));
		String fuel = staticOptions.getFuels().get(fuelIndex + 1);

		Car c = new Car(brand, type, year, color, mileage, fuel, price);
		return c;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		TreeNode treeNode = event.getTreeNode();
		TreeNode parent = treeNode.getParent();
		if (parent.getParent()==null) {
			setFilterBrand(null);
			setFilterType(null);
		}
		else if (parent.getParent().getParent() != null) {
			setFilterBrand(parent.getData().toString());
			setFilterType(treeNode.getData().toString());
		}
		else {
			setFilterBrand(treeNode.getData().toString());
			setFilterType(null);
		}
		Logger log =
				Logger.getLogger(this.getClass().getName());
		log.info(parent.getData().toString());
		log.info(treeNode.getData().toString());
		log.info(treeNode.getData().getClass().toString());
		if(parent.getData().toString().equals("Serien"))
		{
			log.info("isch serie");
		}else if(parent.getData().toString().equals("Root"))
		{
			log.info("root");
		}else{
			log.info("Loading Episodes:");
//			vcp = storageService.getSeasonEpisodes(
//					storageService.getSerieSeasonByName(
//							storageService.getSerieByName(parent.getData().toString()),treeNode.getData().toString()));

			selectedSerie = storageService.getSerieByName(parent.getData().toString());
			selectedSeason =
					storageService.getSerieSeasonByName(
							storageService.getSerieByName(parent.getData().toString()),treeNode.getData().toString());
			//log.info("vcp size:"+vcp.size());
		}



		for (Car c: carPool) {
			boolean visible = true;
			if (getFilterBrand() != null) {
				visible &= getFilterBrand().equals(c.getBrand());
			}
			if (getFilterType() != null) {
				visible &= getFilterType().equals(c.getType());
			}
		}

	}

	public String getFilterBrand() {
		return filterBrand;
	}

	public void setFilterBrand(String filterBrand) {
		this.filterBrand = filterBrand;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
}
