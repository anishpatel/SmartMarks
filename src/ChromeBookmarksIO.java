import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class ChromeBookmarksIO
{	
	private static void recur_read(JsonReader reader, List<Bookmark> bookmarks) throws IOException
	{
		while (reader.hasNext()) {
			JsonToken nextToken = reader.peek();
			if (nextToken == JsonToken.NAME) {
				String name = reader.nextName();
				if (name.equals("url")) {
					String url = reader.nextString();
					bookmarks.add(new Bookmark(url));
				}
			} else if (nextToken == JsonToken.BEGIN_OBJECT) {
				reader.beginObject();
				recur_read(reader, bookmarks);
				reader.endObject();
			} else if (nextToken == JsonToken.BEGIN_ARRAY) {
				reader.beginArray();
				recur_read(reader, bookmarks);
				reader.endArray();
			} else {
				reader.skipValue();
			}
		}
	}
	
	public static List<Bookmark> read(String filePath)
	{
		List<Bookmark> bookmarks = new ArrayList<Bookmark>(); 
		JsonReader reader = null;
		 try {
			reader = new JsonReader(new BufferedReader(new FileReader(filePath)));
			reader.beginObject();
			recur_read(reader, bookmarks);
			reader.endObject();
			return bookmarks;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static void append(String filePath, List<Bookmark> bookmarks)
	{
		throw new UnsupportedOperationException("TODO");
	}
	
	public static void main(String[] args)
	{
		List<Bookmark> bookmarks = ChromeBookmarksIO.read("Bookmarks");
		for (Bookmark bookmark : bookmarks) {
			System.out.println(bookmark.url);
		}
		System.out.println(bookmarks.size());
	}
}
