import java.util.*;
import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.omg.PortableServer.ImplicitActivationPolicyOperations;
public class RunThread {
	// we get the edu pages from the seed and for each page we put it into a thread
	// and start that thread
    public void start(){
    	Seed seed = new Seed();
        ConcurrentLinkedQueue<String>queue = new ConcurrentLinkedQueue<>(new Seed().generateSeed());
        System.out.println(queue.size());
        while(queue.size()>0){
        	String str = queue.poll();
        	Thread thread = new Thread(new GetEachUrl(str));
        	thread.start();
        }
    }
    // Main for the whole project, start downloading page
    public static void main(String args[]){
    	try{
    		File file = new File("E:/CS242DATA");
    		if(!file.exists()) file.mkdirs();
    	}catch(Exception e){
    		System.out.println("failed build folder");
    	}
    	RunThread r = new RunThread();
    	r.start();
    }
}
