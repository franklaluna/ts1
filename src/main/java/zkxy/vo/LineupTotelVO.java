package zkxy.vo;

import java.util.Date;

import zkxy.model.Pdtlevel;
import zkxy.model.Trunk;
import zkxy.model.TrunkLineup;

public class LineupTotelVO {
	//当前时间
	private Date nowTime;  
	//料位名称
	private String zzd_name;
	//产品名称
	private String pdsName; 
	
	private int lineup_count;
	 
	public int getLineup_count() {
		return lineup_count;
	}
	public void setLineup_count(int lineup_count) {
		this.lineup_count = lineup_count;
	} 
	public Date getNowTime() {
		return nowTime;
	}
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
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
	 
}
