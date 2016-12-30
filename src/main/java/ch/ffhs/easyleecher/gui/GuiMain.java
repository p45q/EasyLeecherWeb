package ch.ffhs.easyleecher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import ch.ffhs.easyleecher.gui.image.ImagePane;
import ch.ffhs.easyleecher.gui.table.EpisodeTable;
import ch.ffhs.easyleecher.gui.table.SeasonTable;
import ch.ffhs.easyleecher.gui.table.model.SeasonModel;
import ch.ffhs.easyleecher.gui.tree.TreeMain;
import ch.ffhs.easyleecher.main.Logging;
import ch.ffhs.easyleecher.main.Main;
import ch.ffhs.easyleecher.storage.StorageService;
import ch.ffhs.easyleecher.storage.model.Episode;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;

/**
 * Dies ist das Haupt EasyLeecher Frame
 * 
 * @author thierry baumann, pascal bieri
 */
@SuppressWarnings("serial")
public class GuiMain extends JFrame {
	private static GuiMain instance = new GuiMain();
	private JButton triggerButton;
	private JButton addSerieButton;
	private JTree tree;
	private JTextArea descriptionTextArea;
	private ImagePane imagePane;
	private JPanel mainWrapper;
	private JPanel rightCol;
	private JPanel leftCol;
	private JLabel titleLabel;
	private JPanel actionPanel;
	private Border blackborder;
	private JLabel progressLabel;
	private JPanel descriptonPanel;
	private JPanel infoPanel;
	private JPanel descriptionComponentPanel;
	private StorageService storageService;
	private TreeMain treeMain;
	private SelectionListener selectionListener;
	private JScrollPane treescroll;
	private JMenuItem exitItem;
	private JMenuItem searchEpisodesItem;
	private JMenuItem searchForDownloads;
	private JMenuItem searchNZBsItem;
	private JMenuItem settingsItem;
	private JMenuItem editProvidersItem;
	private JMenuItem removeSerie;
	private JMenuItem aboutItem;
	private JMenuItem disclaimerItem;
	private EpisodeTable episodeTableModel;
	private JPanel seasonTableWrapper;
	private JPanel episodeTableWrapper;
	private SeasonTable seasonTableModel;

	public GuiMain() {
		super("easyleacher");

		storageService = StorageService.getInstance();
		setContentPane(createContentPane());
		buildMenu();
		addListeners();
		checkBannerFolder();
	}

	private JPanel createContentPane() {
		mainWrapper = new JPanel(new BorderLayout());
		rightCol = new JPanel(new BorderLayout(15, 0));
		leftCol = new JPanel(new BorderLayout());
		descriptonPanel = new JPanel(new BorderLayout());
		descriptionComponentPanel = new JPanel(new BorderLayout());
		infoPanel = new JPanel(new BorderLayout());
		descriptionTextArea = new JTextArea();
		titleLabel = new JLabel();

		// Add the Tree
		loadTree();

		// actionpanel erstellen
		actionPanel = new JPanel(new FlowLayout(0));
		addSerieButton = new JButton("Add Serie");
		triggerButton = new JButton("Start all robots");
		actionPanel.add(addSerieButton);
		actionPanel.add(triggerButton);
		mainWrapper.add(actionPanel, BorderLayout.PAGE_START);

		// Statusbar erstellen
		progressLabel = new JLabel();
		progressLabel.setText("All functions working perfectly");
		rightCol.add(progressLabel, BorderLayout.PAGE_END);

		// Descriptionpanel
		imagePane = new ImagePane();
		imagePane.loadimage("banners/notfound.jpg");
		// mainimagePanel.add(imagePane);
		descriptonPanel.add(imagePane, BorderLayout.NORTH);
		titleLabel.setText("Welcome");
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);

		descriptionComponentPanel.add(titleLabel, BorderLayout.NORTH);
		descriptionComponentPanel.add(descriptionTextArea, BorderLayout.CENTER);
		descriptonPanel.add(descriptionComponentPanel, BorderLayout.CENTER);
		rightCol.add(descriptonPanel, BorderLayout.NORTH);

		//rightCol.add(infoPanel, BorderLayout.CENTER);

		// season tabelle erstellen
		// tablemodel für seasons
		seasonTableModel = new SeasonTable();

		// JTable mit model initialisieren
		JTable seasonTable = new JTable(seasonTableModel);

		seasonTableWrapper = new JPanel(new BorderLayout());
		JScrollPane seasonScroll = new JScrollPane(seasonTable);

