
public class Bookmark
{
	private static int lastIdUsed = 0;
	
	public final int id;
	public final String url;
	
	//public HashSet<String> userLabels = null;
	//public HashSet<String> autoLabels = null;
	
	public String rawPage = null;
	public String title = null;
	public String rawBody = null;
	public String bodyText = null;
	
	public Bookmark(String url)
	{
		lastIdUsed += 1;
		this.id = lastIdUsed;
		this.url = url;
	}
}
