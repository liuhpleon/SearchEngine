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
import org.apache.lucene.util.fst.BytesRefFSTEnum.InputOutput;
import java.io.*;
import java.util.LinkedHashMap;
public class LuceneSearch {
	private Directory directory;
	private IndexReader reader;
	private String input;
	
	public LuceneSearch(String input){
		this.input = input;
	}
	
	public IndexSearcher initsearch(){
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
            IndexSearcher searcher = initsearch();
    	    QueryParser parser = new QueryParser(Version.LUCENE_35, "name", new StandardAnalyzer(Version.LUCENE_35));
    	    parser.setAllowLeadingWildcard(true);
    	    Query query = parser.parse(input);
    	    ScoreDoc sd[] = searcher.search(query, 1000).scoreDocs;
    	    for(ScoreDoc d :sd){
    	    	Document doc = searcher.doc(d.doc);
    	    	String name = doc.get("name");
    	    	String path = doc.get("path");
    	    	output.put(name, path);
    	    }
    	}catch (Exception e) {
    		System.out.println(e);
		}
    	return output;
    }
    
    public LinkedHashMap<String,String> searchByWildcard(String put){
    	LinkedHashMap<String,String>output = new LinkedHashMap<String,String>();
    	try{
    		IndexSearcher searcher = initsearch();
    	    Query query = new WildcardQuery(new Term("name","*"+put+"*"));
    	    ScoreDoc sd[] = searcher.search(query, 400).scoreDocs;
    	    for(ScoreDoc d :sd){
    	    	Document doc = searcher.doc(d.doc);
    	    	String name = doc.get("name");
    	    	String path = doc.get("path");
    	    	output.put(name, path);
    	    }
    	}catch(Exception e){
    		System.out.println(e);
    	}
    	return output;
    }
    
    public LinkedHashMap<String, String>finalSearch(){
    	LinkedHashMap<String,String>map = new LinkedHashMap<>();
    	LinkedHashMap<String,String>map1 = searchByQueryParser();
    	int len = input.length();
    	map.putAll(map1);
    	if(map.size()>=100) return map;
    	LinkedHashMap<String,String>map2 = searchByWildcard(input);
    	map.putAll(map2);
    	if(map.size()>=100) return map;
    	LinkedHashMap<String,String>map3 = searchByWildcard(input.substring(0, len/2)+"*"+input.substring(len/2,len));
    	map.putAll(map3);
    	return map;
    }
    
    public void close(){
    	try{
	        reader.close();
	        directory.close();
    	}catch (Exception e) {
    		System.out.println(e);
		}
    }
    
    public static void main(String args[]){
    	String input = "ric";
    	int len = input.length();
    	LuceneSearch ls = new LuceneSearch(input);
        LinkedHashMap<String, String>map = ls.finalSearch();
        for(String key:map.keySet()){
        	System.out.println(key+" "+map.get(key));
        }
        ls.close();
    }
}