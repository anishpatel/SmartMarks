import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TextMinerFuncs
{
/*	public static Set<String> getDict(List<Bookmark> bookmarks)
	{
		Set<String> dict = new HashSet<String>();
		for (Bookmark bookmark : bookmarks) {
			for (String token : bookmark.body.split(" ")) {
				dict.add(token);
			}
		}
		return dict;
	}*/
	
	public static void calcTfs(List<Bookmark> bookmarks)
	{
		for (Bookmark bookmark : bookmarks) {
			Map<String,Integer> tf = new HashMap<String,Integer>(); 
			for (String token : bookmark.body.split(" ")) {
				token = token.toLowerCase();
				if (tf.containsKey(token)) {
					tf.put(token, tf.get(token)+1);
				} else {
					tf.put(token, 1);
				}
			}
			bookmark.tf = tf;
			List<TokenValue> sortedTf = new ArrayList<TokenValue>(tf.size());
			for (String token : tf.keySet()) {
				sortedTf.add(new TokenValue(token, tf.get(token)));
			}
			Collections.sort(sortedTf);
			bookmark.sortedTf = sortedTf;
		}
	}
	
	public static void calcTfidfs(List<Bookmark> bookmarks)
	{
		// create df for all docs and tf for each doc
		Map<String,Integer> df = new HashMap<String,Integer>();
		for (Bookmark bookmark : bookmarks) {
			Map<String,Integer> tf = new HashMap<String,Integer>(); 
			for (String token : bookmark.body.split(" ")) {
				token = token.toLowerCase();
				if (tf.containsKey(token)) {
					tf.put(token, tf.get(token)+1);
				} else {
					tf.put(token, 1);
					if (df.containsKey(token)) {
						df.put(token, df.get(token)+1);
					} else {
						df.put(token, 1);
					}
				}
			}
			bookmark.tf = tf;
			List<TokenValue> sortedTf = new ArrayList<TokenValue>(tf.size());
			for (String token : tf.keySet()) {
				sortedTf.add(new TokenValue(token, tf.get(token)));
			}
			Collections.sort(sortedTf, Collections.reverseOrder());
			bookmark.sortedTf = sortedTf;
		}
		
		// compute tfidf for each doc
		for (Bookmark bookmark : bookmarks) {
			Map<String,Double> tfidf = new HashMap<String,Double>();
			for (String token : bookmark.tf.keySet()) {
				double idf = Math.log( 1.0 * bookmarks.size() / df.get(token) );
				tfidf.put(token, bookmark.tf.get(token) * idf);
			}
			bookmark.tfidf = tfidf;
			List<TokenValue> sortedTfidf = new ArrayList<TokenValue>(tfidf.size());
			for (String token : tfidf.keySet()) {
				sortedTfidf.add(new TokenValue(token, tfidf.get(token)));
			}
			Collections.sort(sortedTfidf, Collections.reverseOrder());
			bookmark.sortedTfidf = sortedTfidf;
		}
	}
}
