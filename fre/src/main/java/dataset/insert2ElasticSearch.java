package dataset;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;



public class insert2ElasticSearch {

	public String insertES(ArrayList<parse_Law> lawdata) throws JsonProcessingException, UnknownHostException
	{
		TransportClient client = TransportClient.builder().build()
					.addTransportAddress(new 
							InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		
		ObjectMapper mapper = new ObjectMapper();
		for (parse_Law lawData_1 : lawdata)
		{
			byte[] json = mapper.writeValueAsBytes(lawData_1);
			IndexResponse response = client.prepareIndex("gesetze", "lawdata")
			        .setSource(json)
			        .get();
		}
		
		client.close();
		return "Success";
	}
	
	
}
