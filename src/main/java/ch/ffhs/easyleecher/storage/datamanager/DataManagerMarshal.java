package ch.ffhs.easyleecher.storage.datamanager;

import java.io.File;

import javax.swing.SwingWorker;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Diese Klasse wird verwendet um Daten in das XML zu schreiben
 * 
 * @author pascal bieri
 */
@SuppressWarnings("rawtypes")
public class DataManagerMarshal extends SwingWorker {
	private final String outputXml;
	private final EasyLeecherData easyLeecherData;

	/**
	 * @param outputXml
	 * @param easyLeecherData
	 */
	public DataManagerMarshal(String outputXml, EasyLeecherData easyLeecherData) {
		this.easyLeecherData = easyLeecherData;
		this.outputXml = outputXml;
	}

	/**
	 * @param settings
	 */
	public void marshalEasyLeecherData(EasyLeecherData settings) {
		try {
			// JAXB context erstellen und initialisierung des marshaller
			JAXBContext jaxbContext = JAXBContext
					.newInstance(EasyLeecherData.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// für eine besser formatierte ausgabe
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			// als xml in outputXml speichern
			jaxbMarshaller.marshal(settings, new File(outputXml));

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	protected Object doInBackground() throws Exception {

		marshalEasyLeecherData(easyLeecherData);

		return null;
	}
}
