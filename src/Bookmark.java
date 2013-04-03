import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;


public class Bookmark
{
	private static int lastIdUsed = 0;
	
	public final long date;
	public final int id;
	public final String url;
	
	public String label = null;
	//public HashSet<String> labels = null;
	
	public String rawPage = null;
	public String title = null;
	public String body = null;
	
	public List<String> tokens = null;	
	public ImmutableList<Integer> bodyCompressed = null;
	
	public ImmutableMap<Integer,Integer> tf = null;
	public ImmutableMap<Integer,Double> tfidf = null;
	public ImmutableList<TokenValue> sortedTf = null;
	public ImmutableList<TokenValue> sortedTfidf = null;
	
	public Bookmark(long date, int id, String url, String label)
	{
		this.date = date;
		this.id = id;
		this.url = url;
		this.label = label;
	}
	
	public Bookmark(long date, int id, String url)
	{
		this(date, id, url, null);
	}
	
	public Bookmark(String url)
	{
		this(-1, ++lastIdUsed, url, null);
	}
	
	@Override
	public String toString()
	{
		if (label != null)
			return label + " " + url;
		else
			return url;
	}
}
