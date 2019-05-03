package zkxy.vo;

public class LoginUserVO {

	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Number getLoginPhone() {
		return loginPhone;
	}
	public void setLoginPhone(Number loginPhone) {
		this.loginPhone = loginPhone;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	private String loginName;
	private Number loginPhone;
	private String loginPassword;
	
}
