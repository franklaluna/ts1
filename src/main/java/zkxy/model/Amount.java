package zkxy.model;

import java.util.Date;
//余额
public class Amount {

	private int ID ;//                  INT                  NOT NULL,
	private int   gou_id ;//              INT                  NOT NULL,
	private int    gong_id     ;//         INT                  NOT NULL,
	private String c_name;
	private String remark;
	public String getremark() {
		return remark;
	}
	public void setremark(String remark) {
		this.remark = remark;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	private double   c_balance ;//           DOUBLE               NOT NULL,
	private boolean   deleted  ;//            DOUBLE PRECISION     NOT NULL,
	private boolean   sync  ;//               REAL                 NOT NULL,
	 public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getGou_id() {
		return gou_id;
	}
	public void setGou_id(int gou_id) {
		this.gou_id = gou_id;
	}
	public int getGong_id() {
		return gong_id;
	}
	public void setGong_id(int gong_id) {
		this.gong_id = gong_id;
	}
	public double getC_balance() {
		return c_balance;
	}
	public void setC_balance(double c_balance) {
		this.c_balance = c_balance;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public boolean isSync() {
		return sync;
	}
	public void setSync(boolean sync) {
		this.sync = sync;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	private Date  update_time ;//         DATETIME             NULL,
}
