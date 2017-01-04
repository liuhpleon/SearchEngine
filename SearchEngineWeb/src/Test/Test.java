package Test;
import java.io.*;
import java.util.*;
public class Test {
    public HashMap<String, String> read(){
    	HashMap<String, String>map = new HashMap<>();
    	try{
    		File f = new File("E:/CS242Link/");
    		File f1 = new File("E:/link.txt");
    		OutputStream os1 = new FileOutputStream(f1);
    		BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(os1));
    		for(File file:f.listFiles()){
    			try{
    			    String n = file.getName(); 
    			    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    			    String url = reader.readLine();
    		    	reader.close();
    		    	n.trim();
    		    	n = n.substring(0, n.length()-4);
    		    	url.trim();
    		    	System.out.println(n+" "+url);
    		        writer1.write(n+"###"+url+"\r\n");
    			    map.put(n, url);
    			}catch(Exception e){
    				
    			}
    		}
    		writer1.close();
    	}catch(Exception e){

    	}
    	return map;
    } 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Test test = new Test();
        test.read();
	}

}
