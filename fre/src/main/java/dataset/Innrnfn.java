package dataset;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Innrnfn {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		TransportClient client = TransportClient.builder().build()
				.addTransportAddress(new 
						InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		
		ArrayList<parse_Law> lawData = new ArrayList<parse_Law>();
		
    	SearchResponse response = client.prepareSearch("gesetze")
    	        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
    	        .setQuery(QueryBuilders.matchAllQuery())                 // Query
    	        .execute()
    	        .actionGet();
    	System.out.println(response);
    	SearchHits sht = response.getHits();
       
        for (SearchHit hit : response.getHits()) {
        	
    		parse_Law  ld = new parse_Law(
					hit.getSource().get("doknr").toString(),
					hit.getSource().get("builddate").toString(),
					hit.getSource().get("norm").toString()
					);
			System.out.println(hit.getSource().get("doknr").toString());
    		lawData.add(ld);	
		
	}

}
}