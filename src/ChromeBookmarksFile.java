import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;


public class ChromeBookmarksFile
{
	String checksum;
	public Map<String,ChromeBookmark> roots;
	int version;
	
	@Override
	public String toString()
	{
		return "ChromeBookmarksFile [checksum=" + checksum + ", roots=" + roots
				+ ", version=" + version + "]";
	}	
	
	public static void main(String[] args)
	{
		String bookmarksFilePath = "Bookmarks-short.json";
		Gson gson = new Gson();
		ChromeBookmarksFile cbf;
		try {
			cbf = gson.fromJson(new BufferedReader(new FileReader(bookmarksFilePath)), ChromeBookmarksFile.class);
			System.out.println(cbf);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
}
