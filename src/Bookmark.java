import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;


public class Bookmark
{
	private static int lastIdUsed = 0;
	
	public final int id;
	public final String url;
	
	//public HashSet<String> userLabels = null;
	//public HashSet<String> autoLabels = null;
	
	public String folder = null;
	
	public String rawPage = null;
	public String title = null;
	public String body = null;
	
	public List<String> tokens = null;	
	public ImmutableList<Integer> bodyCompressed = null;
	
	public ImmutableMap<Integer,Integer> tf = null;
	public ImmutableMap<Integer,Double> tfidf = null;
	public ImmutableList<TokenValue> sortedTf = null;
	public ImmutableList<TokenValue> sortedTfidf = null;
	
	public Bookmark(String url)
	{
		lastIdUsed += 1;
		this.id = lastIdUsed;
		this.url = url;
	}
}
