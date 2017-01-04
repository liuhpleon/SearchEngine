import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

import javax.net.ServerSocketFactory;

import org.w3c.dom.NameList;
public class preHadoop {
	public void write(){
    	try{
            TreeMap<String,List<Info>>map = new TreeMap<>();
    	    File f = new File("/E:/CS242DATA");
    	    OutputStream os = new FileOutputStream("/E:/in.txt");
    	    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
    	    Date date = new Date();
    	    int i = 1;
    	    for(File file:f.listFiles()){
    	    	try{
    	    	    String name = file.getName();
    	    	    if(name.length()>5)name = name.substring(0,name.length()-5);
    	    	    name.replaceAll(".", "");
    	    	    
    	    	    String path = file.getAbsolutePath();
    	    	    
    	    	    int freq = 0;
    	    	    
    	    	    StringTokenizer itr = new StringTokenizer(name);
    	    	    TreeMap<String, Integer>map1 = new TreeMap();
    	    	    while(itr.hasMoreTokens()){
    	    	    	String word = itr.nextToken();
    	    	    	System.out.println(word);
    	    	    	if(map1.containsKey(word)){
    	    	    		map1.put(word, map1.get(word)+1);
    	    	    	}
    	    	    	else map1.put(word, 1);
    	    	    }
    	    	    for(String key:map1.keySet()){
    	    	    	freq = map1.get(key);
    	    	    	Info info = new Info(freq, name, path);
    	    	    	List<Info>list = new ArrayList<>();
    	    	    	if(map.containsKey(key)) map.get(key).add(info);
    	    	    	else{
    	    	    		list.add(info);
    	    	    		map.put(key, list);
    	    	    	}
    	    	    }
    	    	}catch (Exception e) {
    	    		System.out.println(e);
				}
    	    	i++;
    	    }   	
    	    for(String key:map.keySet()){
    	    	List<Info>list = new ArrayList<>();
    	    	for(Info info:map.get(key)){
    	    		list.add(info);
    	            //String combine = "{"+"word:"+key+"\n"+",freq:"+info.F()+"\n"+",name:"+info.N()+"\n"+",path:"+info.P()+"\n"+"}"+"\r\n";
    	            String combine = key+"##"+info.F()+"##"+info.N()+"##"+info.P()+"\r\n";
    	            System.out.println(combine);
    	            writer.write(combine);
    	    	}

    	    }
    	    writer.close();
    	}catch(Exception e){
    		System.out.println(e);
    	}
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        preHadoop a = new preHadoop();
        try{
            a.write();
        }catch(Exception e){
        	System.out.println(e);
        }
	}
}

class Info{
	private int freq;
	private String name;
	private String path;
	public Info(int freq,String name,String path){
		this.freq = freq;
		this.name = name;
		this.path = path;
	}
    public void setf(int n){
    	freq = n;
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