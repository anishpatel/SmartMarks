import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLPreProcessor {

	public static String ReplaceHTMLElementsWithText(String content) {
		content = content.replaceAll("\n", "");
		content = content.replaceAll("&nbsp;", "");
		content = content.replaceAll("&amp;", "&");
		content = content.replaceAll("<;", "");
		content = content.replaceAll(">;", "");
		content = content.replaceAll("&lt;", "<");
		content = content.replaceAll("&gt;", ">");

		return content;
	}

	public static String RemoveScriptFromHTML(String content) {
		String contentWithoutScripts = content.replaceAll("\n", "");
		Matcher matcher = HTMLConstants.HTML_SCRIPT_REGEX.matcher(content);
		List<String> scriptList = new ArrayList<String> ();
		while (matcher.find() && matcher.groupCount() > 0) {
			String htmlContent = matcher.group();
			scriptList.add(htmlContent);
		}
		for (String script : scriptList) {
			contentWithoutScripts = contentWithoutScripts.replace(script, "");
		}

		contentWithoutScripts = contentWithoutScripts.replaceAll(HTMLConstants.HTML_SCRIPT_START, "");
		contentWithoutScripts = contentWithoutScripts.replaceAll(HTMLConstants.HTML_SCRIPT_END, "");

		return contentWithoutScripts;
	}

	public static String RemoveHyperlinksFromHTML(String content, String strPageURL) throws MalformedURLException
	{
		URL pageURL = new URL(strPageURL);
		String contentWithoutHyperlinks = content;
		Pattern p = Pattern.compile("<a\\s+href\\s*=\\s*\"?(.*?)[\"|>]", Pattern.CASE_INSENSITIVE);
		  Matcher m = p.matcher(content);
		  // Create list of link matches.
//		  List<String>  linkList = new ArrayList<String>();
		  while (m.find() && m.groupCount()>0) {
			String link = m.group(1).trim();
			String removeLink = link;
			// Skip empty links.
			if (link.length() < 1) {
				  continue;
			}
			// Skip links that are just page anchors.
			if (link.charAt(0) == '#') {
				  continue;
			}		
			// Skip mailto links.
			if (link.indexOf("mailto:") != -1) {
				  continue;
			}
			// Skip Javascript links.
			if (link.toLowerCase().indexOf("javascript") != -1) {
				  continue;
 			}
			// Prefix absolute and relative URLs if necessary.
			if (link.indexOf("://") == -1) {
				  // Handle absolute URLs.
				  if (link.charAt(0) == '/') {
					link = "http://" + pageURL.getHost() + link;
					  // Handle relative URLs.
				  } 
				  else {
					String file = pageURL.getFile();
					if (file.indexOf('/') == -1) {
						  link = "http://" + pageURL.getHost() + "/" + link;
					} 
					else {
						  String path = file.substring(0, file.lastIndexOf('/') + 1); 
						  link = "http://" + pageURL.getHost() + path + link;
					}
				  }
			}
			// Remove anchors from link.
			int index = link.indexOf('#');
			if (index != -1) {
				  link = link.substring(0, index);
			}
			
			contentWithoutHyperlinks = contentWithoutHyperlinks.replace(removeLink, "");
		  }
		return contentWithoutHyperlinks;
	}
	public static Map<String, String> TokenizeHTMLDocument(String content) {
		content = content.replaceAll("\n", "");
		String htmlTitle = "", htmlBody = "";

		Map<String, String> htmlContentMap = new HashMap<String, String>();
		Matcher matcher = HTMLConstants.HTML_TITLE_REGEX.matcher(content);
		if (matcher.find() && matcher.groupCount() > 0) {
			htmlTitle = matcher.group(1);
			htmlTitle = htmlTitle.replace(HTMLConstants.HTML_TITLE_START, "");
			htmlTitle = htmlTitle.replace(HTMLConstants.HTML_TITLE_END, "");
		}
		matcher = HTMLConstants.HTML_BODY_REGEX.matcher(content);
		if (matcher.find() && matcher.groupCount() > 0) {
			htmlBody = matcher.group(1);
			htmlBody = htmlBody.replace(HTMLConstants.HTML_BODY_START, "");
			htmlBody = htmlBody.replace(HTMLConstants.HTML_BODY_END, "");
		}

		// remove all HTML tags
		htmlBody = htmlBody.replaceAll("&lt;.+?&gt;", " ");
		
		// remove all non alpha characters from title and body
		htmlTitle = htmlTitle.replaceAll("[^A-Za-z\\s]", " ");
//		htmlBody = htmlBody.replaceAll("[^A-Za-z\\s]", " ");
		
		// remove any tokens with no more than 2 characters
//		htmlBody = htmlBody.replaceAll("[A-Za-z]{1,2}", "");
		htmlBody = htmlBody.replaceAll("\\b[\\w']{1,2}\\b", "");
		
		// replace multiple adjacent spaces with a single space
		htmlTitle = htmlTitle.replaceAll("\\s{2,}", " ").trim();
		htmlBody = htmlBody.replaceAll("\\s{2,}", " ").trim();
		
		// read stopwords
		Set<String> stopwords = new HashSet<String>();
		try {
			Scanner stopwordsScanner = new Scanner(new File("stopwords.txt"));
			while (stopwordsScanner.hasNext()) {
				stopwords.add(stopwordsScanner.next());
			}
			stopwordsScanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// tokenize body
		List<String> htmlBodyTokens = new LinkedList<String>(Arrays.asList(htmlBody.split(" ")));
		System.out.println(htmlBodyTokens.size());
/*		Iterator<String> htmlBodyTokensIter = htmlBodyTokens.iterator();
		while (htmlBodyTokensIter.hasNext()) {
			String token = htmlBodyTokensIter.next();
			if (stopwords.contains(token)) {
				htmlBodyTokensIter.remove();
			}
		}*/
		for (int i = 0; i < htmlBodyTokens.size(); ++i) {
			String token = htmlBodyTokens.get(i);
			if (stopwords.contains(token)) {
				htmlBodyTokens.remove(i);
				--i;
			}
		}
		System.out.println(htmlBodyTokens.size());
	    StringBuilder sb = new StringBuilder();
	    String sep = " ";
	    for(String token : htmlBodyTokens) {
	        sb.append(sep).append(token);
	    }
	    htmlBody = sb.toString();
	    System.out.println("length2=" + htmlBody.length());
		
		htmlContentMap.put(HTMLConstants.HTML_TITLE, htmlTitle);
		htmlContentMap.put(HTMLConstants.HTML_BODY, htmlBody);

		return htmlContentMap;
	}
}
