import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ChromeBookmarksIO
{	
	public final static String TRAIN_URLS_FILENAME = "train-urls.csv";
	public final static String TEST_URLS_FILENAME = "test-urls.csv";
	
	public static List<Bookmark> readTrainUrls()
	{
		List<Bookmark> bookmarks = new ArrayList<Bookmark>(); 
		Scanner sc = null;
		try {
			sc = new Scanner(new File(TRAIN_URLS_FILENAME));
			while (sc.hasNext()) {
				String[] urlLabelPair = sc.nextLine().trim().split(";");
				String url = urlLabelPair[0];
				String label = urlLabelPair[1];
				bookmarks.add(new Bookmark(url, label));
			}
			return bookmarks;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
			return null;
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}
	
	public static List<Bookmark> readTestUrls()
	{
		List<Bookmark> bookmarks = new ArrayList<Bookmark>(); 
		Scanner sc = null;
		try {
			sc = new Scanner(new File(TEST_URLS_FILENAME));
			while (sc.hasNext()) {
				String url = sc.nextLine().trim();
				bookmarks.add(new Bookmark(url));
			}
			return bookmarks;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
			return null;
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}
	
	public static void append(String filePath, List<Bookmark> bookmarks)
	{
		throw new UnsupportedOperationException("TODO");
	}
	
	public static void main(String[] args)
	{
		List<Bookmark> trainBookmarks = ChromeBookmarksIO.readTrainUrls();
		List<Bookmark> testBookmarks = ChromeBookmarksIO.readTestUrls();
		System.out.printf("Training Bookmarks (size=%d)%n", trainBookmarks.size());
		for (Bookmark bookmark : trainBookmarks) {
			System.out.println(bookmark.label+",\t"+bookmark.url);
		}
		System.out.printf("Testing Bookmarks (size=%d)%n", testBookmarks.size());
		for (Bookmark bookmark : testBookmarks) {
			System.out.println(bookmark.url);
		}
	}
}
