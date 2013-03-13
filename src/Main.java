import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


public class Main
{
	public static void main(String[] args)
	{
		String corpusPath = "data/out/ChromeBookmarks.xml";
		
		// get input paths
		Properties paths = new Properties();
		try {
			paths.load(new FileInputStream("paths.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		// get bookmarks metadata (e.g., url)
		String bookmarksFilePath = paths.getProperty("bookmarks");
		bookmarksFilePath = bookmarksFilePath.replace("%username%", System.getProperty("user.name"));
		List<Bookmark> bookmarks = ChromeBookmarksIO.read(bookmarksFilePath);
		 
		// crawl bookmarks for webpage content (ie, HTML)
		Crawler crawler = new Crawler();
		for (Bookmark bookmark : bookmarks) {
			bookmark.rawPage = crawler.crawl(bookmark.url);
			bookmark.rawPage = bookmark.rawPage == null ? "" : bookmark.rawPage;
		}
		
		// remove HTML markup and other code
		for (Bookmark bookmark : bookmarks) {
			bookmark.title = HTMLPreProcessor.getTitle(bookmark.rawPage);
			bookmark.body = HTMLPreProcessor.getBody(bookmark.rawPage);
		}
		
		// create XML corpus of bookmarks
		XMLFileIO.write(bookmarks, corpusPath);
		
		// run TMSK/RIKTEXT
		String tmPropsPath = paths.getProperty("tmsk_properties");
		TextMiner tm = new TextMiner(corpusPath, tmPropsPath);
		
		// grab classifications and reconstruct Bookmarks file
//		ChromeBookmarksIO.write(bookmarksFilePath, bookmarks);
	}
}
