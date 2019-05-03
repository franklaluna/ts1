package zkxy.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import zkxy.Role;
import zkxy.model.Company;
import zkxy.model.Trunk;
import zkxy.model.User;
import zkxy.repository.CompanyRepository;
import zkxy.repository.TrunkRepository;

@RestController
@Api(value = "系统相关api", description = "系统相关api")
@RequestMapping("/system")
public class SystemController {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private TrunkRepository trunkRepository;

	@ApiOperation(value = "获取所有用户单位的单位信息列表", notes = "获取所有用户单位的单位信息列表")
	@RequestMapping(value = "/companys", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getCompanys(HttpSession session) throws Throwable {

		User user = (User) session.getAttribute("user");
		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足~");
		}

		List<Company> companys = companyRepository.findAllCustom();

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", companys);
		return map;
	}

	
	@ApiOperation(value = "查询所有场内单位", notes = "查询所有场内单位")
	@RequestMapping(value = "/admin/companys", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getCompanyAdmin(HttpSession session) throws Throwable {

		User user = (User) session.getAttribute("user");
		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足~");
		}

		List<Company> companys = companyRepository.findAllAdmin();
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", companys);
		return map;
	}

	@ApiOperation(value = "获取所有排队车辆列表", notes = "获取所有排队车辆列表") 
	@RequestMapping(value = "/status/trunks", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getTrunksByLine()
			throws Throwable {
		List<Trunk> trunks = trunkRepository.findAll();
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", trunks);
		return map;
	}
//
//	@ApiOperation(value = "获取指定车辆状态", notes = "获取指定车辆状态") 
//	@RequestMapping(value = "/line/trunks", method = RequestMethod.GET)
//	@Role
//	public @ResponseBody Map<String, Object> getTrunksByLine()
//			throws Throwable {
//		List<Trunk> trunks = trunkRepository.findAll();
//		Map<String, Object> map = new HashMap<>();
//		map.put("success", true);
//		map.put("message", trunks);
//		return map;
//	}
//	
	
	@ApiOperation(value = "修改车辆状态", notes = "修改车辆状态")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "trunkId", dataType = "int", required = true, value = "车辆编号"),
			@ApiImplicitParam(paramType = "path", name = "statusKey", dataType = "String", required = true, value = "需排队:xpd;识别转载:sbzz;识别排队:sbpd"),
			@ApiImplicitParam(paramType = "path", name = "statusValue", required = true, dataType = "boolean", value = "0:否;1:是") })
	@RequestMapping(value = "/{trunkId}/{statusKey}/{statusValue}/status", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> setTrunkStatus(@PathVariable("trunkId") int trunkId,
			@PathVariable("statusKey") String statusKey, @PathVariable("statusValue") boolean statusValue)
			throws Throwable {

		Trunk trunk = trunkRepository.findById(trunkId);
		if (trunk == null) {
			throw new Exception("无效的车辆ID");
		}

		String keys[] = { "xpd", "sbzz", "sbpd" };

		if (!Arrays.asList(keys).contains(statusKey)) {
			throw new Exception("无效的statusKey");
		}

		switch (statusKey) {
		case "xpd":
			trunk.setXpd(statusValue);
			break;
		case "sbzz":
			trunk.setSbzz(statusValue);
			break;
		case "sbpd":
			trunk.setSbpd(statusValue);
			break;
		}

		trunkRepository.updateTrunkStatus(trunk);

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "设置成功!");
		return map;
	}

}
