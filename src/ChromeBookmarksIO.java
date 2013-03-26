import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class ChromeBookmarksIO
{	
	private static void recur_read(JsonReader reader, List<Bookmark> bookmarks) throws IOException
	{
/*		List<Bookmark> folder = new ArrayList<Bookmark>();
		String name = "";
		while (reader.hasNext()) {
			JsonToken nextToken = reader.peek();
			if (nextToken == JsonToken.NAME) {
				String tokenName = reader.nextName();
				if (tokenName.equals("name")) {
					name = reader.nextString();
				} else if (tokenName.equals("type")) {
					String type = reader.nextString();
					if (type.equals("folder")) {
						for (Bookmark bookmark : folder) {
							bookmark.folder = name;
						}
					} else if (type.equals("url")) {
						reader.nextName();
						String url = reader.nextString();
						folder.add(new Bookmark(url));
					}
				} else if (tokenName.equals("Bookmarks bar")
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
		}*/
	}
	
	public static ChromeBookmarkFolder read(String filePath)
	{
		JsonReader reader = null;
		 try {
			 ChromeBookmarkFolder bookmarksBar = null;
			 reader = new JsonReader(new BufferedReader(new FileReader(filePath)));
			 reader.beginObject();
			 while (reader.hasNext()) {
				JsonToken nextToken = reader.peek();
				if (nextToken == JsonToken.NAME) {
					String tokenName = reader.nextName();
					if (tokenName.equals("roots")) {
						reader.beginObject();
//						recur_read(reader, bookmarks);
						Gson gson = new Gson();
						tokenName = reader.nextName();
						reader.beginObject();
						bookmarksBar = gson.fromJson(reader, ChromeBookmarkFolder.class);
						reader.endObject();
						tokenName = reader.nextName();
						reader.beginObject();
						ChromeBookmarkFolder other = gson.fromJson(reader, ChromeBookmarkFolder.class);
						reader.endObject();
						tokenName = reader.nextName();
						reader.beginObject();
						ChromeBookmarkFolder synced = gson.fromJson(reader, ChromeBookmarkFolder.class);
						reader.endObject();
						reader.endObject();
					}
				} else {
					reader.skipValue();
				}
			 }
			 reader.endObject();
			 return bookmarksBar;
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
	
/*	public static List<Bookmark> read(String filePath)
	{
		List<Bookmark> bookmarks = new ArrayList<Bookmark>(); 
		JsonReader reader = null;
		 try {
			reader = new JsonReader(new BufferedReader(new FileReader(filePath)));
			reader.beginObject();
			while (reader.hasNext()) {
				JsonToken nextToken = reader.peek();
				if (nextToken == JsonToken.NAME) {
					String tokenName = reader.nextName();
					if (tokenName.equals("roots")) {
//						recur_read(reader, bookmarks);
						Gson gson = new Gson();
						tokenName = reader.nextName();
						ChromeBookmarkFolder bookmarksBar = gson.fromJson(reader, ChromeBookmarkFolder.class);
						tokenName = reader.nextName();
						ChromeBookmarkFolder other = gson.fromJson(reader, ChromeBookmarkFolder.class);
						tokenName = reader.nextName();
						ChromeBookmarkFolder synced = gson.fromJson(reader, ChromeBookmarkFolder.class);
					}
				}
			}
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
	}*/
	
	public static void append(String filePath, List<Bookmark> bookmarks)
	{
		throw new UnsupportedOperationException("TODO");
	}
	
	public static void main(String[] args)
	{
/*		List<Bookmark> bookmarks = ChromeBookmarksIO.read("Bookmarks");
		for (Bookmark bookmark : bookmarks) {
			System.out.println(bookmark.url);
		}
		System.out.println(bookmarks.size());*/
		
		ChromeBookmarkFolder bookmarksBar = ChromeBookmarksIO.read("Bookmarks");
		System.out.println(bookmarksBar.children.size());
	}
}
