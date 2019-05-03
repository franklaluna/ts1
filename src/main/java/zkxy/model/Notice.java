package zkxy.model;

import java.util.Date;
//通知
public class Notice {

	private int ID;
	private String Title;
	private String Details;
	private String ShortDetails;
	private Date CreateTime;
	private String remark;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
	}

	public String getShortDetails() {
		return ShortDetails;
	}

	public void setShortDetails(String shortDetails) {
		ShortDetails = shortDetails;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public String getremark() {
		return remark;
	}

	public void setremark(String remark) {
		remark = remark;
	}

}
