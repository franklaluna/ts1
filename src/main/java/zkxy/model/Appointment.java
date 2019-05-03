package zkxy.model;

import java.util.Date;
//任命的工作
public class Appointment {

	private int ID;// int(11) (NULL) NO PRI (NULL) select,insert,update,references
	private int user_id;// int(11) (NULL) NO (NULL) select,insert,update,references
	private int products;// int(11) (NULL) NO (NULL) select,insert,update,references//产品的id
	private String p_type;
	private String company;

	private int a_count;// int(11) (NULL) NO (NULL) select,insert,update,references
	private Date a_datetime;// datetime (NULL) NO (NULL) select,insert,update,references
	private Date create_time;// datetime (NULL) NO (NULL) select,insert,update,references
	private int scs_num;

	public int getScs_num() {
		return scs_num;
	}

	public void setScs_num(int scs_num) {
		this.scs_num = scs_num;
	}

	public String getP_type() {
		return p_type;
	}

	public void setP_type(String p_type) {
		this.p_type = p_type;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getProducts() {
		return products;
	}

	public void setProducts(int products) {
		this.products = products;
	}

	public int getA_count() {
		return a_count;
	}

	public void setA_count(int a_count) {
		this.a_count = a_count;
	}

	public Date getA_datetime() {
		return a_datetime;
	}

	public void setA_datetime(Date a_datetime) {
		this.a_datetime = a_datetime;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getA_status() {
		return a_status;
	}

	public void setA_status(int a_status) {
		this.a_status = a_status;
	}

	public String getremark() {
		return remark;
	}

	public void setremark(String remark) {
		this.remark = remark;
	}

	private int a_status;// int(11) (NULL) NO (NULL) select,insert,update,references 0、申请成功、
	// 1、申请失败、
	// 2、预约中、
	// 3、请及时提货、
	// 4、超时。
	// 5、结束
	private String remark;// varchar(255) utf8_general_ci YES (NULL) select,insert,update,references
}
