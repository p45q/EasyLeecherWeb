package ch.ffhs.easyleecher.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.ffhs.easyleecher.storage.StorageService;
import ch.ffhs.easyleecher.storage.model.GlobalSettings;

/**
 * Dieses Frame ermöglicht das anpassen der Einstellungen
 * 
 * @author pascal bieri
 */
@SuppressWarnings("serial")
public class SettingsFrame extends JFrame {
	private JTextField seriesRootField;
	private JButton seriesRootButton;
	private JTextField nzbBlackholeField;
	private JButton nzbBlackholeButton;
	private JTextField downloadFolderField;
	private JButton downloadFolderButton;
	private JComboBox searchIntervalBox;
	private JTextField tvdbAPIKeyField;
	private JButton saveButton;
	private JButton nzbProvidersButton;
	private GlobalSettings globalsettings;
	StorageService storageService;

	public SettingsFrame() {
		super("EasyLeecher Settings");
		storageService = StorageService.getInstance();

		setContentPane(createContentPane());
		globalsettings = storageService.getGlobalsettings();
		loadSettings();
	}

	private JPanel createContentPane() {
		JPanel mainWrapper = new JPanel(new BorderLayout());
		JPanel formWrapper = new JPanel(new GridLayout(0, 2));
		seriesRootField = new JTextField(20);
		nzbBlackholeField = new JTextField(20);
		downloadFolderField = new JTextField(20);
		tvdbAPIKeyField = new JTextField(20);

		String[] intervalList = { "every 10 minutes", "every hour",
				"every 4 hours", "every 12 hours", "every day", "every week",
				"never" };
		searchIntervalBox = new JComboBox(intervalList);
		searchIntervalBox.setSelectedIndex(6);

		// GLobal settings
		formWrapper.add(new JLabel("Global Settings"));
		formWrapper.add(new JLabel(""));
		formWrapper.add(new JLabel("Series Root Location"));
		JPanel rootLocationWrapper = new JPanel(new FlowLayout());
		rootLocationWrapper.add(seriesRootField);
		seriesRootButton = new JButton("Choose");
		rootLocationWrapper.add(seriesRootButton);
		formWrapper.add(rootLocationWrapper);

		JPanel nzbLocationWrapper = new JPanel(new FlowLayout());
		formWrapper.add(new JLabel("NZB Blackhole Location"));
		nzbLocationWrapper.add(nzbBlackholeField);
		nzbBlackholeButton = new JButton("Choose");
		nzbLocationWrapper.add(nzbBlackholeButton);
		formWrapper.add(nzbLocationWrapper);

		JPanel downloadLocationWrapper = new JPanel(new FlowLayout());
		formWrapper.add(new JLabel("Sabnzbdplus Download Location"));
		downloadFolderButton = new JButton("Choose");
		downloadLocationWrapper.add(downloadFolderField);
		downloadLocationWrapper.add(downloadFolderButton);
		formWrapper.add(downloadLocationWrapper);

		formWrapper.add(new JLabel("Search interval"));
		formWrapper.add(searchIntervalBox);
		formWrapper.add(new JLabel("TVDB Api Key"));
		formWrapper.add(tvdbAPIKeyField);

		// actionpanel for buttons
		JPanel actionPanel = new JPanel(new FlowLayout(0));
		// button save
		saveButton = new JButton("Save Settings");
		actionPanel.add(saveButton);
		nzbProvidersButton = new JButton("Edit NZB PRoviders");
		actionPanel.add(nzbProvidersButton);
		formWrapper.add(actionPanel);

		mainWrapper.add(formWrapper, BorderLayout.WEST);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveSettings();
				JOptionPane.showMessageDialog(null, "Settings saved");
			}
		});
		nzbProvidersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame f = new EditNZBProvidersFrame();
				f.pack();
				f.setVisible(true);
			}
		});
		nzbBlackholeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String folderpath = chooseFolder();
				if (folderpath.length() > 0) {
					nzbBlackholeField.setText(folderpath);
				}
			}
		});
		seriesRootButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String folderpath = chooseFolder();
				if (folderpath.length() > 0) {
					seriesRootField.setText(folderpath);
				}
			}
		});
		downloadFolderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String folderpath = chooseFolder();
				if (folderpath.length() > 0) {
					downloadFolderField.setText(folderpath);
				}
			}
		});
		return mainWrapper;
	}

	private void loadSettings() {
		if (globalsettings != null) {
			seriesRootField.setText(globalsettings.getSeriesRootFolder());
			nzbBlackholeField.setText(globalsettings.getNzbBlackholeFolder());
			downloadFolderField.setText(globalsettings.getDownloadFolder());
			Integer searchIntervalint = globalsettings.getSearchInterval();
			searchIntervalBox.setSelectedIndex(searchIntervalint);
			tvdbAPIKeyField.setText(globalsettings.getTvdbAPIKey());
		}
	}

	private void saveSettings() {
		globalsettings.setSeriesRootFolder(seriesRootField.getText());
		globalsettings.setNzbBlackholeFolder(nzbBlackholeField.getText());
		globalsettings.setDownloadFolder(downloadFolderField.getText());
		globalsettings.setTvdbAPIKey(tvdbAPIKeyField.getText());
		System.out.println(searchIntervalBox.getSelectedIndex());
		globalsettings.setSearchInterval(searchIntervalBox.getSelectedIndex());
		storageService.setGlobalsettings(globalsettings);

	}

	private String chooseFolder() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("choosertitle");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): "
					+ chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : "
					+ chooser.getSelectedFile());
			return chooser.getSelectedFile().toString();
		} else {
			System.out.println("No Selection ");
			return "";
		}

	}

}
