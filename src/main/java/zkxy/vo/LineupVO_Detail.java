package zkxy.vo;
public class LineupVO_Detail implements Comparable<LineupVO_Detail>{
	private int pdslevel_id;
	private long num;
	private int lineup_status;
	private String statusName;
	public int getPdslevel_id() {
		return pdslevel_id;
	}
	public void setPdslevel_id(int pdslevel_id) {
		this.pdslevel_id = pdslevel_id;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public int getLineup_status() {
		return lineup_status;
	}
	public void setLineup_status(int lineup_status) {
		this.lineup_status = lineup_status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	@Override
	public int compareTo(LineupVO_Detail arg0) {
		 
		  return this.lineup_status==arg0.lineup_status?0:1;
	}
	
	 
}