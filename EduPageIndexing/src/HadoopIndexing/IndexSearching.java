package HadoopIndexing;
import java.io.*;
import java.util.*;

public class IndexSearching {
	private String query;
	public IndexSearching(String query){
		this.query = query;
	}
	public List<Message>read(){
		List<Message>messages = new ArrayList<Message>();
		String input = "";
		try{
		    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/E:/in.json")));
		    while ((input = reader.readLine())!=null){
		    	String[]info = input.split("##");
		    	Message m = new Message(info[0], Integer.parseInt(info[1]), info[2], info[3]);
		    	//System.out.println(info[0]+info[1]+info[2]+info[3]);
		    	messages.add(m);
		    }
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return messages;
	}
	public List<Message>find(){
		List<Message>messages = read();
		List<Message>result = new ArrayList<>();
		for(Message m:messages){
			if(m.W().contains(query)){
			    result.add(m);
			}			
		}
		return result;
	}
	public LinkedHashMap<String, String> Ranking(){
		LinkedHashMap<String, String>map = new LinkedHashMap<>();
	    TreeMap<Float, List<Message>>ranking = new TreeMap<>();
	    List<Message>messages = find();
	    if(messages==null) return null;
	    for(Message m:messages){
	    	String name = m.N();
	    	float score = query.length()/name.length();
	    	if(ranking.containsKey(score)) ranking.get(score).add(m);
	    	else{
	    		List<Message>list = new ArrayList<>();
	    		list.add(m);
	    		ranking.put(score,list);
	    	}
	    }
	    while(ranking.lastKey()!=null){
	    	float key = ranking.firstKey();
	    	for(Message m:ranking.get(key)){
	    		String name = m.N();
	    		String path = m.P();
	    		map.put(name,path);
	    	}
	    	return map;
	    }
		return map;
	}
	public static void main(String args[]){
		IndexSearching searching = new IndexSearching("good");
		LinkedHashMap< String, String>map = searching.Ranking();
		for(String key:map.keySet()){
			System.out.println(key+map.get(key));
		}
	}
}

class Message{
	private int freq;
	private String name;
	private String path;
	private String word;
	public Message(String word,int freq,String name,String path){
		this.freq = freq;
		this.name = name;
		this.path = path;
		this.word = word;
	}
    public void setf(int n){
    	freq = n;
    }
    public String W(){
    	return word;
    }
	public int F(){
		return freq;
	}
	public String N(){
		return name;
	}
	public String P(){
		return path;
	}
}
