/*write by haopeng liu*/
import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import java.util.*;
public class BuildIndex {
	public void Index(){
    	IndexWriter writer = null;
    	try{
    		Directory dict = FSDirectory.open(new File("/E:/Indexing/index01"));
        	IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
    	    writer = new IndexWriter(dict, config);
    	    Document doc = null;
    	    File f = new File("/E:/CS242DATA");
    	    Date date = new Date();
    	    System.out.println(date.toString());
    	    int i = 1;
    	    for(File file:f.listFiles()){
    	    	try{
    	    	    String name = file.getName();
    	    	    name = name.substring(0,name.length()-5);
    	    	    doc = new Document();
    	    	    doc.add(new Field("name", name,Store.YES,Index.ANALYZED));
    	    	    doc.add(new Field("path",file.getAbsolutePath(),Store.YES,Index.ANALYZED));
    	    	    System.out.println(i+"->"+doc.get("name")+"->"+doc.get("path"));
    	        	writer.addDocument(doc);
    	    	}catch (Exception e) {
    	    		System.out.println(e);
				}
    	    	i++;
    	    }   	
    	    System.out.print(date.toString());
    	    writer.close();
    	}catch(Exception e){
    		System.out.println(e);
    	}
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        BuildIndex a = new BuildIndex();
        try{
            a.Index();
        }catch(Exception e){
        	System.out.println(e);
        }
	}
}
