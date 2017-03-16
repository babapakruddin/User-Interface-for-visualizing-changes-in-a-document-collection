package dataset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

@ManagedBean(name = "lawBean", eager = true)
@RequestScoped

public class lawBean {
			
	public ArrayList<parse_Law> queryLawresponse = new ArrayList<parse_Law>();
	public String lawSearch;
		
		public String getlawSearch() {
	        return lawSearch;
	    }

	    public void setlawSearch(String lawSearch) {
	        this.lawSearch = lawSearch;
	    }
		  public List<parse_Law> getqueryLawresponse(){
		    	return queryLawresponse;
		    } 
	    
	    public void executeQuery() throws IOException{
	    	System.out.println("Test");
	    	TransportClient client = TransportClient.builder().build()
					.addTransportAddress(new 
							InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
			
				
	    	SearchResponse response = client.prepareSearch("gesetze")
	    	        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
	    	        .setQuery(QueryBuilders.matchAllQuery())                 // Query
	    	        .execute()
	    	        .actionGet();
	    	
	    	SearchHits sht = response.getHits();
	    	System.out.println(sht);
	        for (SearchHit hit : response.getHits()) {
	        	
	    		parse_Law  ld = new parse_Law(
						hit.getSource().get("doknr").toString(),
						hit.getSource().get("builddate").toString(),
						hit.getSource().get("norm").toString()
						);
	    		queryLawresponse.add(ld);	
			
		}

		     
	    }

}