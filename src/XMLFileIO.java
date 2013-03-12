import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLFileIO
{
	public static final String XML_ROOT = "BookmarkClassifier";
	public static final String XML_TAG_BOOKMARK = "Bookmark";
	public static final String XML_ATTR_ID = "Id";
	public static final String XML_TAG_URL = "Url";
	public static final String XML_TAG_TITLE = "Title";
	public static final String XML_TAG_BODY = "Body";
	
	public static void write(List<Bookmark> bookmarks, String filePath)
	{
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
	
			// Root Element
			Element rootElement = doc.createElement(XML_ROOT);
			doc.appendChild(rootElement);
			
			// append each bookmark to XML file
			for (Bookmark bookmark : bookmarks) {
				// Bookmark Elements
				Element bmElement = doc.createElement(XML_TAG_BOOKMARK);
				rootElement.appendChild(bmElement);
	
				// set id attribute to Bookmark element
				bmElement.setAttribute(XML_ATTR_ID, ""+bookmark.id);
				
				// url element
				Element url = doc.createElement(XML_TAG_URL);
				url.appendChild(doc.createTextNode(bookmark.url));
				
				// title element
				Element title = doc.createElement(XML_TAG_TITLE);
				title.appendChild(doc.createTextNode(bookmark.title));
				bmElement.appendChild(title);
	
				// body element
				Element body = doc.createElement(XML_TAG_BODY);
				body.appendChild(doc.createTextNode(bookmark.bodyText));
				bmElement.appendChild(body);
			}
				
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
//			StreamResult result = new StreamResult(System.out); // Output to console for testing
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
}