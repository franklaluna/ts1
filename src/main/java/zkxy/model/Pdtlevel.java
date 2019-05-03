package zkxy.model;
//产品的细节
import java.util.Date;

public class Pdtlevel {

    private int ID;//    int(11)      (NULL)           NO      PRI     (NULL)           select,insert,update,references
    private String name;//   varchar(16)  utf8_general_ci  NO              (NULL)           select,insert,update,references
    private int pdt;// int(11)      (NULL)           NO              (NULL)           select,insert,update,references
    private double totle;//e    double       (NULL)           NO              (NULL)           select,insert,update,references
    private double surplus;//
    private int percentage;
    private String p_type;
    private String pdt_level;

    private double cur_hght;

    public double getCur_hght() {
        return cur_hght;
    }

    public void setCur_hght(double cur_hght) {
        this.cur_hght = cur_hght;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAlarm_ll() {
        return alarm_ll;
    }

    public void setAlarm_ll(double alarm_ll) {
        this.alarm_ll = alarm_ll;
    }

    public double getAlarm_l() {
        return alarm_l;
    }

    public void setAlarm_l(double alarm_l) {
        this.alarm_l = alarm_l;
    }

    public double getAlarm_h() {
        return alarm_h;
    }

    public void setAlarm_h(double alarm_h) {
        this.alarm_h = alarm_h;
    }

    public double getAlarm_hh() {
        return alarm_hh;
    }

    public void setAlarm_hh(double alarm_hh) {
        this.alarm_hh = alarm_hh;
    }

    public double getCur_wght() {
        return cur_wght;
    }

    public void setCur_wght(double cur_wght) {
        this.cur_wght = cur_wght;
    }

    private Date update_time;
    private String short_name;
    private int type;
    private double alarm_ll;
    private double alarm_l;
    private double alarm_h;
    private double alarm_hh;
    private double cur_wght;

    public String getPdt_level() {
        return pdt_level;
    }

    public void setPdt_level(String pdt_level) {
        this.pdt_level = pdt_level;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPdt() {
        return pdt;
    }

    public void setPdt(int pdt) {
        this.pdt = pdt;
    }

    public double getTotle() {
        return totle;
    }

    public void setTotle(double totle) {
        this.totle = totle;
    }

    public double getSurplus() {
        return surplus;
    }

    public void setSurplus(double surplus) {
        this.surplus = surplus;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
