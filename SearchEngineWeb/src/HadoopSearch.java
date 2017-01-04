import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
public class HadoopSearch {
	private String query;
	public HadoopSearch(String query){
		this.query = query;
	}
	public List<Message>read(){
		List<Message>messages = new ArrayList<Message>();
		String input = "";
		try{
		    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/E:/in.txt")));
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
	public List<List<Message>>find(){
		List<Message>messages = read();
		List<List<Message>>result = new ArrayList<>();
		List<Message>result1 = new ArrayList<>();
		List<Message>result2 = new ArrayList<>();
		for(Message m:messages){
			if(m.W().toLowerCase().equals(query.toLowerCase())){
				result1.add(m);
			}
			else{
			    if(m.W().toLowerCase().contains(query.toLowerCase())){
			        result2.add(m);
			    }	
			}
		}
		result.add(result1);
		result.add(result2);
		System.out.println(result1.size()+" "+result2.size());
		return result;
	}
	public LinkedHashMap<String, String> Ranking(){
	    List<List<Message>>messages = find();
        LinkedHashMap<String,String>result = new LinkedHashMap<>();
        result.putAll(TfIdf(messages.get(0)));
        result.putAll(TfIdf(messages.get(1)));
        return result;
	}
	public LinkedHashMap<String, String> TfIdf(List<Message>messages){
		TreeMap<Double, List<Message>>ranking = new TreeMap<>();
		LinkedHashMap<String, String>map = new LinkedHashMap<>();
	    if(messages==null) return null;
	    for(Message m:messages){
	    	String name = m.N();
	    	double tf = cal(name,query);
	    	double idf = m.N().split(" ").length;
	    	double score = tf/idf;
	    	if(ranking.containsKey(score)) ranking.get(score).add(m);
	    	else{
	    		List<Message>list = new ArrayList<>();
	    		list.add(m);
	    		ranking.put(score,list);
	    	}
	    }
	    
	    while(ranking.size()>0){
	    	double key = ranking.lastKey();
	    	for(Message m:ranking.get(key)){
	    		String name = m.N();
	    		String path = m.P();
	    		map.put(name,path);
	    	}
	    	ranking.remove(key);
	    }
		return map;
	}
	public int cal(String s,String sub){
		s = s.toLowerCase();
		sub = sub.toLowerCase();
	        Pattern pattern = Pattern.compile(sub);   
	        Matcher matcher = pattern.matcher(s);  
	        int count = 0;   
	        while (matcher.find()){ 
	            count++;  
	        }  
	        return  count;  
	}
	public static void main(String args[]){
		HadoopSearch searching = new HadoopSearch("ucr");
		LinkedHashMap< String, String>map = searching.Ranking();
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
