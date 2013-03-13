import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;


public class TextMinerFuncs
{
	public Set<String> getDict(List<Bookmark> bookmarks)
	{
		Set<String> dict = new HashSet<String>();
		for (Bookmark bookmark : bookmarks) {
			for (String token : bookmark.body.split(" ")) {
				dict.add(token);
			}
		}
		return dict;
	}
	
	public void calcTfs(List<Bookmark> bookmarks)
	{
		for (Bookmark bookmark : bookmarks) {
			Map<String,Integer> tf = new HashMap<String,Integer>(); 
			for (String token : bookmark.body.split(" ")) {
				if (tf.containsKey(token)) {
					tf.put(token, tf.get(token)+1);
				} else {
					tf.put(token, 1);
				}
			}
			Comparator<String> valueComparator = Ordering.natural().onResultOf(Functions.forMap(tf));
			bookmark.tf = ImmutableSortedMap.copyOf(tf, valueComparator);
		}
	}
	
	private double calcIdf(List<Bookmark> bookmarks)
	{
		Set<String> dict = getDict(bookmarks);
		return Math.log(1.0 * bookmarks.size() / dict.size());
	}
	
	public void calcTfidfs(List<Bookmark> bookmarks)
	{
		calcTfs(bookmarks);
		double idf = calcIdf(bookmarks);
		for (Bookmark bookmark : bookmarks) {
			Map<String,Double> tfidf = new HashMap<String,Double>();
			for (String token : bookmark.tf.keySet()) {
				tfidf.put(token, bookmark.tf.get(token)*idf);
			}
			Comparator<String> valueComparator = Ordering.natural().onResultOf(Functions.forMap(tfidf));
			bookmark.tfidf = ImmutableSortedMap.copyOf(tfidf, valueComparator);
		}
	}
}
