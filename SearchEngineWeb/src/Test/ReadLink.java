package Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class ReadLink {
	 public HashMap<String, String> read(){
	    	HashMap<String, String>map = new HashMap<>();
	    	try{
	    		File f = new File("E:/link.txt");
	    		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	    		String info = "";
	    	    while((info=reader.readLine())!=null){
	    	    	try{
	    			    String name = info.split("###")[0];
	    			    String url = info.split("###")[1];
	    			    name.trim();
	    			    url.trim();
	    			    //System.out.println(name+" "+url);
	    			    map.put(name, url);
	    	    	}catch(Exception e){
	    	    		System.out.println(e);
	    	    	}
	    		}
	    	    reader.close();
	    	}catch(Exception e){
                System.out.println(e);
	    	}
	    	return map;
	    } 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ReadLink read = new ReadLink();
        read.read();
	}

}
