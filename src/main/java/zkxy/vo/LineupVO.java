package zkxy.vo;

import java.util.Date;
import java.util.List;

public class LineupVO {
	private Date updateTime;
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<LineupVO_Detail> getLineupVO_Details() {
		return lineupVO_Details;
	}

	public void setLineupVO_Details(List<LineupVO_Detail> lineupVO_Details) {
		this.lineupVO_Details = lineupVO_Details;
	}

	private List<LineupVO_Detail> lineupVO_Details;

	
}
