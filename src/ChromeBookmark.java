import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public abstract class ChromeBookmark
{
    public long date_added;
    public int id;
    public String name;
    public String type;
    
    public String folderName = null;
    
	public ChromeBookmark(long date_added, int id, String name, String type)
	{
		this.date_added = date_added;
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public static void main(String[] args)
	{
		Gson gson = new Gson();
/*		ChromeBookmark cb = gson.fromJson("{\"date_added\": \"13008450244072270\",\r\n" + 
				"            \"date_modified\": \"13008469526659183\",\r\n" + 
				"            \"id\": \"13\",\r\n" + 
				"            \"name\": \"shopping\",\r\n" + 
				"            \"type\": \"folder\"\r\n" + 
				"         }", ChromeBookmark.class);
		System.out.println(cb);*/
		Type chromeBookmarksListType = new TypeToken<List<ChromeBookmark>>(){}.getType();
		List<ChromeBookmark> chromeBookmarks = gson.fromJson("[ {\r\n" + 
				"               \"date_added\": \"13006835118502428\",\r\n" + 
				"               \"id\": \"5\",\r\n" + 
				"               \"name\": \"Fundamentals of Predictive Text Mining (Texts in Computer Science): Sholom M. Weiss, Nitin Indurkhya, Tong Zhang: 9781447125655: Amazon.com: Books\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://www.amazon.com/Fundamentals-Predictive-Mining-Computer-Science/dp/1447125657/ref=sr_1_1?ie=UTF8&qid=1362361494&sr=8-1&keywords=predictive+text+mining\"\r\n" + 
				"            }, {\r\n" + 
				"               \"date_added\": \"13007628170393986\",\r\n" + 
				"               \"id\": \"11\",\r\n" + 
				"               \"name\": \"Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://www.amazon.com/\"\r\n" + 
				"            }, {\r\n" + 
				"               \"date_added\": \"13008452111688181\",\r\n" + 
				"               \"id\": \"23\",\r\n" + 
				"               \"name\": \"Travel Wallet - Slim Leather Wallets by Bellroy\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://us.bellroy.com/products/travel-wallet#video\"\r\n" + 
				"            }, {\r\n" + 
				"               \"date_added\": \"13008465129984386\",\r\n" + 
				"               \"id\": \"38\",\r\n" + 
				"               \"name\": \"DSLR: Digital SLR Cameras - Free Shipping - Best Buy\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://www.bestbuy.com/site/Digital-Cameras/Digital-SLR-Cameras/abcat0401005.c?id=abcat0401005\"\r\n" + 
				"            }, {\r\n" + 
				"               \"date_added\": \"13008465207017665\",\r\n" + 
				"               \"id\": \"40\",\r\n" + 
				"               \"name\": \"Samsung Series 7 Chronos trickles into US stores\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://www.engadget.com/2013/02/22/samsung-series-7-chronos-on-sale/\"\r\n" + 
				"            }, {\r\n" + 
				"               \"date_added\": \"13008466026630990\",\r\n" + 
				"               \"id\": \"56\",\r\n" + 
				"               \"name\": \"Chaps Dress Shoes - Men\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://www.kohls.com/upgrade/webstore/product_page.jsp?PRODUCT%3C%3Eprd_id=845524892922021&mr:referralID=674a1d76-9342-11e2-9389-001b2166c62d\"\r\n" + 
				"            }, {\r\n" + 
				"               \"date_added\": \"13008469242946885\",\r\n" + 
				"               \"id\": \"72\",\r\n" + 
				"               \"name\": \"Dell Studio 14 (1458) and Studio 14z Laptop Details | Dell\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://www.dell.com/us/p/studio-14/pd\"\r\n" + 
				"            }, {\r\n" + 
				"               \"date_added\": \"13008469306209317\",\r\n" + 
				"               \"id\": \"74\",\r\n" + 
				"               \"name\": \"Lenovo G585 Laptop | Lenovo (US)\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://shop.lenovo.com/betaweb/us/en/laptops/essential/g-series/g585/#customize\"\r\n" + 
				"            }, {\r\n" + 
				"               \"date_added\": \"13008469321456317\",\r\n" + 
				"               \"id\": \"75\",\r\n" + 
				"               \"name\": \"Electronics, Cars, Fashion, Collectibles, Coupons and More Online Shopping | eBay\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://www.ebay.com/\"\r\n" + 
				"            }, {\r\n" + 
				"               \"date_added\": \"13008469382711838\",\r\n" + 
				"               \"id\": \"76\",\r\n" + 
				"               \"name\": \"Best Laptops & Notebooks - Newegg.com\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://www.newegg.com/Laptops-Notebooks/SubCategory/ID-32?Tpk=laptops\"\r\n" + 
				"            }, {\r\n" + 
				"               \"date_added\": \"13008469510807183\",\r\n" + 
				"               \"id\": \"77\",\r\n" + 
				"               \"name\": \"Men Oxfords - Search Zappos.com\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://www.zappos.com/mens-shoes~ov#!/mens-oxfords-shoes~36\"\r\n" + 
				"            }, {\r\n" + 
				"               \"date_added\": \"13008469526659183\",\r\n" + 
				"               \"id\": \"78\",\r\n" + 
				"               \"name\": \"Deals & Bargains | Sales & Savings - Amazon.com\",\r\n" + 
				"               \"type\": \"url\",\r\n" + 
				"               \"url\": \"http://www.amazon.com/Deals/b?ie=UTF8&node=909656\"\r\n" + 
				"            } ]", chromeBookmarksListType);
		System.out.println(chromeBookmarks.size());
	}
}
