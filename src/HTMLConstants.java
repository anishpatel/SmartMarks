import java.util.regex.Pattern;


public class HTMLConstants {

	public static final Pattern HTML_TITLE_REGEX = Pattern.compile("<title(.+?)</title>");;
	public static final Pattern HTML_BODY_REGEX = Pattern.compile("<body(.+?)</body>");;
	public static final Pattern HTML_SCRIPT_REGEX = Pattern.compile("<script(.+?)</script>");;
	
	public static final String 	HTML_TITLE = "title";
	public static final String 	HTML_TITLE_START = "<title>";
	public static final String 	HTML_TITLE_END = "</title>";
	
	public static final String 	HTML_BODY = "body";
	public static final String 	HTML_BODY_START = "<body>";
	public static final String 	HTML_BODY_END = "</body>";
	
	public static final String 	HTML_SCRIPT_START = "<script>";
	public static final String 	HTML_SCRIPT_END = "</script>";
}
