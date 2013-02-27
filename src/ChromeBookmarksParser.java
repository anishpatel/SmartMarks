

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class ChromeBookmarksParser
{
	private final List<String> urls = new ArrayList<String>();
	
	private void recur_parse(JsonReader reader) throws IOException
	{
		while (reader.hasNext()) {
			if (reader.peek() == JsonToken.NAME) {
				String name = reader.nextName();
/*				if (name.equals("roots")) {
					reader.beginObject();
					
					reader.endObject();
				} else if (name.equals("bookmark_bar")) {
					reader.beginObject();
					recur_parse(reader); // recurse
					reader.endObject();
				} else if (name.equals("other")) {
					reader.beginObject();
					recur_parse(reader); // recurse
					reader.endObject();
				} else if (name.equals("synced")) {
					reader.beginObject();
					recur_parse(reader); // recurse
					reader.endObject();
				} else if (name.equals("children")) {
					reader.beginArray();
					reader.beginObject();
					recur_parse(reader); // recurse
					reader.endArray();
				} else*/ if (name.equals("url")) {
					String url = reader.nextString();
					urls.add(url);
				} else {
					if (reader.peek() == JsonToken.STRING) {
						reader.skipValue(); // avoid string values
					}
				}
			} else if (reader.peek() == JsonToken.BEGIN_OBJECT) {
				reader.beginObject();
				recur_parse(reader); // recurse
				reader.endObject();
			} else if (reader.peek() == JsonToken.BEGIN_ARRAY) {
				reader.beginArray();
				recur_parse(reader); // recurse
				reader.endArray();
			} else {
				reader.skipValue(); // avoid unhandled events
			}
		}
	}
	
	public void parse()
	{
		 try {
			JsonReader reader = new JsonReader(new FileReader("Bookmarks"));
			reader.beginObject();
			recur_parse(reader);
			reader.endObject();
			reader.close();
	     } catch (FileNotFoundException e) {
			e.printStackTrace();
	     } catch (IOException e) {
			e.printStackTrace();
	     }
	}
	
	public List<String> getUrls()
	{
		return Collections.unmodifiableList(urls);
	}
	
	public static void main(String[] args)
	{
		ChromeBookmarksParser cbp = new ChromeBookmarksParser();
		cbp.parse();
		List<String> urls = cbp.getUrls();
		for (String url : urls) {
			System.out.println(url);
		}
	}

}
