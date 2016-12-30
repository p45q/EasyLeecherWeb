package ch.ffhs.easyleecher.storage.datamanager;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ch.ffhs.easyleecher.storage.model.Episode;
import ch.ffhs.easyleecher.storage.model.GlobalSettings;
import ch.ffhs.easyleecher.storage.model.NZBProvider;
import ch.ffhs.easyleecher.storage.model.Season;
import ch.ffhs.easyleecher.storage.model.Serie;

/**
 * Diese Klasse wird verwendet um Daten aus dem XML zu laden
 * 
 * @author thierry baumann, pascal bieri
 */
public class DataManagerUnmarshal {
	private final String inputXml;

	/**
	 * @param inputXml
	 */
	public DataManagerUnmarshal(String inputXml) {
		this.inputXml = inputXml;
	}

	/**
	 * @return series
	 */
	public ArrayList<Serie> getSeries() {
		EasyLeecherData settings = unmarshalData();
		return settings.getSeries();
	}

	/**
	 * @return seasons
	 */
	public ArrayList<Season> getSeasons() {
		EasyLeecherData settings = unmarshalData();
		return settings.getSeasons();
	}

	/**
	 * @return episodes
	 */
	public ArrayList<Episode> getEpisodes() {
		EasyLeecherData settings = unmarshalData();
		return settings.getEpisodes();
	}

	/**
	 * @return NZBProviders
	 */
	public ArrayList<NZBProvider> getNZBProviders() {
		EasyLeecherData settings = unmarshalData();
		return settings.getNzbProviders();
	}

	/**
	 * @return globalSettings
	 */
	public GlobalSettings getGlobalSettings() {
		EasyLeecherData settings = unmarshalData();
		return settings.getGlobalSettings();
	}

	/**
	 * @return xml
	 */
	private EasyLeecherData unmarshalData() {
		try {
			// JAXB context erstellen und initialisierung des marshaller
			JAXBContext jaxbContext = JAXBContext
					.newInstance(EasyLeecherData.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			// settings objekt aus xml erstellen und zurückgeben
			return (EasyLeecherData) unmarshaller.unmarshal(new FileReader(
					inputXml));

		} catch (JAXBException je) {
			je.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return null;
	}
}
