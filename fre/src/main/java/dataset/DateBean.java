package dataset;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean (name = "dateBean", eager = true)
@SessionScoped
public class DateBean {
	  private Date startDate, endDate;

	  public Date getStartDate() {
	    return(startDate);
	  }

	  public void setStartDate(Date startDate) {
	    this.startDate = startDate;
	  }

	  public Date getEndDate() {
	    return(endDate);
	  }

	  public void setEndDate(Date endDate) {
	    this.endDate = endDate;
	  }
}