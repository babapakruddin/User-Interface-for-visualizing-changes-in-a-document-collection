package com.masterthesis.ovgu;

import org.primefaces.model.chart.BarChartModel;

public class ResultLaw {
	public String doknr;
	public String builddate;
	private String diffHtml;
	private BarChartModel perLwBarMD1;

	ResultLaw(String a,String b, String c)
	{
		 setDoknr(a);
		 setBuilddate(b);
		 setDiffHtml(c);
	}


	public ResultLaw() {
		// TODO Auto-generated constructor stub
	}


	

	public String getDoknr() {
		return doknr;
	}


	public void setDoknr(String doknr) {
		this.doknr = doknr;
	}


	public String getBuilddate() {
		return builddate;
	}


	public void setBuilddate(String builddate) {
		this.builddate = builddate;
	}



	public String getDiffHtml() {
		return diffHtml;
	}


	public void setDiffHtml(String diffHtml) {
		this.diffHtml = diffHtml;
	}


	public BarChartModel getPerLwBarMD1() {
		return perLwBarMD1;
	}


	public void setPerLwBarMD1(BarChartModel perLwBarMD1) {
		this.perLwBarMD1 = perLwBarMD1;
	}




}
