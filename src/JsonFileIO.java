import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class JsonFileIO
{	
	public static <T> T read(String filePath, Class<T> classOfT)
	{
		try {
			return new Gson().fromJson(new BufferedReader(new FileReader(filePath)), classOfT);
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
		return null;
	}
	
	public static void write(String filePath, Object src)
	{
		try {
			new Gson().toJson(src, new BufferedWriter(new FileWriter(filePath)));
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String[] args)
	{
		String bookmarksFilePath = "Bookmarks-short.json";
		ChromeBookmarksFile cbf = JsonFileIO.read(bookmarksFilePath, ChromeBookmarksFile.class);
		System.out.println(cbf);
	}
}
