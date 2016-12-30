package ch.ffhs.easyleecher.main;

/**
 * Diese Klasse wird verwendet um zu Testzwecken Debugmeldungen auzugeben
 * 
 * @author pascal bieri
 *
 */
public class Logging {
	static boolean debugMode = false;

	/**
	 * @param message
	 */
	public static void logMessage(String message) {
		if (debugMode)
			System.out.println("Debug:" + message);
	}
}