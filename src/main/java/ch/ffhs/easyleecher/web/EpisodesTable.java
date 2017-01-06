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
package ch.ffhs.easyleecher.web;

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
public class EpisodesTable implements Serializable {
	private static final long serialVersionUID = 1L;
	private StorageService storageService;
	private TreeNode selectedNode;
	private Season selectedSeason;;
	private Serie selectedSerie;
	private ArrayList<Episode> vcp;



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
	public List<Episode> getVisibleEpisodes() {
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



	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		TreeNode treeNode = event.getTreeNode();
		TreeNode parent = treeNode.getParent();

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


	}



}
