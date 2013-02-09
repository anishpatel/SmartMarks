import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXMLFile {

	public static void WriteHTMLDocumentToXML(
			HashMap<Integer, HashMap<String, String>> bookmarkResultMap,
			ArrayList<Integer> bookmarkIdList) {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Root Element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(XMLConstants.XML_ROOT);
			doc.appendChild(rootElement);

			for (Integer id : bookmarkIdList) {

				HashMap<String, String> htmlContentMap = bookmarkResultMap
						.get(id);

				// Bookmark Elements
				Element bookmark = doc
						.createElement(XMLConstants.XML_TAG_BOOKMARK);
				rootElement.appendChild(bookmark);

				// set id attribute to Bookmark element
				bookmark.setAttribute(XMLConstants.XML_ATTR_ID,
						htmlContentMap.get(XMLConstants.XML_ATTR_ID));

				// title element
				Element title = doc.createElement(XMLConstants.XML_TAG_TITLE);
				title.appendChild(doc.createTextNode(htmlContentMap
						.get(HTMLConstants.HTML_TITLE)));
				bookmark.appendChild(title);

				// title element
				Element body = doc.createElement(XMLConstants.XML_TAG_BODY);
				body.appendChild(doc.createTextNode(htmlContentMap
						.get(HTMLConstants.HTML_BODY)));
				bookmark.appendChild(body);

			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(
					"ChromeBookmarks.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}