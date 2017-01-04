/*write by haopeng liu*/
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Test.ReadLink;

//import edu.ucr.cs242.runQuery.SearchLuceneIndex;

/**
 * Servlet implementation class HadoopServlet
 */
public class HadoopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HadoopServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String searchSentence = request.getParameter("query");
	        PrintWriter wr = response.getWriter();
	        searchSentence.trim();
	        if(searchSentence.length() == 0){
	        	wr.println("<title>you must input some words</title>");
	        	wr.println("<body>");
	        	wr.println("<center><h2>Invaild Input</h2></center>");
	            wr.println("<center><p><img src=\"C:/Users/Jessie/Desktop/searchEngineInterface/error.png\" alt=\"edu\" width=\"350\" height=\"350\"></p></center>");
	        	wr.println("</body>");
	        }
	        else{
	            HadoopSearch s = new HadoopSearch(searchSentence);
			    LinkedHashMap<String,String>map = s.Ranking();
	    	    if(map.size()==0){
	    	    	
	    	    	wr.println("<title>No Result for your search</title>");
	    	    	wr.println("<center><p><img src=\"C:/Users/Jessie/Desktop/searchEngineInterface/NoResult.jpg\" alt=\"edu\" width=\"915\" height=\"530\"></p></center>");
	    	    }
	    	    else{
	    	    	ReadLink read = new ReadLink();
	    	    	HashMap<String,String>links = read.read();
	    	    	System.out.println(links.size());
	    	    	wr.println("<p><img src= \"C:/Users/Jessie/Desktop/searchEngineInterface/edu.png\" alt=\"edu\" width=\"200\" height=\"110\"></p>");
	    	    	wr.print("<HR style='border:3 dashed #CCCCCC' width='100%' SIZE=3>");
	                wr.println("<h3>"+"Hadoop Search Result for "+searchSentence+":"+"</h3>");
	                int i = 1;
			        for(String name:map.keySet()) {
				        String path = map.get(name);
				        String url = links.get(name);
				        if(i%2==0)wr.println("<div style=\"background-color:#82FF82;padding:10px;margin-bottom:5px;width:80%;word-break:break-all\">");
				        else wr.println("<div style=\"background-color:#FFD382;padding:10px;margin-bottom:5px;width:80%;word-break:break-all\">");
				        if(links.containsKey(name)){
				        	url = links.get(name);
				        }
				        else url = "http://localhost:8080/";
				        wr.println("<p>"+"<A HREF= ");
				        wr.println(url);
				        wr.println(" target=\"_blank\" style=\"text-decoration:none\">"+"<font size = \"5\" color=\"black\">"+name+"</font>" +"</A>"+"</p>");
				        wr.print("<p>The link is: "+url+"</p>");
				        wr.println("<p>"+"File Located in: "+path+"</p>");
				        wr.println("</div>");
				        i++;
				        if(i>100) break;
			        }
	            }    
	        }
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}