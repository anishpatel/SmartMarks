import java.util.List;
import java.util.Map;


public class Bookmark
{
	private static int lastIdUsed = 0;
	
	public final int id;
	public final String url;
	
	//public HashSet<String> userLabels = null;
	//public HashSet<String> autoLabels = null;
	
	public String rawPage = null;
	public String title = null;
	public String body = null;
	
	public Map<String,Integer> tf = null;
	public Map<String,Double> tfidf = null;
	
	public List<TokenValue> sortedTf = null;
	public List<TokenValue> sortedTfidf = null;
	
	public Bookmark(String url)
	{
		lastIdUsed += 1;
		this.id = lastIdUsed;
		this.url = url;
	}
}
