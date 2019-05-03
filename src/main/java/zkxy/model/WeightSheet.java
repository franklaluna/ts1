package zkxy.model;

import java.util.Date;
//货物清单
public class WeightSheet {
	private int id;// int(11)

	private String order_Id;// varchar(16)
	private int card_id;// int(11)
	private String car_id;// varchar(16)
	private int product_id;// int(11)
	private String p_type;
	private double mao_zhong;// double
	private double pi_zhong;// double
	private double jing_zhong;// double
	private double price;// double
	private double total;// double
	private double gong_id;// int(11)
	public String getGong_name() {
		return gong_name;
	}

	public void setGong_name(String gong_name) {
		this.gong_name = gong_name;
	}

	public String getGou_name() {
		return gou_name;
	}

	public void setGou_name(String gou_name) {
		this.gou_name = gou_name;
	}

	private String gong_name;
	private String gou_name;
	private double gou_id;// int(11)
	private Date huibang_time;// datetime
	private Date jinbang_time;// datetime

	private double y_price;
	private double y_total;
	private boolean delete;
	private boolean sync;
	private Date create_time;

	public double getY_price() {
		return y_price;
	}

	public void setY_price(double y_price) {
		this.y_price = y_price;
	}

	public double getY_total() {
		return y_total;
	}

	public void setY_total(double y_total) {
		this.y_total = y_total;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrder_Id() {
		return order_Id;
	}

	public void setOrder_Id(String order_Id) {
		this.order_Id = order_Id;
	}

	public int getCard_id() {
		return card_id;
	}

	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getP_type() {
		return p_type;
	}

	public void setP_type(String p_type) {
		this.p_type = p_type;
	}

	public double getMao_zhong() {
		return mao_zhong;
	}

	public void setMao_zhong(double mao_zhong) {
		this.mao_zhong = mao_zhong;
	}

	public double getPi_zhong() {
		return pi_zhong;
	}

	public void setPi_zhong(double pi_zhong) {
		this.pi_zhong = pi_zhong;
	}

	public double getJing_zhong() {
		return jing_zhong;
	}

	public void setJing_zhong(double jing_zhong) {
		this.jing_zhong = jing_zhong;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getGong_id() {
		return gong_id;
	}

	public void setGong_id(double gong_id) {
		this.gong_id = gong_id;
	}

	public double getGou_id() {
		return gou_id;
	}

	public void setGou_id(double gou_id) {
		this.gou_id = gou_id;
	}

	public Date getHuibang_time() {
		return huibang_time;
	}

	public void setHuibang_time(Date huibang_time) {
		this.huibang_time = huibang_time;
	}

	public Date getJinbang_time() {
		return jinbang_time;
	}

	public void setJinbang_time(Date jinbang_time) {
		this.jinbang_time = jinbang_time;
	}

}
