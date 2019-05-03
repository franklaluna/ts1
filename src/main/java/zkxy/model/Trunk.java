package zkxy.model;
//货车的后箱信息
public class Trunk {
	private int ID;
	private String car_no;
	private String car_type;
	private double car_length;
	private int car_driver;
	private String d_no;
	private boolean xpd=true;
	private boolean sbzz=true;
	private boolean sbpd=true;
	

	public boolean isXpd() {
		return xpd;
	}

	public void setXpd(boolean xpd) {
		this.xpd = xpd;
	}

	public boolean isSbzz() {
		return sbzz;
	}

	public void setSbzz(boolean sbzz) {
		this.sbzz = sbzz;
	}

	public boolean isSbpd() {
		return sbpd;
	}

	public void setSbpd(boolean sbpd) {
		this.sbpd = sbpd;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	public double getCar_length() {
		return car_length;
	}

	public void setCar_length(double car_length) {
		this.car_length = car_length;
	}

	public int getCar_driver() {
		return car_driver;
	}

	public void setCar_driver(int car_driver) {
		this.car_driver = car_driver;
	}

	public String getD_no() {
		return d_no;
	}

	public void setD_no(String d_no) {
		this.d_no = d_no;
	}
}
