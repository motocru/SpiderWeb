/*Author: Kevin Urban
 * Class: CS402
 * Program: SpiderWeb*/

public class result {
	/*creating variables to be used by the object when constructed*/
	private String link;
	private String qParam;
	
	/*constructor class*/
	result(String name, String query) {
		link = name;
		qParam = query;
	}
	
	/*returns the link and query parameters for later use when printing*/
	String getLink() {
		return link;
	}
	
	String getQParam() {
		return qParam;
	}
}
