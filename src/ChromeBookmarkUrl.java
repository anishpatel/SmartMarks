

public abstract class ChromeBookmarkUrl extends ChromeBookmark
{
	public final String url;
    
	public ChromeBookmarkUrl(long date_added, int id, String name, String type, String url)
	{
		super(date_added, id, name, type);
		this.url = url;
	}
	
	@Override
	public String toString()
	{
		return "ChromeBookmark [date_added=" + date_added + ", id=" + id
				+ ", name=" + name + ", type=" + type + ", url=" + url + "]";
	}
}
