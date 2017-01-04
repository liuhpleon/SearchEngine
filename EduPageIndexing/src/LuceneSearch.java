/*write by haopeng liu*/
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.util.LinkedHashMap;

public class LuceneSearch {
	private Directory directory;
	private IndexReader reader;
	private String input;
	
	public LuceneSearch(String input){
		this.input = input;
	}
	
	public IndexSearcher init(){
		try{
	        directory = FSDirectory.open(new File("/E:/Indexing/index01"));
	        reader = IndexReader.open(directory);
	        IndexSearcher searcher = new IndexSearcher(reader);
	        return searcher;
		}catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
    public LinkedHashMap<String,String> searchByQueryParser(){
    	LinkedHashMap<String,String>output = new LinkedHashMap<String,String>();
    	try{
            IndexSearcher searcher = init();
    	    QueryParser parser = new QueryParser(Version.LUCENE_35, "name", new StandardAnalyzer(Version.LUCENE_35));
    	    parser.setAllowLeadingWildcard(true);
    	    Query query = parser.parse(input);
    	    ScoreDoc sd[] = searcher.search(query, 1000).scoreDocs;
    	    for(ScoreDoc d :sd){
    	    	Document doc = searcher.doc(d.doc);
    	    	String name = doc.get("name");
    	    	String path = doc.get("path");
    	    	output.put(name, path);
    		    System.out.println(doc.get("name")+" "+doc.get("path"));
    	    }
    	}catch (Exception e) {
    		System.out.println(e);
		}
    	return output;
    }
    
    public LinkedHashMap<String,String> searchByWildcard(String put){
    	LinkedHashMap<String,String>output = new LinkedHashMap<String,String>();
    	try{
    		IndexSearcher searcher = init();
    	    Query query = new WildcardQuery(new Term("name","*"+put+"*"));
    	    ScoreDoc sd[] = searcher.search(query, 1000).scoreDocs;
    	    for(ScoreDoc d :sd){
    	    	Document doc = searcher.doc(d.doc);
    	    	String name = doc.get("name");
    	    	String path = doc.get("path");
    		    System.out.println(doc.get("name")+" "+doc.get("path"));
    	    }
    	}catch(Exception e){
    		System.out.println(e);
    	}
    	return output;
    }
    
    public void close(){
    	try{
	        reader.close();
	        directory.close();
    	}catch (Exception e) {
			// TODO: handle exception
    		System.out.println(e);
		}
    }
    
    public static void main(String args[]){
    	String input = "ri";
    	int len = input.length();
    	LuceneSearch ls = new LuceneSearch(input);
        ls.searchByQueryParser();
        System.out.println("--");
        ls.searchByWildcard(input);
        System.out.println("--");
        ls.searchByWildcard(input.substring(0, len/2)+"*"+input.substring(len/2,len));
        ls.close();
    }
}