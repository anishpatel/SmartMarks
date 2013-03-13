import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

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
			bookmark.body = bookmark.body.toLowerCase();
		}
		
		// create XML corpus of bookmarks
		XMLFileIO.write(bookmarks, corpusPath);
		
		// tokenize, stem, and stop
		Scanner sc = null;
		Map<String,String> stemTable = new HashMap<String,String>();
		File stemsFile = new File(paths.getProperty("stems"));
		try {
			sc = new Scanner(stemsFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		while (sc.hasNext()) {
			String root = sc.next().trim();
			String token = sc.next().trim();
			stemTable.put(token, root);
		}
		Set<String> stopwords = new HashSet<String>();
		File stopwordsFile = new File(paths.getProperty("stopwords"));
		try {
			sc = new Scanner(stopwordsFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		while (sc.hasNext()) {
			stopwords.add(sc.next().trim());
		}
		for (Bookmark bookmark : bookmarks) {
			List<String> tokens = Arrays.asList(bookmark.body.toLowerCase().split(" "));
			for (int i = 0; i < tokens.size(); ++i) {
				String token = tokens.get(i);
				if (stemTable.containsKey(token)) {
					tokens.set(i, stemTable.get(token));
				}
			}
			tokens = new LinkedList<String>(tokens);
			Iterator<String> tokensIter = tokens.iterator();
			while (tokensIter.hasNext()) {
				String token = tokensIter.next();
				if (stopwords.contains(token)) {
					tokensIter.remove();
				}
			}
			bookmark.tokens = ImmutableList.copyOf(tokens);
		}
		
		// create dictionary, create reverse lookup table, and compress each bookmark's body using lookup table
		List<String> dict = TextMinerFuncs.getDict(bookmarks);
		Map<String,Integer> tokenLookup = TextMinerFuncs.getTokenLookupTable(dict);
		for (Bookmark bookmark : bookmarks) {
			List<Integer> bodyCompressed = new ArrayList<Integer>(bookmark.tokens.size());
			for (String token : bookmark.tokens) {
				bodyCompressed.add(tokenLookup.get(token));
				if (tokenLookup.get(token) == null) System.out.println(tokenLookup.get(token));
			}
			bookmark.bodyCompressed = ImmutableList.copyOf(bodyCompressed);
		}
		
		// calculate tfidf for each term for each bookmark
		Map<Integer,Integer> df = TextMinerFuncs.calcTfidfs(bookmarks);
		for (Bookmark bookmark : bookmarks) {
			System.out.println(bookmark.url);
			for (TokenValue tv : bookmark.sortedTfidf) {
//				if (tv.value.intValue() >= 1) {
					String token = dict.get(tv.tokenId);
					System.out.printf("%20s\t%5.02f\t%3d%n", token, tv.value, bookmark.tf.get(tv.tokenId));
//				}
			}
		}
		
/*		// run TMSK/RIKTEXT
		String tmPropsPath = paths.getProperty("tmsk_properties");
		TextMiner tm = new TextMiner(corpusPath, tmPropsPath);*/
		
/*		// grab classifications and reconstruct Bookmarks file
		ChromeBookmarksIO.write(bookmarksFilePath, bookmarks);*/
	}
}
