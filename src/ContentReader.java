import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ContentReader {
	public static String GetStatusCode(URL url) throws IOException {
		String statusCode = "404";
		URLConnection uc = url.openConnection();
		uc.setRequestProperty("Authorization", "Basic bG9hbnNkZXY6bG9AbnNkM3Y=");
		uc.setRequestProperty("User-Agent", "");
		uc.connect();
		statusCode = uc.getHeaderField(0);

		return statusCode;
	}

	public static String GetContentType(URL url) throws IOException {
		String contentType = "";
		URLConnection uc = url.openConnection();
		uc.setRequestProperty("Authorization", "Basic bG9hbnNkZXY6bG9AbnNkM3Y=");
		uc.setRequestProperty("User-Agent", "");
		uc.connect();
		contentType = uc.getContentType();
		
		return contentType;
	}

	public static String GetContent(URL url) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String strTemp = "";
		StringBuffer content = new StringBuffer();
		while (null != (strTemp = br.readLine())) {
			content.append(strTemp);
		}
		return content.toString();
	}
}