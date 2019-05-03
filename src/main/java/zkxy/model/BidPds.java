package zkxy.model;
//竞标的产品
public class BidPds {

	private int   ID        ;//           int not null,
	private int  bid_id         ;//           int not null,
	private int  pds_id     ;//               int not null,
	private double  yield    ;//                 double not null,
	private double   price   ;//                  double not null,
	private String   remark   ;//                 varchar(255),
	private String p_type;
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
	public int getBid_id() {
		return bid_id;
	}
	public void setBid_id(int bid_id) {
		this.bid_id = bid_id;
	}
	public int getPds_id() {
		return pds_id;
	}
	public void setPds_id(int pds_id) {
		this.pds_id = pds_id;
	}
	public double getYield() {
		return yield;
	}
	public void setYield(double yield) {
		this.yield = yield;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
