import java.util.LinkedList;
import java.util.List;


public class BookmarkFactory
{
	private final ChromeBookmarksFile cbf;
	
	private List<Bookmark> ChromeBookmarkToBookmarks(ChromeBookmark cBookmark, String currFolder)
	{
		List<Bookmark> bookmarks = new LinkedList<Bookmark>();
		if (cBookmark.type.equals("folder")) {
			for (ChromeBookmark childCBookmark : cBookmark.children) {
				bookmarks.addAll( ChromeBookmarkToBookmarks(childCBookmark, cBookmark.name) );
			}
		} else if (cBookmark.type.equals("url")) {
			bookmarks.add(new Bookmark(cBookmark.date_added, cBookmark.id, cBookmark.url, currFolder));
		}
		return bookmarks;
	}
	
	private List<Bookmark> ChromeBookmarkToBookmarks(ChromeBookmark cBookmark)
	{
		List<Bookmark> bookmarks = new LinkedList<Bookmark>();
		if (cBookmark.type.equals("folder")) {
			for (ChromeBookmark childCBookmark : cBookmark.children) {
				bookmarks.addAll( ChromeBookmarkToBookmarks(childCBookmark) );
			}
		} else if (cBookmark.type.equals("url")) {
			bookmarks.add(new Bookmark(cBookmark.date_added, cBookmark.id, cBookmark.url));
		}
		return bookmarks;
	}
	
	public BookmarkFactory(ChromeBookmarksFile cbf)
	{
		this.cbf = cbf;
	}
	
	public List<Bookmark> getTrainingBookmarks()
	{
		return ChromeBookmarkToBookmarks(cbf.roots.get("bookmark_bar"), "bookmark_bar");
	}
	
	public List<Bookmark> getTestBookmarks()
	{
		return ChromeBookmarkToBookmarks(cbf.roots.get("other"));
	}
	
	public static void main(String[] args)
	{
		String bookmarksFilePath = "Bookmarks.json";
		ChromeBookmarksFile cbf = JsonFileIO.read(bookmarksFilePath, ChromeBookmarksFile.class);
		BookmarkFactory bf = new BookmarkFactory(cbf);
		List<Bookmark> trainBookmarks = bf.getTrainingBookmarks();
		for (Bookmark bookmark : trainBookmarks) {
			System.out.println(bookmark);
		}
		for (int i = 0; i < 80; ++i) System.out.print('-');
		System.out.println();
		List<Bookmark> testBookmarks = bf.getTestBookmarks();
		for (Bookmark bookmark : testBookmarks) {
			System.out.println(bookmark);
		}
	}
}
