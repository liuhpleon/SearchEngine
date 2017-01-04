import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
public class Seed{
	// get some seed as the init start 
	// the reason why I choose Bing because the page style is very friend and logic
	// each page has 14 result and since result 16 its very uniform 
	// so we start from 16 to get seed
	private String startUrl = "https://www.bing.com/search?q=.edu&go=Search&qs=bs&first=";
    public Queue<String> generateSeed(){
    	Queue<String>seed = new LinkedList<>();
    	HashSet<String>set = new HashSet<>();
    	for(int i=300;i<=400;i=i+14){  
            String currentUrl = startUrl+i;
            try{
                Document info = Jsoup.connect(currentUrl).get();
                Element doc = info.select("#b_results").get(0);
                int s = 1;
                for(Element page:doc.children()){
                    Element officalAddress = page.select("a").get(0);
            	    String address = officalAddress.attr("href");
            	    if(isVaild(address)&&!set.contains(address)){
            		        seed.add(address);
            		        set.add(address);
            	    }
            	    s++;
            	    if(s>14) break;
                }
                Thread.sleep(100);
            }catch(Exception e){
            	System.out.println("failed Seed");
            }
    	}
    	return seed;
    }
    // check if this is an .edu page
	public boolean isVaild(String address){
		if(address.length()<7) return false;
		boolean b1 = address.substring(address.length()-5,address.length()).equals(".edu/");
		boolean b2 = address.substring(0,7).equals("http://");
	    boolean b3 = address.length()>7;
	    return b1&&b2&&b3;
	}


}
