import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.ImmutableList;


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
		
		// get bookmarks metadata from Chrome's Bookmarks JSON file
		String bookmarksFilePath = paths.getProperty("bookmarks");
		bookmarksFilePath = bookmarksFilePath.replace("%username%", System.getProperty("user.name"));
		ChromeBookmarksFile cbf = JsonFileIO.read(bookmarksFilePath, ChromeBookmarksFile.class);
		ChromeBookmarksEditor cbe = new ChromeBookmarksEditor(cbf);
		List<Bookmark> trainBookmarks = cbe.getTrainingBookmarks();
		List<Bookmark> testBookmarks = cbe.getTestBookmarks();
		 
		// crawl bookmarks for webpage content (ie, HTML)
		Crawler crawler = new Crawler();
		for (Bookmark bookmark : trainBookmarks) {
			bookmark.rawPage = crawler.crawl(bookmark.url);
			bookmark.rawPage = bookmark.rawPage == null ? "" : bookmark.rawPage;
		}
		
		// remove HTML markup and other code
		for (Bookmark bookmark : trainBookmarks) {
			bookmark.title = HTMLPreProcessor.getTitle(bookmark.rawPage);
			bookmark.body = HTMLPreProcessor.getBody(bookmark.rawPage);
			bookmark.body = bookmark.body.toLowerCase();
		}
		
		// create XML corpus of trainBookmarks
		XMLFileIO.write(trainBookmarks, corpusPath);
		
		// tokenize, stem, and stop
		TextMinerFuncs.tokenize(trainBookmarks);
		String stemwordsFilePath = paths.getProperty("stemwords");
		TextMinerFuncs.stem(trainBookmarks, stemwordsFilePath);
		String stopwordsFilePath = paths.getProperty("stopwords");
		TextMinerFuncs.stop(trainBookmarks, stopwordsFilePath);
		
		// create dictionary, create reverse lookup table, and compress each bookmark's body using lookup table
		List<String> dict = TextMinerFuncs.getDict(trainBookmarks);
		Map<String,Integer> tokenLookup = TextMinerFuncs.getTokenLookupTable(dict);
		for (Bookmark bookmark : trainBookmarks) {
			List<Integer> bodyCompressed = new ArrayList<Integer>(bookmark.tokens.size());
			for (String token : bookmark.tokens) {
				bodyCompressed.add(tokenLookup.get(token));
				if (tokenLookup.get(token) == null) System.out.println(tokenLookup.get(token));
			}
			bookmark.bodyCompressed = ImmutableList.copyOf(bodyCompressed);
		}
		
		// calculate tf*idf for each term for each bookmark
		Map<Integer,Integer> df = TextMinerFuncs.calcTfidfs(trainBookmarks);
		for (Bookmark bookmark : trainBookmarks) {
			System.out.println(bookmark.url);
			for (TokenValue tv : bookmark.sortedTfidf) {
//				if (tv.value.doubleValue > 0.0) {
					String token = dict.get(tv.tokenId);
					System.out.printf("%20s\t%5.02f\t%3d%n", token, tv.value, bookmark.tf.get(tv.tokenId));
//				}
			}
		}
		
/*		// run TMSK/RIKTEXT
		String tmPropsPath = paths.getProperty("tmsk_properties");
		TextMiner tm = new TextMiner(corpusPath, tmPropsPath);*/
		
		// reconstruct Bookmarks file with labeled test bookmarks
		cbe.updateTestBookmarks(testBookmarks);
	}
}
