/*Author: Kevin Urban
 * Class: CS402
 * Program: SpiderWeb*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderWeb {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("You must give 3 arguments (root URL, max number reachable, and a timeout value)");
			System.exit(1);
		}
		int reach = 0;
		int timeout = 0;
		/*checks if the numbers given in the command line are actual integers
		 * and exits if they are not*/
		try {
			reach = Integer.parseInt(args[1]);
			timeout = Integer.parseInt(args[2]);
		} catch(NumberFormatException q) {
			System.out.println("You must give an integer number for the max reachable and timeout values");
			System.exit(1);
		}
		/*exits the program if the given numbers are not within specified limit*/
		System.out.println("The numbers given are: "+reach+" and "+timeout);
		if (reach < 1 || reach > 10000 || timeout < 1 || timeout > 10000) {
			System.out.println("Your values for max reachable and timeout must be between 1 and 10000");
			System.exit(1);
		}
		/*initializing a group of variables to be used */
		int cnt = 1; //count starts at 1 since we are putting the initial site inside of the array
		int crawlCount = 0;
		ArrayList<result> resList = new ArrayList<result>();
		ArrayList<String> grabList = new ArrayList<String>();
		Document doc;
		grabList.add(args[0]); //Adding the initial url to the list
		/*try catch loop creating for crawling through the given site*/
		try {
			while (cnt < reach) {
				doc = Jsoup.connect(grabList.get(crawlCount)).timeout(timeout).followRedirects(true).get();
				Elements anchors = doc.select("a[href]");
				/*loops over all found anchor tags and places them inside of the arrayList 
				 * if they aren't already there*/
				for (Element link : anchors) {
					/*checks if there is a fragment inside of an anchor and disregards if there is*/
					if (!link.attr("href").contains("#")) {
						String tmpStr = link.attr("href");
						/*checks if the link contains the https:// information and appends
						 * if not found according to the current link page*/
						if (!tmpStr.contains("https://") && !tmpStr.contains("http://")) {
							if (link.attr("href").charAt(0) != '/') {
								tmpStr = grabList.get(crawlCount)+"/"+tmpStr;
							} else {
								tmpStr = grabList.get(crawlCount)+tmpStr;
							}
						}
						if (!comparison(tmpStr, grabList)) {
							grabList.add(tmpStr);
							cnt++;
						}
					}
					if (!(cnt < reach)) {
						break;
					}
				}
				crawlCount++; //increment the crawl count once all tags are found
			}
		} catch (IOException e) {
			System.out.println("Could not reach site for crawling");
		}
		resList = orgList(grabList);
		printList(resList);
		
	}
	
	/*formats and prints out the array in the specified fashion*/
	private static void printList(ArrayList<result> resList) {
		for (int i = 0; i < resList.size(); i++) {
			if (resList.get(i).getQParam().equals("")) {
				System.out.println(resList.get(i).getLink());
			} else {
				System.out.println(resList.get(i).getLink());
				String[] tmpArr = createQueryArray(resList.get(i));
				for (int  j = 0; j < tmpArr.length; j++) {
					System.out.println("\t"+tmpArr[j].substring(0,tmpArr[j].indexOf("=")-1)+": "+tmpArr[j].substring(tmpArr[j].indexOf("=")+1));
				}
			}
		}
	}
	/*this method organizes the list of urls and then puts them into an array of the result object type
	 * and then returns*/
	private static ArrayList<result> orgList(ArrayList<String> grabList){
		grabList.sort(String::compareToIgnoreCase);
		ArrayList<result> resList = new ArrayList<result>();
		for (int i = 0; i < grabList.size(); i++) {
			if (grabList.get(i).contains("?")) {
				resList.add(new result(grabList.get(i).substring(0, grabList.get(i).indexOf("?")-1),grabList.get(i).substring(grabList.get(i).indexOf("?")+1)));
			} else {
				resList.add(new result(grabList.get(i), ""));
			}
		}
		return resList;
	}
	/*compares a possible string to every element in the list to see if it already exists inside of the list*/
	private static boolean comparison(String str, ArrayList<String> list) {
		for(String s : list) {
			if (s.toLowerCase().equals(str.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	/*checks the query parameters inside of a given result
	 * then creates a string array of queries in the url
	 * and returns the array to be printed.*/
	private static String[] createQueryArray(result res) {
		if (!res.getQParam().contains("&")) {
			String[] a = new String[1];
			a[0] = res.getQParam();
			return a;
		} else {
			String[] a = res.getQParam().split("&");
			Arrays.sort(a);
			return a;
		}
	}
}
