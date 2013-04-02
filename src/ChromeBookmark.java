import java.util.List;

import com.google.gson.Gson;


public class ChromeBookmark
{
    public long date_added;
    public int id;
    public String name;
    public String type;
    public String url;
    public List<ChromeBookmark> children;
    
	@Override
	public String toString()
	{
		return "ChromeBookmark [date_added=" + date_added + ", id=" + id
				+ ", name=" + name + ", type=" + type + ", url=" + url
				+ ", children=" + children + "]";
	}
	
	public static void main(String[] args)
	{
		Gson gson = new Gson();
		ChromeBookmark cb = gson.fromJson("{\r\n" + 
				"         \"children\": [ {\r\n" + 
				"            \"date_added\": \"13008777126258647\",\r\n" + 
				"            \"id\": \"80\",\r\n" + 
				"            \"name\": \"Allrecipes - Recipes and cooking confidence for home cooks everywhere\",\r\n" + 
				"            \"type\": \"url\",\r\n" + 
				"            \"url\": \"http://allrecipes.com/\"\r\n" + 
				"         }, {\r\n" + 
				"            \"date_added\": \"13008777417044357\",\r\n" + 
				"            \"id\": \"81\",\r\n" + 
				"            \"name\": \"Aaron Swartz Prosecutor Carmen Ortiz Admonished In 2004 For Aggressive Tactic\",\r\n" + 
				"            \"type\": \"url\",\r\n" + 
				"            \"url\": \"http://www.huffingtonpost.com/2013/03/25/aaron-swartz-carmen-ortiz_n_2951478.html\"\r\n" + 
				"         }, {\r\n" + 
				"            \"date_added\": \"13008777440385423\",\r\n" + 
				"            \"id\": \"82\",\r\n" + 
				"            \"name\": \"Kindle Fire HD - Most Advanced 7\\\" Tablet - Only $199\",\r\n" + 
				"            \"type\": \"url\",\r\n" + 
				"            \"url\": \"http://www.amazon.com/gp/product/B0083PWAPW/ref=kin_dev_gw_dual_t?ie=UTF8&nav_sdd=aps&pf_rd_m=ATVPDKIKX0DER&pf_rd_s=center-1&pf_rd_r=1W1S81ZVJ1Q9GCC0QC1T&pf_rd_t=101&pf_rd_p=1493999442&pf_rd_i=507846\"\r\n" + 
				"         } ],\r\n" + 
				"         \"date_added\": \"13006835012115968\",\r\n" + 
				"         \"date_modified\": \"13008777440385423\",\r\n" + 
				"         \"id\": \"2\",\r\n" + 
				"         \"name\": \"Other bookmarks\",\r\n" + 
				"         \"type\": \"folder\"\r\n" + 
				"      }", ChromeBookmark.class);
		System.out.println(cb);
	}
}
