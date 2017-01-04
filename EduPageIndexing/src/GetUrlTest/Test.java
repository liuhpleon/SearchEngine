package GetUrlTest;
import java.io.*;
import java.util.*;
public class Test {
    public HashMap<String, String> read(){
    	HashMap<String, String>map = new HashMap<>();
    	try{
    		File f = new File("E:/CS242Link/");
    		for(File file:f.listFiles()){
    			try{
    			    String n = file.getName(); 
    			    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    			    String url = reader.readLine();
    			    reader.close();
    			    n.trim();
    			    url.trim();
    			    //System.out.println(n+" "+url);
    			    map.put(n, url);
    			}catch(Exception e){
    				//System.out.println(e);
    			}
    		}
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
