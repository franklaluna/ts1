package zkxy.vo;

import java.math.BigDecimal;
import java.util.Date;

public class WeightSheetStatisticsVO {
	private int id;// int(11)

	private String order_Id;// varchar(16)
	private int card_id;// int(11)
	private String car_id;// varchar(16)
	private int product_id;// int(11)
	private String p_type;
	private BigDecimal mao_zhong;// double
	private BigDecimal pi_zhong;// double
	private BigDecimal jing_zhong;// double
	private BigDecimal price;// double
	private BigDecimal total;// double
	private double gong_id;// int(11)
	private double gou_id;// int(11)
	private Date huibang_time;// datetime
	private Date jinbang_time;// datetime
	private double fhl;
	private double ccl;
	

	public double getFhl() {
		return fhl;
	}

	public void setFhl(double fhl) {
		this.fhl = fhl;
	}

	public double getCcl() {
		return ccl;
	}

	public void setCcl(double ccl) {
		this.ccl = ccl;
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

	public BigDecimal getMao_zhong() {
		return mao_zhong;
	}

	public void setMao_zhong(BigDecimal mao_zhong) {
		this.mao_zhong = mao_zhong;
	}

	public BigDecimal getPi_zhong() {
		return pi_zhong;
	}

	public void setPi_zhong(BigDecimal pi_zhong) {
		this.pi_zhong = pi_zhong;
	}

	public BigDecimal getJing_zhong() {
		return jing_zhong;
	}

	public void setJing_zhong(BigDecimal jing_zhong) {
		this.jing_zhong = jing_zhong;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
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