		seasonTableWrapper.add(seasonTable.getTableHeader(),
				BorderLayout.NORTH);
		seasonTableWrapper.add(seasonScroll, BorderLayout.CENTER);

		// episode tabelle erstellen
		// tablemodel für episoden
		episodeTableModel = new EpisodeTable();

		// JTable mit model initialisieren
		JTable episodeTable = new JTable(episodeTableModel);

		episodeTableWrapper = new JPanel(new BorderLayout());
		JScrollPane episodeScroll = new JScrollPane(episodeTable);

		episodeTableWrapper.add(episodeTable.getTableHeader(),
				BorderLayout.NORTH);
		episodeTableWrapper.add(episodeScroll, BorderLayout.CENTER);

		blackborder = BorderFactory.createLineBorder(Color.black);
		rightCol.setBorder(blackborder);
		leftCol.setBorder(blackborder);
		infoPanel.setBorder(blackborder);

		// alles zusammenführen
		leftCol.add(rightCol, BorderLayout.CENTER);
		mainWrapper.add(leftCol, BorderLayout.CENTER);
		return mainWrapper;

	}

	private void addListeners() {
		addSerieButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (storageService.getGlobalsettings().getTvdbAPIKey().length() > 0) {
					if (storageService.getGlobalsettings()
							.getSeriesRootFolder().length() > 0
							&& storageService.getGlobalsettings()
									.getDownloadFolder().length() > 0) {
						if (storageService.getNZBProviders().size() > 0) {
							JFrame f = new AddSerieFrame();
							f.pack();
							f.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(new JFrame(),
									"NZB Provider needed", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Series root folder and download folder missing",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"TVDB API needed", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		triggerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.startTVDBChecker();
			}
		});
		removeSerie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (tree.getSelectionCount() > 0) {
					if (tree.getSelectionPath().getPathComponent(1) instanceof Serie) {
						Serie serie = (Serie) tree.getSelectionPath()
								.getPathComponent(1);

						storageService.removeSerie(serie);

						loadTree();
						setStatus("Serie " + serie.getSerieName()
								+ " successfully removed");
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"Select serie to delete", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// MENUbar Listeners
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.stopAllRobots();
				dispose();

			}
		});
		searchEpisodesItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.startTVDBChecker();

			}
		});
		settingsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new SettingsFrame();
				f.pack();
				f.setResizable(false);
				f.setVisible(true);
			}
		});
		editProvidersItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new EditNZBProvidersFrame();
				f.pack();
				f.setVisible(true);

			}
		});
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"This application was created for an FFHS Project\nCreated by: Pascal and Thierry",
								"About", JOptionPane.PLAIN_MESSAGE);
			}
		});
		disclaimerItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String disclaimer = "DISCLAIMER";
				JOptionPane.showMessageDialog(new JFrame(), disclaimer,
						"Disclaimer", JOptionPane.PLAIN_MESSAGE);

			}
		});
	}

	private void setContent(Object object) {

		if (seasonTableWrapper != null)
			rightCol.remove(seasonTableWrapper);
		if (episodeTableWrapper != null)
			rightCol.remove(episodeTableWrapper);

		if (object.getClass() == Serie.class) {
			Serie selectedSerie = (Serie) object;
			loadSerieDetails(selectedSerie);
		} else if (object.getClass() == Season.class) {
			Season selectedSeason = (Season) object;
			loadSeasonDetails(selectedSeason);
		} else if (object.getClass() == Episode.class) {
			Episode selectedEpisode = (Episode) object;
			loadEpisodeDetails(selectedEpisode);
		}
	}

	private void loadSeasonDetails(Season season) {
		Serie serie = storageService.getSeasonSerie(season);
		String bannerpath = "banners/" + serie.getSerieID() + ".jpg";
		titleLabel.setText(serie.getSerieName() + " Season "
				+ season.getSeasonName());
		// descriptionTextArea.setText(serie.getSerieDescription());
		setDescription(season);
		rightCol.add(episodeTableWrapper, BorderLayout.CENTER);
		episodeTableModel.addEpisodes(storageService.getSeasonEpisodes(season));
		rightCol.repaint();

		if (serie.getBanner() != null && serie.getBanner().length() != 0) {
			if (new File(bannerpath).exists()) {
				switchBanner(bannerpath);
			} else {
				try {
					// TODO SWINGWORKER
					URL website = new URL(serie.getBanner());
					ReadableByteChannel rbc = Channels.newChannel(website
							.openStream());
					FileOutputStream fos = new FileOutputStream(bannerpath);
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					switchBanner(bannerpath);
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			switchBanner("banners/notfound.jpg");
		}

	}

	private void loadEpisodeDetails(Episode episode) {
		Season season = storageService.getEpisodeSeason(episode);
		Serie serie = storageService.getEpisodeSerie(episode);
		String bannerpath = "banners/" + serie.getSerieID() + ".jpg";
		titleLabel.setText(serie.getSerieName() + " Season "
				+ season.getSeasonName() + " Episode "
				+ episode.getEpisodeEpisode());
		// descriptionTextArea.setText(season.getSeasonName() + "x"+
		// episode.getEpisodeEpisode()+ " - "+ episode.getEpisodeName() +"\n"+
		// serie.getSerieDescription());
		setDescription(episode);
		if (serie.getBanner() != null && serie.getBanner().length() != 0) {
			if (new File(bannerpath).exists()) {
				switchBanner(bannerpath);
			} else {
				try {
					// TODO SWINGWORKER
					URL website = new URL(serie.getBanner());
					ReadableByteChannel rbc = Channels.newChannel(website
							.openStream());
					FileOutputStream fos = new FileOutputStream(bannerpath);
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					switchBanner(bannerpath);
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			switchBanner("banners/notfound.jpg");
		}
	}

	private void loadSerieDetails(Serie serie) {
		String bannerpath = "banners/" + serie.getSerieID() + ".jpg";
		titleLabel.setText(serie.getSerieName());
		// descriptionTextArea.setText(serie.getSerieDescription());
		setDescription(serie);

		rightCol.add(seasonTableWrapper, BorderLayout.CENTER);

		seasonTableModel.clearTable();
		ArrayList<Season> serieSeasons = storageService.getSerieSeasons(serie);

		for (Season season : serieSeasons) {
			ArrayList<Episode> seasonEpisodes = storageService
					.getSeasonEpisodes(season);
			int wanted = 0;
			int snatched = 0;
			int downloaded = 0;
			int notfound = 0;

			for (Episode episode : seasonEpisodes) {
				switch (episode.getEpisodeStatus()) {
				case 0:
					wanted++;
					break;
				case 1:
					snatched++;
					break;
				case 2:
					downloaded++;
					break;
				case 5:
					notfound++;
					break;
				}
			}
			SeasonModel seasonModel = new SeasonModel();
			seasonModel.setSeasonName(season.getSeasonName());
			seasonModel.setTotalEpisodes(seasonEpisodes.size());
			seasonModel.setWanted(wanted);
			seasonModel.setSnatched(snatched);
			seasonModel.setDownloaded(downloaded);
			seasonModel.setNotfound(notfound);

			seasonTableModel.addSeason(seasonModel);
		}

		rightCol.repaint();

		if (serie.getBanner() != null && serie.getBanner().length() != 0) {
			if (new File(bannerpath).exists()) {
				switchBanner(bannerpath);
			} else {
				try {
					// TODO SWINGWORKER
					URL website = new URL(serie.getBanner());
					ReadableByteChannel rbc = Channels.newChannel(website
							.openStream());
					FileOutputStream fos = new FileOutputStream(bannerpath);
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					switchBanner(bannerpath);
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			switchBanner("banners/notfound.jpg");
		}
		reloadGUI();
	}

	/**
	 * @param object
	 */
	public void setDescription(Object object) {
		StringBuilder descriptionBuilder;
		descriptionBuilder = new StringBuilder();
		if (object.getClass() == Serie.class) {
			Serie serie = (Serie) object;
			descriptionBuilder.append("Status: " + serie.getSerieStatus());
			descriptionBuilder.append("\nAdded on: "
					+ serie.getSerieDateAdded());
			descriptionBuilder.append("\nIs aired on: "
					+ serie.getAirsDayOfWeek());
			descriptionBuilder.append("\nDescripton:\n");
			descriptionBuilder.append(serie.getSerieDescription());

		} else if (object.getClass() == Season.class) {
			Season selectedSeason = (Season) object;
			Serie serie = storageService.getSeasonSerie(selectedSeason);
			descriptionBuilder.append("Season Number: "
					+ selectedSeason.getSeasonName() + "\n");
			descriptionBuilder
					.append("Serie Status: " + serie.getSerieStatus());
			descriptionBuilder.append("\nAdded on: "
					+ serie.getSerieDateAdded());
			descriptionBuilder.append("\nIs aired on: "
					+ serie.getAirsDayOfWeek());
		} else if (object.getClass() == Episode.class) {
			Episode selectedEpisode = (Episode) object;
			Serie serie = storageService.getEpisodeSerie(selectedEpisode);
			descriptionBuilder.append("Episode Name:"
					+ selectedEpisode.getEpisodeName() + "\n");
			descriptionBuilder.append("Episode Number:"
					+ selectedEpisode.getEpisodeEpisode() + "\n");

			switch (selectedEpisode.getEpisodeStatus()) {
			case 0:
				descriptionBuilder.append("Episode Status: Wanted");
				break;
			case 1:
				descriptionBuilder.append("Episode Status: Snatched");
				break;
			case 2:
				descriptionBuilder.append("Episode Status: Downloaded");
				;
				break;
			case 5:
				descriptionBuilder.append("Episode Status: Not Found");
				break;
			}
			descriptionBuilder.append("\nSerie Status: "
					+ serie.getSerieStatus());
			descriptionBuilder.append("\nAdded on: "
					+ serie.getSerieDateAdded());
			descriptionBuilder.append("\nIs aired on: "
					+ serie.getAirsDayOfWeek());
		}
		descriptionTextArea.setText(descriptionBuilder.toString());
	}

	/**
	 * @param bannerpath
	 */
	public void switchBanner(String bannerpath) {

		Logging.logMessage("Switching banner to " + bannerpath);
		descriptonPanel.remove(imagePane);
		imagePane.loadimage(bannerpath);
		descriptonPanel.add(imagePane, BorderLayout.NORTH);
		// Force to reload image
		imagePane.repaint();
	}

	private void checkBannerFolder() {
		if (!new File("banners").exists()) {
			new File("banners").mkdirs();
		}
	}

	private void buildMenu() {
		// Creates a menubar for a JFrame
		JMenuBar menuBar = new JMenuBar();

		// Add the menubar to the frame
		setJMenuBar(menuBar);

		// file menu
		JMenu fileMenu = new JMenu("File");
		exitItem = new JMenuItem("Exit");
		searchEpisodesItem = new JMenuItem("Search for newly released episodes");
		searchForDownloads = new JMenuItem("Look for completed Downloads");
		searchNZBsItem = new JMenuItem("Search for available downloads");
		fileMenu.add(searchEpisodesItem);
		fileMenu.add(searchForDownloads);
		fileMenu.add(searchNZBsItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		// edit menu
		JMenu editMenu = new JMenu("Edit");
		settingsItem = new JMenuItem("Edit settings");
		editProvidersItem = new JMenuItem("Edit NZB Providers");
		removeSerie = new JMenuItem("Remove Serie");

		editMenu.add(settingsItem);
		editMenu.add(editProvidersItem);
		editMenu.addSeparator();
		editMenu.add(removeSerie);

		menuBar.add(editMenu);

		// edit menu
		JMenu helpMenu = new JMenu("Help");
		aboutItem = new JMenuItem("About");
		disclaimerItem = new JMenuItem("Disclaimer");
		helpMenu.add(aboutItem);
		helpMenu.add(disclaimerItem);
		menuBar.add(helpMenu);

	}

	public void loadTree() {
		if (tree == null) {
			treeMain = new TreeMain();
			tree = treeMain.getTree();
			selectionListener = new SelectionListener();
			tree.addTreeSelectionListener(selectionListener);
			treescroll = new JScrollPane(tree);
			treescroll.setPreferredSize(new Dimension(300, 200));
			leftCol.add(treescroll, BorderLayout.WEST);
		} else {
			leftCol.remove(treescroll);
			treeMain = null;
			treeMain = new TreeMain();
			tree = null;
			tree = treeMain.getTree();
			selectionListener = null;
			selectionListener = new SelectionListener();
			tree.addTreeSelectionListener(selectionListener);
			treescroll = null;
			treescroll = new JScrollPane(tree);
			treescroll.setPreferredSize(new Dimension(300, 200));
			leftCol.add(treescroll, BorderLayout.WEST);
			leftCol.revalidate();
			leftCol.repaint();
		}
	}

	public void reloadGUI() {
		rightCol.revalidate();
		rightCol.repaint();
		leftCol.revalidate();
		leftCol.repaint();
		mainWrapper.revalidate();
		mainWrapper.repaint();
	}

	class SelectionListener implements TreeSelectionListener {
		@Override
		public void valueChanged(TreeSelectionEvent se) {
			JTree tree = (JTree) se.getSource();
			setContent(tree.getLastSelectedPathComponent());

		}
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		progressLabel.setText(status);
	}

	/**
	 * @return instance
	 */
	public static GuiMain getInstance() {
		if (instance == null) {
			instance = new GuiMain();
		}
		return instance;
	}

}
