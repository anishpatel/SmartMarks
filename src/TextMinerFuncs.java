import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;


public class TextMinerFuncs
{
	public static List<String> getDict(List<Bookmark> bookmarks)
	{
		SortedSet<String> dict = new TreeSet<String>();
		for (Bookmark bookmark : bookmarks) {
			dict.addAll(bookmark.tokens);
		}
		return ImmutableList.copyOf(dict);
	}
	
	public static Map<String,Integer> getTokenLookupTable(List<String> dict)
	{
		Map<String,Integer> tokenLookup = new HashMap<String,Integer>(dict.size());
		for (int i = 0; i < dict.size(); ++i) {
			tokenLookup.put(dict.get(i), i);
		}
		return ImmutableMap.copyOf(tokenLookup);
	}
	
	public static void calcTfs(List<Bookmark> bookmarks)
	{
		for (Bookmark bookmark : bookmarks) {
			Map<Integer,Integer> tf = new HashMap<Integer,Integer>(); 
			for (int tokenId : bookmark.bodyCompressed) {
				if (tf.containsKey(tokenId)) {
					tf.put(tokenId, tf.get(tokenId)+1);
				} else {
					tf.put(tokenId, 1);
				}
			}
			bookmark.tf = ImmutableMap.copyOf(tf);
			List<TokenValue> sortedTf = new ArrayList<TokenValue>(tf.size());
			for (int tokenId : tf.keySet()) {
				sortedTf.add(new TokenValue(tokenId, tf.get(tokenId)));
			}
			Collections.sort(sortedTf);
			bookmark.sortedTf = ImmutableList.copyOf(sortedTf);
		}
	}
	
	public static Map<Integer,Integer> calcTfidfs(List<Bookmark> bookmarks)
	{
		// create df for all docs and tf for each doc
		Map<Integer,Integer> df = new HashMap<Integer,Integer>();
		for (Bookmark bookmark : bookmarks) {
			Map<Integer,Integer> tf = new HashMap<Integer,Integer>(); 
			for (int tokenId : bookmark.bodyCompressed) {
				if (tf.containsKey(tokenId)) {
					tf.put(tokenId, tf.get(tokenId)+1);
				} else {
					tf.put(tokenId, 1);
					if (df.containsKey(tokenId)) {
						df.put(tokenId, df.get(tokenId)+1);
					} else {
						df.put(tokenId, 1);
					}
				}
			}
			bookmark.tf = ImmutableMap.copyOf(tf);
			List<TokenValue> sortedTf = new ArrayList<TokenValue>(tf.size());
			for (int tokenId : tf.keySet()) {
				sortedTf.add(new TokenValue(tokenId, tf.get(tokenId)));
			}
			Collections.sort(sortedTf, Collections.reverseOrder());
			bookmark.sortedTf = ImmutableList.copyOf(sortedTf);
		}
		
		// compute tfidf for each doc
		for (Bookmark bookmark : bookmarks) {
			Map<Integer,Double> tfidf = new HashMap<Integer,Double>(bookmark.tf.size());
			for (int tokenId : bookmark.tf.keySet()) {
				double idf = Math.log( 1.0 * bookmarks.size() / df.get(tokenId) );
				tfidf.put(tokenId, bookmark.tf.get(tokenId) * idf);
			}
			bookmark.tfidf = ImmutableMap.copyOf(tfidf);
			List<TokenValue> sortedTfidf = new ArrayList<TokenValue>(tfidf.size());
			for (int tokenId : tfidf.keySet()) {
				sortedTfidf.add(new TokenValue(tokenId, tfidf.get(tokenId)));
			}
			Collections.sort(sortedTfidf, Collections.reverseOrder());
			bookmark.sortedTfidf = ImmutableList.copyOf(sortedTfidf);
		}
		
		return ImmutableMap.copyOf(df);
	}
}
