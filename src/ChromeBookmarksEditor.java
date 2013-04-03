import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ChromeBookmarksEditor
{
	private final static String IGNORE_FOLDER = "ignore";
	
	private final ChromeBookmarksFile cbf;
	
	private List<Bookmark> ChromeBookmarkToBookmarks(ChromeBookmark cBookmark, String currFolder)
	{
		List<Bookmark> bookmarks = new LinkedList<Bookmark>();
		if (cBookmark.type.equals("folder") && (! cBookmark.name.equalsIgnoreCase(IGNORE_FOLDER))) {
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
	
	private List<ChromeBookmark> BookmarksToChromebookmarks(List<Bookmark> bookmarks)
	{
		List<ChromeBookmark> cBookmarks = new LinkedList<ChromeBookmark>();
		for (Bookmark bookmark : bookmarks) {
			ChromeBookmark cBookmark = new ChromeBookmark();
			cBookmark.date_added = bookmark.date;
			cBookmark.id = bookmark.id;
			cBookmark.name = bookmark.title;
			cBookmark.type = "url";
			cBookmark.url = bookmark.url;
			cBookmarks.add(cBookmark);
		}
		return cBookmarks;
	}
	
	public ChromeBookmarksEditor(ChromeBookmarksFile cbf)
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
	
	public ChromeBookmarksFile updateTestBookmarks(List<Bookmark> bookmarks)
	{
		// add test bookmarks to folders by label
		Map<String,List<Bookmark>> lbledTestBookmarks = new HashMap<String,List<Bookmark>>();
		for (Bookmark bookmark : bookmarks) {
			if (! lbledTestBookmarks.containsKey(bookmark.label))
				lbledTestBookmarks.put(bookmark.label, new LinkedList<Bookmark>());
			lbledTestBookmarks.get(bookmark.label).add(bookmark);
		}
		addTestBookmarks(lbledTestBookmarks, cbf.roots.get("bookmark_bar"));
		
		// remove test bookmarks from Other
		Map<Integer,Bookmark> idTestBookmarks = new HashMap<Integer,Bookmark>();
		for (Bookmark bookmark : bookmarks) {
			idTestBookmarks.put(bookmark.id, bookmark);
		}
		removeTestBookmarks(idTestBookmarks, cbf.roots.get("other"));
		
		return cbf;
	}
	
	private void addTestBookmarks(Map<String,List<Bookmark>> bookmarks, ChromeBookmark cBookmark)
	{
		Set<String> labels = bookmarks.keySet();
		if (cBookmark.type.equals("folder") && (! cBookmark.name.equalsIgnoreCase(IGNORE_FOLDER))) {
			String folderName = cBookmark.name.toLowerCase();
			if (labels.contains(folderName)) {
				List<ChromeBookmark> cBookmarks = BookmarksToChromebookmarks( bookmarks.get(folderName) );
				cBookmark.children.addAll(cBookmarks);
			}
		}
	}
	
	private void removeTestBookmarks(Map<Integer,Bookmark> bookmarks, ChromeBookmark cBookmarkFolder)
	{
		assert cBookmarkFolder.type.equals("folder");
		if (! cBookmarkFolder.name.equalsIgnoreCase(IGNORE_FOLDER)) {
			Iterator<ChromeBookmark> it = cBookmarkFolder.children.iterator();
			while (it.hasNext()) {
				ChromeBookmark childCBookmark = it.next();
				if (bookmarks.containsKey(childCBookmark.id))
					it.remove();
				else if (childCBookmark.type.equals("folder"))
					removeTestBookmarks(bookmarks, childCBookmark);
			}
		}
			
	}
	
	public static void main(String[] args)
	{
		String bookmarksFilePath = "Bookmarks.json";
		ChromeBookmarksFile cbf = JsonFileIO.read(bookmarksFilePath, ChromeBookmarksFile.class);
		ChromeBookmarksEditor bf = new ChromeBookmarksEditor(cbf);
		List<Bookmark> trainBookmarks = bf.getTrainingBookmarks();
		List<Bookmark> testBookmarks = bf.getTestBookmarks();
		
		// print bookmarks
		for (Bookmark bookmark : trainBookmarks) System.out.println(bookmark);
		for (int i = 0; i < 80; ++i) System.out.print('-');
		System.out.println();
		for (Bookmark bookmark : testBookmarks) System.out.println(bookmark);
	}
}
