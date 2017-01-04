import java.awt.print.Printable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class GetEachUrl implements Runnable{
	private String url;
	private String name;
	public GetEachUrl(String url){
		this.url = url;
	}
	//crawling each .edu page, get all the valid links from that page
	// I use hash set to avoid duplicate 
	// and I set up a limit of 5000 pages of each .edu website inorder to control 
    public static HashMap<String,String> getUrl(String url,String name){
    	HashMap<String,String>set = new HashMap<>();
    	Queue<String>queue = new LinkedList<>();
    	set.put(url,modifyName(url));
    	System.out.println(modifyName(url));
    	System.out.println("---------------");
    	queue.add(url);
    	int i = 0;
    	while(queue.size()>0){
			String cur = queue.poll();
			Document doc = null;
			try{
			    doc = Jsoup.connect(cur).get();
			    Thread.sleep(10);
			}
			catch(Exception e){
				System.out.println("failed to connect, move to next");
			}
			if(doc==null) continue;
			else{
				Elements links = null;
				try{
		        links = doc.select("body").get(0).getElementsByTag("a");
				}catch (Exception e){
					System.out.println("failed to load page, move to next");
				}
				if(links==null) break;
				if(links!=null){
			        for(Element link:links){
		                String linkHref = link.attr("href");
		                String linkName = modifyName(url)+" "+link.text();
		                if(!set.containsKey(linkHref)){
				            queue.add(linkHref);  
				            set.put(linkHref,linkName);
				            i++;
				            System.out.println(i+"->"+linkName);

		                }
		                if(set.size()>1000) break;
			        }
			        if(set.size()>1000) break;
				}
			}
		}
    	return set;
    }
    // reWrite run method 
	public void run() {
		HashMap<String,String>queue = getUrl(url,name);
		for(String cururl:queue.keySet()){
            String name = queue.get(cururl);
            try{
                Download download = new Download(cururl,name);
                download.core();
                Thread.sleep(500);
            }catch (Exception e) {
				System.out.println("next");;
			}
	    }	
	}
	// check if this is an vaild page
	private static boolean isVaild(String address){
		if(address==null||address.length()<7) return false;
		boolean b1 = address.substring(0,7).equals("http://");
	    boolean b2 = address.length()>7;
	    return b1&&b2;
	}
	//modify the url by name rule, so that we can save it by using it's url	
	private static String modifyName(String address){
		if(address.length()-5>=11){
		    address = address.substring(11,address.length()-5);
		}
		return address;
	}
	private static String check(String address){
		char rep = '.';
		char s1 = '\\';
		char s2 = '/';
		char s3 = ':';
		char s4 = '*';
		char s5 = '?';
		char s6 = '<';
		char s7 = '>';
		char s8 = '|';
		HashSet<Character>set = new HashSet<>();
		for(int i=1;i<=8;i++){
			set.add(s1);
			set.add(s2);
			set.add(s3);
			set.add(s4);
			set.add(s5);
			set.add(s6);
			set.add(s7);
			set.add(s8);
		}
		String res = "";
		for(int i=0;i<Math.min(address.length(),150);i++){
			if(!set.contains(address.charAt(i))) res = res+address.charAt(i);
		}
		return res;
	}
	// test 1 thread is working? 2.the modify address works?
    public static void main(String args[]){
    	//Thread t = new Thread(new GetEachUrl("http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=202909&extra=page%3D1%26filter%3Dsortid%26sortid%3D192%26searchoption%5B3090%5D%5Bvalue%5D%3D2%26searchoption%5B3090%5D%5Btype%5D%3Dradio%26sortid%3D192"));
    	//t.start();
    	String address = "http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=202909&extra=page%3D1%26filter%3Dsortid%26sortid%3D192%26searchoption%5B3090%5D%5Bvalue%5D%3D2%26searchoption%5B3090%5D%5Btype%5D%3Dradio%26sortid%3D192";
    	GetEachUrl url = new GetEachUrl("hello");
    	url.getUrl(address, "hello");
    	//System.out.print(url.modifyName(address));
    }
}