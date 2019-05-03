package zkxy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import zkxy.Role;
import zkxy.model.Pdtlevel;
import zkxy.repository.PdtLevelRepository;

@RestController
@Api(value = "料位信息api",description= "料位信息")
@RequestMapping("/PdtLevel")
public class PdtLevelController {

	@Autowired
	private PdtLevelRepository pdtLevelRepository;
	
	@ApiOperation(value = "查询料位", notes = "查询料位") 
	@RequestMapping(value = "/List", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getPdtLevel() throws Throwable {
		List<Pdtlevel> pdtlevels = pdtLevelRepository.findPdtlevel(); 
		//添加汇总的信息
		Pdtlevel Totlelevel=pdtLevelRepository.findAllPdtTotle();
		if(!CollectionUtils.isEmpty(pdtlevels) &&Totlelevel!=null) {
			Totlelevel.setName("总览");
			Totlelevel.setID(-1);
			pdtlevels.add(0, Totlelevel);
		} 
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", pdtlevels); 
		return map;
	}
 
	
}
