package zkxy.vo;

import java.util.Date;

public class AppointmentVO {
 
	private int products;// int(11) (NULL) NO (NULL) select,insert,update,references 
	private int a_count;// int(11) (NULL) NO (NULL) select,insert,update,references
	private Date a_datetime;// datetime (NULL) NO (NULL) select,insert,update,references
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
}
