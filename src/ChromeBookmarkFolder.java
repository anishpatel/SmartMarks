import java.util.List;


public abstract class ChromeBookmarkFolder extends ChromeBookmark
{
    public List<ChromeBookmark> children;
    
	public ChromeBookmarkFolder(long date_added, int id, String name, String type, List<ChromeBookmark> children)
	{
		super(date_added, id, name, type);
		this.children = children;
	}
	
	@Override
	public String toString()
	{
		return "ChromeBookmark [date_added=" + date_added + ", id=" + id
				+ ", name=" + name + ", type=" + type + ", nChildren=" + children.size() + "]";
	}
}
