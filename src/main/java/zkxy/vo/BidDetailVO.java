package zkxy.vo;

import java.util.Date;

public class BidDetailVO { 
	
	//private int   bid_id    ;          
	private int   Bid_pds_id    ;         
	private double   price  ;             
	private double   requirement  ;    
	private String   remark    ;
	public int getBid_pds_id() {
		return Bid_pds_id;
	}
	public void setBid_pds_id(int bid_pds_id) {
		Bid_pds_id = bid_pds_id;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
 
}
