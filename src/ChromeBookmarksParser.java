import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class ChromeBookmarksParser
{
	private final List<String> urls = new ArrayList<String>();
	
	private void recur_parse(JsonReader reader) throws IOException
	{
		while (reader.hasNext()) {
			JsonToken nextToken = reader.peek();
			if (nextToken == JsonToken.NAME) {
				String name = reader.nextName();
				if (name.equals("url")) {
					String url = reader.nextString();
					urls.add(url);
				}
			} else if (nextToken == JsonToken.BEGIN_OBJECT) {
				reader.beginObject();
				recur_parse(reader);
				reader.endObject();
			} else if (nextToken == JsonToken.BEGIN_ARRAY) {
				reader.beginArray();
				recur_parse(reader);
				reader.endArray();
			} else {
				reader.skipValue();
			}
		}
	}
	
	public void parse(String filePath)
	{
		JsonReader reader = null;
		 try {
			reader = new JsonReader(new FileReader(filePath));
			reader.beginObject();
			recur_parse(reader);
			reader.endObject();
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
	}
	
	public List<String> getUrls()
	{
		return Collections.unmodifiableList(urls);
	}
	
	public static void main(String[] args)
	{
		ChromeBookmarksParser cbp = new ChromeBookmarksParser();
		cbp.parse("Bookmarks");
		List<String> urls = cbp.getUrls();
		for (String url : urls) {
			System.out.println(url);
		}
	}
}
