package com.masterthesis.ovgu;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import java.util.Date;

@ManagedBean(name = "lawBean", eager = true)
@ViewScoped
public class LawBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final boolean False = false;

	public ArrayList<ArrayList<result_law>> diLawresponse = new ArrayList<ArrayList<result_law>>();
	
	public ArrayList<result_law> dlResp = new ArrayList<result_law>();
	
	private BarChartModel barModel;
	public Integer bCount = 0; 
    
    public BarChartModel getBarModel() {
    	
        return barModel;
    }
 
	private Date startDate;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) throws ParseException {
		if (startDate != null)
		{
			this.startDate = startDate;
			System.out.println(startDate.toString() + "first");
		}
		else
		{
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
			this.startDate = df.parse("20120625153116");
		}
	}

	public Date getEndDate() {
		
		return endDate;
	}

	public void setEndDate(Date endDate) throws ParseException {
		
		if (endDate != null)
		{
			this.endDate = endDate;
			System.out.println(startDate.toString() + "frf");
			System.out.println(startDate.toString() + "sec");

		}
		else
		{
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
			this.endDate = df.parse("20120625153116");
		}
	}

	private Date endDate;
    
    public Integer getbCount() {
		return bCount;
	}

	public void setbCount(Integer bCount) {
		this.bCount = bCount;
	}


	public boolean disableFlag = False;
	public boolean disablePanelFlag = False;
	public boolean disablePanelFlagSet = False;

	public boolean isDisablePanelFlagSet() {
		return False;
	}


	public void setDisablePanelFlagSet(boolean disablePanelFlagSet) {
		this.disablePanelFlagSet = disablePanelFlagSet;
	}


	public ArrayList<result_law> getDlResp() {
		return dlResp;
	}

	public void setDlResp(ArrayList<result_law> dlResp) {
		this.dlResp = dlResp;
	}

	public ArrayList<ArrayList<result_law>> getDiLawresponse() {
		return diLawresponse;
	}

	public void setDiLawresponse(ArrayList<ArrayList<result_law>> diLawresponse) {
		this.diLawresponse = diLawresponse;
	}

	
	public String totalEntries;
	public String lawSearch;
	public String selectedDokNr;
	public ArrayList<parse_Law> queryLawresponse = new ArrayList<parse_Law>();
	public  String elapsedTime;
	public Integer withChanges = 0;
	public String currIndex;
	
	
	public String getCurrIndex() {
		return currIndex;
	}

	public void setCurrIndex(String currIndex) {
		this.currIndex = currIndex;
		//System.out.println(currIndex + "fg");
	}

	
	public  String nuberofChnages;
	
	public String getNuberofChnages() {
		return nuberofChnages;
	}

	public void setNuberofChnages(String nuberofChnages) {
		this.nuberofChnages = nuberofChnages;
	}

	public Integer getWithChanges() {
		return withChanges;
	}

	public void setWithChanges(Integer withChanges) {
		this.withChanges = withChanges;
	}

	public  String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime( String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getTotalEntries() {
		return totalEntries;
	}

	public void setTotalEntries(String totalEntries) {
		this.totalEntries = totalEntries;
	}


	public void dif(String te) {

	}

	public String getlawSearch() {
		return lawSearch;
	}

	public void setlawSearch(String lawSearch) {
		this.lawSearch = lawSearch;
	}

	public boolean isDisablePanelFlag() {
		return disablePanelFlag;
	}


	public void setDisablePanelFlag(boolean disablePanelFlag) {
		this.disablePanelFlag = disablePanelFlag;
	}


	public List<parse_Law> getqueryLawresponse() {
		return queryLawresponse;
	}


	public String getSelectedDokNr() {
		return selectedDokNr;
	}

	public void setSelectedDokNr(String selectedDokNr) {
		this.selectedDokNr = selectedDokNr;
	}
	public void getDiff(Integer re) throws IOException {
		System.out.println("Hello"+ re);
		
		dlResp = diLawresponse.get(re);
		System.out.println(dlResp);
    }

	public void executeQuery() throws IOException {

		
		if (bCount !=  0)
		{
	      FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove("lawBean");
	      executeQuery();
	      bCount = 0;
		}
		
		bCount = bCount + 1;
		
		long startTime = System.currentTimeMillis();

		TransportClient client = TransportClient.builder().build()
				.addTransportAddress(new 
						InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		System.out.println("start searching");

		
		QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("fplag", "n"))
				.must(QueryBuilders.wildcardQuery("norm", "*" + lawSearch.toLowerCase() + "*"));

		SearchResponse response = client.prepareSearch("abschluss").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(query) // Query
				.execute().actionGet();

		System.out.println(startDate + "ss");

		
		System.out.println(lawSearch);
		SearchHits sht = response.getHits();
		//System.out.println(sht);
		//System.out.println(sht.totalHits());
		totalEntries = "Total Number of Hits : " + new String (""+sht.totalHits());
		
		Integer i = 0;
		for (SearchHit hit : response.getHits()) {

			parse_Law ld = new parse_Law(hit.getSource().get("builddate").toString(), hit.getSource().get("fplag").toString(),
					hit.getSource().get("doknr").toString(),
					
					hit.getSource().get("norm").toString().substring(0, 50), "Test", "Test");
			//System.out.println(ld.getNorm());
			parse_Law ld2  = getrelatedDoknr(ld,ld.doknr);
			ld2.setCurInd(i.toString());
				queryLawresponse.add(ld2);
				i = i +1;
		}
	    long stopTime = System.currentTimeMillis();
	    
	    elapsedTime = "Total Time : " + new String (""+(stopTime - startTime)) + "ms" ;
	    nuberofChnages = "Number of Laws with Changes : " + withChanges;

	}

	public parse_Law getrelatedDoknr(parse_Law ld1, String doknmr) throws UnknownHostException
	{
		ArrayList<result_law> tl = new ArrayList<result_law>();
		
		
		
		TransportClient client = TransportClient.builder().build()
				.addTransportAddress(new 
						InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

		QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("doknr", doknmr))
				.must(QueryBuilders.matchQuery("fplag", "y"));

		SearchResponse response = client.prepareSearch("abschluss").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(query) // Query
				.execute().actionGet();
	    
		System.out.println(lawSearch);
		SearchHits sht = response.getHits();
		System.out.println(sht);
		System.out.println(sht.totalHits());
		int i = 0; 
		if (sht.totalHits() != 0)
		{
			BarChartModel barModel1 = new BarChartModel();
		for (SearchHit hit : response.getHits()) {
				
				String date_Year = hit.getSource().get("builddate").toString();
				String dff = hit.getSource().get("dffNumber").toString();
				ChartSeries s1 = new ChartSeries();
		       s1.setLabel(date_Year);
		        s1.set("", Integer.parseInt(dff));
		        barModel1.addSeries(s1);
			}
			
		barModel1.setTitle("Need to Change");
        barModel1.setLegendPosition("nw");
         
        Axis xAxis = barModel1.getAxis(AxisType.X);
        xAxis.setLabel("Previous Laws");
         
        Axis yAxis = barModel1.getAxis(AxisType.Y);
        yAxis.setLabel("Number of Differences");
        
			for (SearchHit hit : response.getHits()) {

				result_law rl = new result_law(hit.getSource().get("doknr").toString(),
						hit.getSource().get("builddate").toString(), hit.getSource().get("diffHtml").toString());

				tl.add(rl);
				i = i++;
				rl.setPerLwBarMD1(barModel1);
				}
			
	
			ld1.setPerLwBarMD(barModel);
			
			ld1.setChangesExists("This law has " +tl.size()+ " previous versions please click on show changes");
			ld1.setDisableFlag(false);
			ld1.setShowChnages("Show Changes");
			ld1.setResultLaw(tl);
			
			withChanges  = withChanges + 1;
			diLawresponse.add(tl);
			
	        
		
			return ld1;
		}
		else
		{
			result_law rl1 = new result_law("Test","Test","Test");
			tl.add(rl1);
			ld1.setChangesExists("This law does not contain any previous versions");
			ld1.setDisableFlag(true);
			ld1.setShowChnages(" ");
			ld1.setResultLaw(tl);
			diLawresponse.add(tl);
			return ld1;
		}
	
	}



}
