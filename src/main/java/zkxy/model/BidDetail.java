package zkxy.model;

import java.util.Date;
//竞价细节
public class BidDetail {

	private int ID    ;//            
	private int   UserID     ;          
	private int  bid_pds_id         ;//                

	private int   pds_id    ;      
	private String p_type;
	
	private int bid_id;
	private String bid_name;
	private String c_name;
	
	public int getBid_id() {
		return bid_id;
	}
	public void setBid_id(int bid_id) {
		this.bid_id = bid_id;
	}
	public String getBid_name() {
		return bid_name;
	}
	public void setBid_name(String bid_name) {
		this.bid_name = bid_name;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getP_type() {
		return p_type;
	}
	public void setP_type(String p_type) {
		this.p_type = p_type;
	}
	private double   price  ;             
	private double   requirement  ;         
	private Date   create_time  ;       
	private String   remark    ;
	private int    bid_status     ;//          int not null,
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
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public int getBid_pds_id() {
		return bid_pds_id;
	}
	public void setBid_pds_id(int bid_pds_id) {
		this.bid_pds_id = bid_pds_id;
	}
	public int getPds_id() {
		return pds_id;
	}
	public void setPds_id(int pds_id) {
		this.pds_id = pds_id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getRequirement() {
		return requirement;
	}
	public void setRequirement(double requirement) {
		this.requirement = requirement;
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
