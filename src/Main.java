import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main
{
	public static void main(String[] args)
	{
		// get bookmarks from Chrome's Bookmarks JSON file
		ChromeBookmarksParser cbp = new ChromeBookmarksParser();
		String bookmarksFilePath = "Bookmarks";
		cbp.parse(bookmarksFilePath);
		List<String> urlList = cbp.getUrls();
		Map<Integer,String> urlMap = new HashMap<Integer,String>();
		List<Integer> bookmarkIdList = new ArrayList<Integer>(urlList.size());
		for (int i = 0; i < urlList.size(); ++i) {
			bookmarkIdList.add(i);
			urlMap.put(i, urlList.get(i));
		}
		
		// crawl bookmarks
		Map<Integer, Map<String, String>> bookmarkResultMap = Crawler.CrawlBookmarks(bookmarkIdList, urlMap);
		WriteXMLFile.WriteHTMLDocumentToXML(bookmarkResultMap, bookmarkIdList);
	}
}
