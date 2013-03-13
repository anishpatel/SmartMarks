import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLPreProcessor
{
	public static final Pattern HTML_TITLE_REGEX = Pattern.compile("<title.*?>(.*?)</title>");
	public static final Pattern HTML_BODY_REGEX = Pattern.compile("<body.*?>(.*?)</body>");
	
	public static final String 	HTML_TITLE_START = "<title>";
	public static final String 	HTML_TITLE_END = "</title>";
	
	public static final String 	HTML_BODY_START = "<body>";
	public static final String 	HTML_BODY_END = "</body>";
	
	private static String replaceWhiteSpaceWithSpace(String s)
	{
		return s.replaceAll("\\s+", " ").trim();
	}

	private static String getText(String html)
	{
		String text = html;
		text = text.replaceAll("<script.*?>.*?</script>", " ");
		text = text.replaceAll("<style.*?>.*?</style>", " ");
		text = text.replaceAll("<.+?>", " ");
		text = text.replaceAll("&.+?;", " ");
		text = text.replaceAll("[^a-zA-Z_\\s'$]", " ");
		text = replaceWhiteSpaceWithSpace(text);
		return text;
	}
	
	public static String getTitle(String html)
	{
		html = replaceWhiteSpaceWithSpace(html);
		Matcher matcher = HTML_TITLE_REGEX.matcher(html);
		if (matcher.find() && matcher.groupCount() > 0) {
			return getText(matcher.group(1));
		} else {
			return "";
		}
	}
	
	public static String getBody(String html)
	{
		html = replaceWhiteSpaceWithSpace(html);
		Matcher matcher = HTML_BODY_REGEX.matcher(html);
		if (matcher.find() && matcher.groupCount() > 0) {
			return getText(matcher.group(1));
		} else {
			return "";
		}
	}
}
