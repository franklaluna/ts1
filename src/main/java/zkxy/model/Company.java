package zkxy.model;

import java.util.Date;

public class Company {
	private int ID;
	private String c_name;
	private String c_code;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getC_code() {
		return c_code;
	}
	public void setC_code(String c_code) {
		this.c_code = c_code;
	}
	public String getC_alias() {
		return c_alias;
	}
	public void setC_alias(String c_alias) {
		this.c_alias = c_alias;
	}
	public String getC_type() {
		return c_type;
	}
	public void setC_type(String c_type) {
		this.c_type = c_type;
	}
	public String getC_contacts() {
		return c_contacts;
	}
	public void setC_contacts(String c_contacts) {
		this.c_contacts = c_contacts;
	}
	public String getC_memo() {
		return c_memo;
	}
	public void setC_memo(String c_memo) {
		this.c_memo = c_memo;
	}

	private String c_alias;
	private String c_type;
	private String c_contacts;
	private String c_memo;
	
	     
	private int parent_id  ;// int(11)       (NULL)           YES             (NULL)           select,insert,update,references           
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
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

	private boolean deleted ;//    tinyint(1)    (NULL)           YES             0                select,insert,update,references           
	private boolean  sync    ;//    tinyint(1)    (NULL)           YES             1                select,insert,update,references           
	
}
