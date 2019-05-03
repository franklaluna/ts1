package zkxy.model;

import java.util.Date;

/**
 * 排队
 * @author pc
 *
 */
public class TrunkLineup {
	private int ID;
	private int trunk_id;
	private int pdslevel_id;
	private String zzd_name;
	private int lineup_status;
	private Date updatetime;
	private String remark;
	private Date line_time;
	private Date no_time;
	private boolean deleted;
	private boolean sync;
	private int bd_company;
	private String bd_company_name;
	private int line_no;
	private int pds_id; 
	private String p_type;
	
	
	 
	
	public String getZzd_name() {
		return zzd_name;
	}
	public void setZzd_name(String zzd_name) {
		this.zzd_name = zzd_name;
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
	public int getBd_company() {
		return bd_company;
	}
	public void setBd_company(int bd_company) {
		this.bd_company = bd_company;
	}
	public String getBd_company_name() {
		return bd_company_name;
	}
	public void setBd_company_name(String bd_company_name) {
		this.bd_company_name = bd_company_name;
	}
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
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getTrunk_id() {
		return trunk_id;
	}
	public void setTrunk_id(int trunk_id) {
		this.trunk_id = trunk_id;
	}
	public int getPdslevel_id() {
		return pdslevel_id;
	}
	public void setPdslevel_id(int pdslevel_id) {
		this.pdslevel_id = pdslevel_id;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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