package ch.ffhs.easyleecher.main;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ch.ffhs.easyleecher.download.TVDBChecker;
import ch.ffhs.easyleecher.gui.GuiMain;
import ch.ffhs.easyleecher.storage.StorageService;

/**
 * Diese Klasse wird zu beginn geladen und startet die Worker/Gui
 * 
 * @author thierry baumann, pascal bieri
 * 
 */
public class Main {
	private static ScheduledExecutorService execReleaseChecker;
	private static TVDBChecker tvdbChecker;

	private static StorageService storageService;

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 */
	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		init();
		initRobots();

		buildFrame();
	}

	public static void init() {
		storageService = StorageService.getInstance();
	}

	/**
	 * Diese Funktion startet sämtliche Robots nach dem definierten Intervall
	 */
	public static void initRobots() {
		int searchInterval = storageService.getGlobalsettings()
				.getSearchInterval();

		switch (searchInterval) {
		case 0:
			searchInterval = 600;
			break;
		case 1:
			searchInterval = 3600;
			break;
		case 2:
			searchInterval = 14400;
			break;
		case 3:
			searchInterval = 43200;
			break;
		case 4:
			searchInterval = 86400;
			break;
		case 5:
			searchInterval = 604800;
			break;
		default:
			searchInterval = 0;
		}

		if (searchInterval > 0) {

			execReleaseChecker = Executors.newSingleThreadScheduledExecutor();
			tvdbChecker = new TVDBChecker();


			execReleaseChecker.scheduleAtFixedRate(tvdbChecker, 0,
					searchInterval, TimeUnit.SECONDS);

		}
	}



	/**
	 * Die Funktion startet den TVDBdchecker
	 */
	public static void startTVDBChecker() {
		execReleaseChecker.execute(tvdbChecker);
	}

	/**
	 * Die Funktion stoppt alle Robots
	 */
	public static void stopAllRobots() {
		Logging.logMessage("Stopping all robots");
		execReleaseChecker.shutdown();
	}

	/**
	 * Diese Funktion instanziiert das Gui Frame
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 */
	public static void buildFrame() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame f = GuiMain.getInstance();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // wichtig!
		f.setSize(1065, 800);
		f.setVisible(true);
	}
}
