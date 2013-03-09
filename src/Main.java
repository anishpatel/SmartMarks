import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class Main
{
	public static void main(String[] args)
	{
		// get path of Bookmarks JSON file 
		String bookmarksFilePath;
		if (args.length == 1) {
			bookmarksFilePath = args[0];
		} else if (args.length == 0) {
			Properties bookmarksLoc = new Properties();
			try {
				bookmarksLoc.load(new FileInputStream("data/in/bookmarks-location.properites"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
			bookmarksFilePath = bookmarksLoc.getProperty("path");
		} else {
			throw new IllegalArgumentException("Too many arguments passed.");
		}
		
		// get bookmarks metadata (e.g., url)
		List<Bookmark> bookmarks = ChromeBookmarksIO.read(bookmarksFilePath);
		
		// crawl bookmarks for webpage content (ie, HTML)
		Crawler crawler = new Crawler();
		for (Bookmark bookmark : bookmarks) {
			bookmark.rawPage = crawler.crawl(bookmark.url);
			bookmark.rawPage = bookmark.rawPage == null ? "" : bookmark.rawPage;
		}
		
		// remove HTML markup and other code
		for (Bookmark bookmark : bookmarks) {
			Map<String, String> htmlContentMap = HTMLPreProcessor.splitHTMLDocument(bookmark.rawPage);
			bookmark.title = htmlContentMap.get(HTMLConstants.HTML_TITLE);
			bookmark.rawBody = htmlContentMap.get(HTMLConstants.HTML_BODY);
			bookmark.bodyText = bookmark.rawBody;
			bookmark.bodyText = HTMLPreProcessor.removeScriptFromHTML(bookmark.bodyText);
			bookmark.bodyText = HTMLPreProcessor.replaceHTMLElementsWithText(bookmark.bodyText);
			bookmark.bodyText = HTMLPreProcessor.removeHyperlinksFromHTML(bookmark.bodyText, bookmark.url);
		}
		
		// create XML corpus of bookmarks
		XMLFileIO.write(bookmarks);
		
		// run TMSK/RIKTEXT
		
		// grab classifications and reconstruct Bookmarks file
//		ChromeBookmarksIO.write(bookmarksFilePath, bookmarks);
	}
}
