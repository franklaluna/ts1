package zkxy.vo;

import java.util.Date;
import java.util.List;

import zkxy.model.BidPds;

public class BidDeployVO {
	 
	private String    bid_name  ;//           varchar(32) not null,
	private Date    bid_time  ;//           datetime not null, 
	private String    remark    ;//           varchar(255),
	
	List<BidPds> bidPdsInfos;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<BidPds> getBidPdsInfos() {
		return bidPdsInfos;
	}

	public void setBidPdsInfos(List<BidPds> bidPdsInfos) {
		this.bidPdsInfos = bidPdsInfos;
	}
	
}
