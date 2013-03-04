import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class Main
{
	public static void main(String[] args)
	{
		/* get path of Bookmarks JSON file */
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
		
		/* get urls from Bookmarks */
		ChromeBookmarksParser cbp = new ChromeBookmarksParser();
		cbp.parse(bookmarksFilePath);
		List<String> urlList = cbp.getUrls();
		
		
		Map<Integer,String> urlMap = new HashMap<Integer,String>();
		List<Integer> bookmarkIdList = new ArrayList<Integer>(urlList.size());
		for (int i = 0; i < urlList.size(); ++i) {
			bookmarkIdList.add(i);
			urlMap.put(i, urlList.get(i));
		}
		
		/* crawl bookmarks and write pages to XML files */
		Map<Integer, Map<String, String>> bookmarkResultMap = Crawler.CrawlBookmarks(bookmarkIdList, urlMap);
		WriteXMLFile.WriteHTMLDocumentToXML(bookmarkResultMap, bookmarkIdList);
	}
}
