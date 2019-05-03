package zkxy.vo;

import java.util.Date;

import zkxy.model.Pdtlevel;
import zkxy.model.Trunk;
import zkxy.model.TrunkLineup;

public class LineupSingleVO {
	//当前时间
	private Date nowTime; 
	//排队序号
	private int lineupNo;
	//状态名称
	private String statusName;  
	//车牌号
	private String car_no;
	//料位名称
	private String zzd_name;
	//产品名称
	private String pdsName;
	//状态值
	private int lineup_status;
	//状态更新时间
	private Date updatetime; 
	//入队时间
	private Date line_time;
	//叫号时间
	private Date no_time;
	
	private String company_name;
	

	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getLineup_status_name() {
		return lineup_status_name;
	}
	public void setLineup_status_name(String lineup_status_name) {
		this.lineup_status_name = lineup_status_name;
	}
 
	private String lineup_status_name;
 
	
	private int line_no;
	private int pds_id; 
	private String p_type;
	
	public int getLine_no() {
		return line_no;
	}
	public void setLine_no(int line_no) {
		this.line_no = line_no;
	}
	public int getPds_id() {
		return pds_id;
	}
	public void setPds_id(int pds_id) {
		this.pds_id = pds_id;
	}
	public String getP_type() {
		return p_type;
	}
	public void setP_type(String p_type) {
		this.p_type = p_type;
	}
	public Date getNowTime() {
		return nowTime;
	}
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}
	public int getLineupNo() {
		return lineupNo;
	}
	public void setLineupNo(int lineupNo) {
		this.lineupNo = lineupNo;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public String getZzd_name() {
		return zzd_name;
	}
	public void setZzd_name(String zzd_name) {
		this.zzd_name = zzd_name;
	}
	public String getPdsName() {
		return pdsName;
	}
	public void setPdsName(String pdsName) {
		this.pdsName = pdsName;
	}
	public int getLineup_status() {
		return lineup_status;
	}
	public void setLineup_status(int lineup_status) {
		this.lineup_status = lineup_status;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Date getLine_time() {
		return line_time;
	}
	public void setLine_time(Date line_time) {
		this.line_time = line_time;
	}
	public Date getNo_time() {
		return no_time;
	}
	public void setNo_time(Date no_time) {
		this.no_time = no_time;
	}
	
	 
}
