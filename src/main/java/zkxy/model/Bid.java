package zkxy.model;

import java.util.Date;
//竞标
public class Bid {
	private int  ID    ;//               int not null,
	private String    bid_name  ;//           varchar(32) not null,
	private Date    bid_time  ;//           datetime not null,

	private Date    create_time  ;//        datetime not null,
	private String    remark    ;//           varchar(255),
	private int bid_status;
	private String bid_dingbiap;
	public String getBid_dingbiap() {
		return bid_dingbiap;
	}
	public void setBid_dingbiap(String bid_dingbiap) {
		this.bid_dingbiap = bid_dingbiap;
	}
	public int getBid_status() {
		return bid_status;
	}
	public void setBid_status(int bid_status) {
		this.bid_status = bid_status;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getBid_name() {
		return bid_name;
	}
	public void setBid_name(String bid_name) {
		this.bid_name = bid_name;
	}
	public Date getBid_time() {
		return bid_time;
	}
	public void setBid_time(Date bid_time) {
		this.bid_time = bid_time;
	}
 
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	 

}
