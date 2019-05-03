package zkxy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import zkxy.Role;
import zkxy.model.Amount;
import zkxy.model.Company;
import zkxy.model.User;
import zkxy.repository.AmountRepository;
import zkxy.repository.CompanyRepository;
import zkxy.vo.AmountVO;
import zkxy.vo.BidDeployVO;

@RestController
@Api(value = "余额相关api", description = "余额相关api")
@RequestMapping("/Amount")
public class AmountController {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private AmountRepository amountRepository;

	
	@ApiOperation(value = "查询余额", notes = "查询余额")	
	@RequestMapping(value = "/remain", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> queryGongDw( HttpSession session) throws Throwable {
		User user = (User) session.getAttribute("user");
		List<Amount> zkxy_amounts;
		if ("系统用户".equals(user.getUserType())) {
			zkxy_amounts=amountRepository.findAmountByGongId(user.getCompany());
		}
		else if("客户单位".equals(user.getUserType())){
			zkxy_amounts=amountRepository.findAmountByGouId(user.getCompany());
		}
		else {
			throw new Exception("该用户无权查看余额信息");
		}
		 
		
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", zkxy_amounts);
		return map;
	}
	
 

	@ApiOperation(value = "给客户进行充值", notes = "给客户进行充值")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "gou_id", dataType = "int", required = true, value = "单位编号"),
			@ApiImplicitParam(paramType = "path", name = "amount", required = true, dataType = "double", value = "充值金额（元）") })
	@RequestMapping(value = "/{gou_id}/{amount}/recharge", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> recharge(@PathVariable("gou_id") int gou_id,
			@PathVariable("amount") double amount, HttpSession session) throws Throwable {

		User user = (User) session.getAttribute("user");
		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足~");
		}
		if (amount <= 0) {
			throw new Exception("充值金额应该大于0!");
		}
		
		
		Company companyGou = companyRepository.findById(gou_id);
		if (companyGou == null) {
			throw new Exception("无效的购货单位Id!");
		}
		
		//Company companyGong = companyRepository.findById(user.getCompany());

		
        Amount zkxy_amount=amountRepository.findAmountByGouIdAndGongId(gou_id, user.getCompany());
        
        if(zkxy_amount==null) 
        {
        	throw new Exception("当前单位无充值权限!");
        	
        	
        } 
        
        amountRepository.recharge(gou_id,user.getCompany(), amount,""); 

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "充值成功!");
		return map;
	}
	
	

	@ApiOperation(value = "给客户进行充值", notes = "给客户进行充值")
	@ApiImplicitParam(paramType = "body", name = "amountVO", dataType = "amountVO", required = true, value = "充值详情")
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	@Role
	public @ResponseBody Map<String, Object> rechargeWithCheck(@RequestBody AmountVO amountVO
			, HttpSession session) throws Throwable {

		User user = (User) session.getAttribute("user");
		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足~");
		}
		if (amountVO == null||amountVO.getAmount()<0||amountVO.getGou_id()==0) {
			throw new Exception("无效的充值信息！");
		}
		
		
		Company companyGou = companyRepository.findById(amountVO.getGou_id());
		if (companyGou == null) {
			throw new Exception("无效的购货单位Id!");
		}
		
		//Company companyGong = companyRepository.findById(user.getCompany());

		
        Amount zkxy_amount=amountRepository.findAmountByGouIdAndGongId(amountVO.getGou_id(), user.getCompany());
        
        if(zkxy_amount==null) 
        {
        	throw new Exception("当前单位无充值权限!"); 
        }
//        String fromDate = simpleFormat.format("2016-05-01 12:00");  
//        String toDate = simpleFormat.format("2016-06-01 12:00");  
//        long from = simpleFormat.parse(fromDate).getTime();  
//        long to = simpleFormat.parse(toDate).getTime();  
      //TODO 检查是否当天第二次充值。
    	//TODO 如果是第二次充值，检查上次备注跟本次备注是否一致。
    	//TODO 如果一致，给出错误提示
        double days = (zkxy_amount.getUpdate_time().getTime()-(new Date().getTime()))/(1000 * 60 * 60 * 24);  
        if(days<1) {
        	if(StringUtils.isEmpty(amountVO.getRemark())||amountVO.getRemark().equals(zkxy_amount.getremark()))  
        	{
        		throw new Exception("您所输入的款项，今日（或未入账的）的单据中已经有同金额的单据存在！"
        				+ "请检查是否重复。若确定入账，请备注不同的内容！"); 
        	}
        } 
        
        amountRepository.recharge(amountVO.getGou_id(),user.getCompany(), amountVO.getAmount(),amountVO.getRemark());  

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "充值成功!");
		return map;
	}
}
