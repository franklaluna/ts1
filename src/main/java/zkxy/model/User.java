package zkxy.model;

public class User {
 
	private int UserID;     
	private String UserName;
	private String LoginPsd ;
	private String UserPhone ;
	private int Company;//company的id
	private String UserType;
	private boolean FirstLogin;
	private int ParentUser;
	private int car_id;
	public int getCar_id() {
		return car_id;
	}
	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}
	private String car_no;
	
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public boolean isFirstLogin() {
		return FirstLogin;
	}
	public void setFirstLogin(boolean firstLogin) {
		FirstLogin = firstLogin;
	}
	public void setCompany(int company) {
		Company = company;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getLoginPsd() {
		return LoginPsd;
	}
	public void setLoginPsd(String loginPsd) {
		LoginPsd = loginPsd;
	}
	public String getUserPhone() {
		return UserPhone;
	}
	public void setUserPhone(String userPhone) {
		UserPhone = userPhone;
	}
	public int getCompany() {
		return Company;
	}
	public void setCompanys(int company) {
		Company = company;
	}
	public String getUserType() {
		return UserType;
	}
	public void setUserType(String userType) {
		UserType = userType;
	}
 
	public int getParentUser() {
		return ParentUser;
	}
	public void setParentUser(int parentUser) {
		ParentUser = parentUser;
	}


}
