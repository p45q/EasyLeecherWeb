package ch.ffhs.easyleecher.tvdb.tools;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.WebServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.yamj.api.common.http.CommonHttpClient;

/**
 * Dies ist eine Hilfsklasse für die TVDB Api
 * 
 * @author thierry baumann, pascal bieri
 */
public class DOMHelper {

	private static final Logger LOG = LoggerFactory.getLogger(DOMHelper.class);
	private static final String YES = "yes";
	private static final String ENCODING = "UTF-8";
	private static final int RETRY_COUNT = 5;
	// Milliseconds to retry
	private static final int RETRY_TIME = 250;
	private static CommonHttpClient httpClient = null;
	// Constants
	private static final String ERROR_WRITING = "Error writing the document to ";

	// Hide the constructor
	protected DOMHelper() {
		// prevents calls from subclass
		throw new UnsupportedOperationException();
	}

	public static void setHttpClient(CommonHttpClient newHttpClient) {
		httpClient = newHttpClient;
	}

	public static String getValueFromElement(Element element, String tagName) {
		NodeList elementNodeList = element.getElementsByTagName(tagName);
		if (elementNodeList == null) {
			return "";
		} else {
			Element tagElement = (Element) elementNodeList.item(0);
			if (tagElement == null) {
				return "";
			}

			NodeList tagNodeList = tagElement.getChildNodes();
			if (tagNodeList == null || tagNodeList.getLength() == 0) {
				return "";
			}
			return ((Node) tagNodeList.item(0)).getNodeValue();
		}
	}

	public static synchronized Document getEventDocFromUrl(String url) {
		InputStream in = null;
		Document doc = null;

		try {
			String webPage = getValidWebpage(url);

			if (StringUtils.isNotBlank(webPage)) {
				in = new ByteArrayInputStream(webPage.getBytes(ENCODING));

				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();

				doc = db.parse(in);
				in.close();
				doc.getDocumentElement().normalize();
			}
		} catch (UnsupportedEncodingException ex) {
			throw new WebServiceException("Unable to encode URL: " + url, ex);
		} catch (ParserConfigurationException error) {
			throw new WebServiceException(
					"Unable to parse TheTVDb response, please try again later.",
					error);
		} catch (SAXException error) {
			throw new WebServiceException(
					"Unable to parse TheTVDb response, please try again later.",
					error);
		} catch (IOException error) {
			throw new WebServiceException(
					"Unable to parse TheTVDb response, please try again later.",
					error);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				// Input Stream was already closed or null
				LOG.trace("Failed to close InputStream", ex);
			}
		}

		return doc;
	}

	private static String getValidWebpage(String url) {
		// Count the number of times we download the web page
		int retryCount = 0;
		// Is the web page valid
		boolean valid = false;
		String webPage;

		try {
			while (!valid && (retryCount < RETRY_COUNT)) {
				retryCount++;
				webPage = requestWebPage(url);
				if (StringUtils.isNotBlank(webPage)) {
					// See if the ID is null
					if (!webPage.contains("<id>")
							|| webPage.contains("<id></id>")) {
						// Wait an increasing amount of time the more retries
						// that happen
						waiting(retryCount * RETRY_TIME);
						continue;
					} else {
						valid = true;
					}
				}

				// Couldn't get a valid webPage so, quit.
				if (!valid) {
					throw new WebServiceException(
							"Failed to download data from " + url);
				}

				return webPage;
			}
		} catch (UnsupportedEncodingException ex) {
			throw new WebServiceException("Unable to encode URL: " + url, ex);
		} catch (IOException ex) {
			throw new WebServiceException("Unable to download URL: " + url, ex);
		}

		return null;
	}

	public static String convertDocToString(Document doc)
			throws TransformerException {
		// set up a transformer
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
		trans.setOutputProperty(OutputKeys.INDENT, YES);

		// create string from xml tree
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(doc);
		trans.transform(source, result);
		return sw.toString();
	}

	public static boolean writeDocumentToFile(Document doc, String localFile) {
		try {
			TransformerFactory transfact = TransformerFactory.newInstance();
			Transformer trans = transfact.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, YES);
			trans.setOutputProperty(OutputKeys.INDENT, YES);
			trans.transform(new DOMSource(doc), new StreamResult(new File(
					localFile)));
			return true;
		} catch (TransformerConfigurationException ex) {
			LOG.warn(ERROR_WRITING + localFile, ex);
			return false;
		} catch (TransformerException ex) {
			LOG.warn(ERROR_WRITING + localFile, ex);
			return false;
		}
	}

	public static void appendChild(Document doc, Element parentElement,
			String elementName, String elementValue) {
		Element child = doc.createElement(elementName);
		Text text = doc.createTextNode(elementValue);
		child.appendChild(text);
		parentElement.appendChild(child);
	}


	private static void waiting(int milliseconds) {
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while ((t1 - t0) < milliseconds);
	}

	private static String requestWebPage(String url) throws IOException {
		return httpClient.requestContent(url, Charset.forName(ENCODING));
	}
}
