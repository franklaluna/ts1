package zkxy.model;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 发货计划实体
 *
 * @author xqy251
 */

public class DeliveryPlan extends Model<DeliveryPlan> {

    // Name Code Data Type Length Precision Primary Foreign Key Mandatory
    // ID ID int TRUE FALSE TRUE
    // 客户单位ID gou_id int FALSE FALSE FALSE
    // 品种 products int FALSE FALSE FALSE
    // 计划类型 plan_type int FALSE FALSE FALSE
    // 计划天数 plan_day int FALSE FALSE FALSE
    // 日期起点 start_time datetime FALSE FALSE FALSE
    // 日期终点 end_time datetime FALSE FALSE FALSE
    // 计划数 plan_number int FALSE FALSE FALSE
    // 已装数量 yz_number int FALSE FALSE FALSE
    // 已过皮 gp_number int FALSE FALSE FALSE
    // 已排队 pd_number int FALSE FALSE FALSE
    // 操作员 operator varchar(32) 32 FALSE FALSE FALSE
    // 删除标志 deleted real FALSE FALSE FALSE
    // 同步标志 sync real FALSE FALSE FALSE


    private Integer id;

    private Integer gou_id;
    /**
     * 客户单位名称
     */

    private String gou_name;

    private Integer products;

    /**
     * 产品名称
     */

    private String products_name;

    private Integer plan_type;

    private Integer plan_day;

    private Date start_time;

    private Date end_time;

    private Integer plan_number;

    private Integer yz_number;

    private Integer gp_number;

    private Integer pd_number;

    private String operator;

    private boolean deleted;

    private boolean sync;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGou_id() {
        return gou_id;
    }

    public void setGou_id(Integer gou_id) {
        this.gou_id = gou_id;
    }

    public Integer getProducts() {
        return products;
    }

    public void setProducts(Integer products) {
        this.products = products;
    }

    public Integer getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(Integer plan_type) {
        this.plan_type = plan_type;
    }

    public Integer getPlan_day() {
        return plan_day;
    }

    public void setPlan_day(Integer plan_day) {
        this.plan_day = plan_day;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Integer getPlan_number() {
        return plan_number;
    }

    public void setPlan_number(Integer plan_number) {
        this.plan_number = plan_number;
    }

    public Integer getYz_number() {
        return yz_number;
    }

    public void setYz_number(Integer yz_number) {
        this.yz_number = yz_number;
    }

    public Integer getGp_number() {
        return gp_number;
    }

    public void setGp_number(Integer gp_number) {
        this.gp_number = gp_number;
    }

    public Integer getPd_number() {
        return pd_number;
    }

    public void setPd_number(Integer pd_number) {
        this.pd_number = pd_number;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public String getGou_name() {
        return gou_name;
    }

    public void setGou_name(String gou_name) {
        this.gou_name = gou_name;
    }

    public String getProducts_name() {
        return products_name;
    }

    public void setProducts_name(String products_name) {
        this.products_name = products_name;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
