package ch.ffhs.easyleecher.tvdb.model;

import ch.ffhs.easyleecher.tvdb.tools.DOMHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Dies ist ein Model der TVDBAPI
 * 
 * @author thierry baumann, pascal bieri
 */
public class Mirrors {

	public static final String TYPE_XML = "XML";
	public static final String TYPE_BANNER = "BANNER";
	public static final String TYPE_ZIP = "ZIP";

	private static final int MASK_XML = 1;
	private static final int MASK_BANNER = 2;
	private static final int MASK_ZIP = 4;

	private static final Random RNDM = new Random();

	private final List<String> xmlList = new ArrayList<String>();
	private final List<String> bannerList = new ArrayList<String>();
	private final List<String> zipList = new ArrayList<String>();

	public Mirrors(String apiKey) {
		// Make this synchronized so that only one
		synchronized (this) {
			String urlString = "http://www.thetvdb.com/api/" + apiKey
					+ "/mirrors.xml";
			Document doc;

			doc = DOMHelper.getEventDocFromUrl(urlString);
			int typeMask;
			String url;

			NodeList nlMirror = doc.getElementsByTagName("Mirror");
			for (int nodeLoop = 0; nodeLoop < nlMirror.getLength(); nodeLoop++) {
				Node nMirror = nlMirror.item(nodeLoop);

				if (nMirror.getNodeType() == Node.ELEMENT_NODE) {
					Element eMirror = (Element) nMirror;
					url = DOMHelper.getValueFromElement(eMirror, "mirrorpath");
					typeMask = Integer.parseInt(DOMHelper.getValueFromElement(
							eMirror, "typemask"));
					addMirror(typeMask, url);
				}
			}
		}
	}

	public String getMirror(String type) {
		String url = null;
		if (type.equals(TYPE_XML) && !xmlList.isEmpty()) {
			url = xmlList.get(RNDM.nextInt(xmlList.size()));
		} else if (type.equals(TYPE_BANNER) && !bannerList.isEmpty()) {
			url = bannerList.get(RNDM.nextInt(bannerList.size()));
		} else if (type.equals(TYPE_ZIP) && !zipList.isEmpty()) {
			url = zipList.get(RNDM.nextInt(zipList.size()));
		}
		return url;
	}

	private void addMirror(int typeMask, String url) {
		switch (typeMask) {
		case MASK_XML:
			xmlList.add(url);
			break;
		case MASK_BANNER:
			bannerList.add(url);
			break;
		case (MASK_XML + MASK_BANNER):
			xmlList.add(url);
			bannerList.add(url);
			break;
		case MASK_ZIP:
			zipList.add(url);
			break;
		case (MASK_XML + MASK_ZIP):
			xmlList.add(url);
			zipList.add(url);
			break;
		case (MASK_BANNER + MASK_ZIP):
			bannerList.add(url);
			zipList.add(url);
			break;
		case (MASK_XML + MASK_BANNER + MASK_ZIP):
			xmlList.add(url);
			bannerList.add(url);
			zipList.add(url);
			break;
		default:
			break;
		}
	}
}
