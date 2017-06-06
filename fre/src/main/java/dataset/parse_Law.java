package org.std.the.law.app;

import java.util.ArrayList;

import org.primefaces.model.chart.BarChartModel;

public class parse_Law {
	public String doknr;
	public String builddate;
	public String norm;
	private String fplag;
	private String standkommentar;
	private String ABWFORMAT;
	
	private String completeext;
	private String diffHtml;
	private String curInd;
	
	private String dffNumber;
	
	
	ArrayList<result_law> resultLaw = new ArrayList<result_law>();
	
	private BarChartModel perLwBarMD;
	
	public ArrayList<result_law> getResultLaw() {
		return resultLaw;
	}


	public void setResultLaw(ArrayList<result_law> resultLaw) {
		this.resultLaw = resultLaw;
	}


	public String getShowChnages() {
		return showChnages;
	}


	public void setShowChnages(String showChnages) {
		this.showChnages = showChnages;
	}


	private String changesExists;
	private String showChnages;
	
	private boolean disableFlag;
	
	public boolean isDisableFlag() {
		return disableFlag;
	}


	public void setDisableFlag(boolean disableFlag) {
		this.disableFlag = disableFlag;
	}


	parse_Law(String bDate, String fpF ,String dok, String nOR,  String st, String cpText)
	{
		 
		 setBuilddate(bDate);
		 setfplag(fpF);
		 setDoknr(dok);
		 
		 setNorm(nOR);
		 setStandkommentar(st);
		 setCompleteext(cpText);
	}


	public parse_Law() {
		// TODO Auto-generated constructor stub
	}


	public String getNorm() {
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
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



	public String getfplag() {
		return fplag;
	}


	public void setfplag(String fplag) {
		this.fplag = fplag;
	}


	public String getABWFORMAT() {
		return ABWFORMAT;
	}


	public void setABWFORMAT(String aBWFORMAT) {
		ABWFORMAT = aBWFORMAT;
	}


	public String getStandkommentar() {
		return standkommentar;
	}


	public void setStandkommentar(String standkommentar) {
		this.standkommentar = standkommentar;
	}


	public String getCompleteext() {
		return completeext;
	}


	public void setCompleteext(String completeext) {
		this.completeext = completeext;
	}


	public String getDiffHtml() {
		return diffHtml;
	}


	public void setDiffHtml(String diffHtml) {
		this.diffHtml = diffHtml;
	}


	public String getChangesExists() {
		return changesExists;
	}


	public void setChangesExists(String changesExists) {
		this.changesExists = changesExists;
	}


	public String getCurInd() {
		return curInd;
	}


	public void setCurInd(String curInd) {
		this.curInd = curInd;
	}


	public BarChartModel getPerLwBarMD() {
		return perLwBarMD;
	}


	public void setPerLwBarMD(BarChartModel perLwBarMD) {
		this.perLwBarMD = perLwBarMD;
	}


	public String getDffNumber() {
		return dffNumber;
	}


	public void setDffNumber(String dffNumber) {
		this.dffNumber = dffNumber;
	}




}
