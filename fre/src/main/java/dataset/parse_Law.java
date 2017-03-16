package dataset;

public class parse_Law {
	public String doknr;
	public String builddate;

	public String norm;
	   
	parse_Law(String a,String b, String c)
	{
		 setDoknr(a);
		 setBuilddate(b);
		 setNorm(c);
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

}
