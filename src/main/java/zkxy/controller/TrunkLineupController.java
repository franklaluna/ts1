package zkxy.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import zkxy.Role;
import zkxy.model.Company;
import zkxy.model.Pdtlevel;
import zkxy.model.Trunk;
import zkxy.model.TrunkLineup;
import zkxy.model.User;
import zkxy.repository.CompanyRepository;
import zkxy.repository.PdtLevelRepository;
import zkxy.repository.ProductRepository;
import zkxy.repository.TrunkLineupRepository;
import zkxy.repository.TrunkRepository;
import zkxy.vo.LineupSingleVO;
import zkxy.vo.LineupTotelVO;
import zkxy.vo.LineupVO;
import zkxy.vo.LineupVO_Detail;

@RestController
@Api(value = "排队API", description = "排队API")
@RequestMapping("/TrunkLineup")
public class TrunkLineupController {

	@Autowired
	private TrunkLineupRepository trunkLineupRepository;

	@Autowired
	private TrunkRepository trunkRepository;

	@Autowired
	private PdtLevelRepository pdtLevelRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@ApiOperation(value = "通过选择料库编号，返回当前库中整体排队情况", notes = "通过选择料库编号，返回当前库中整体排队情况")
	@ApiImplicitParam(paramType = "path", name = "PsdLevelId", dataType = "int", required = true, value = "料位编号")
	@RequestMapping(value = "/{PsdLevelId}/StatusWithPsdLevelId", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getLineupDetailWithPsdLevelId(@PathVariable("PsdLevelId") int PsdLevelId,
			HttpSession session) throws Throwable {

		User user = (User) session.getAttribute("user");
		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足~");
		}

		Pdtlevel level = pdtLevelRepository.findByID(PsdLevelId);

		if (PsdLevelId != -1 && level == null) {
			throw new Exception("无效的料位编号");
		}

		List<LineupVO_Detail> vo_detail = null;//
		if (PsdLevelId == -1) {
			// 总览
			vo_detail = trunkLineupRepository.findAllStatus();
		} else {
			vo_detail = trunkLineupRepository.findStatusByPsdLevelId(PsdLevelId);
		}
		boolean dd = false, qj = false, yj = false, yb = false, zz = false, zw = false;
		if (!CollectionUtils.isEmpty(vo_detail)) {
			for (LineupVO_Detail detail : vo_detail) {
				switch (detail.getLineup_status()) {
				case 1:
					detail.setStatusName("等待");
					dd = true;
					break;
				case 2:
					detail.setStatusName("请进");
					qj = true;
					break;
				case 3:
					detail.setStatusName("已进");
					yj = true;
					break;
				case 4:
					detail.setStatusName("预备");
					yb = true;
					break;
				case 5:
					detail.setStatusName("正装");
					zz = true;
					break;
				case 6:
					detail.setStatusName("装完");
					zw = true;
					break;
				}
			}

		}

		if (!dd) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(1);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("等待");

			vo_detail.add(0, vo);
		}
		if (!qj) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(2);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("请进");

			vo_detail.add(1, vo);
		}
		if (!yj) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(3);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("已进");
			vo_detail.add(2, vo);
		}
		if (!yb) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(4);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("预备");
			vo_detail.add(3, vo);
		}
		if (!zz) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(5);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("正装");
			vo_detail.add(4, vo);
		}
		if (!zw) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(6);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("装完");
			vo_detail.add(5, vo);
		}

		Collections.sort(vo_detail);

		LineupVO lineupVO = new LineupVO();
		lineupVO.setLineupVO_Details(vo_detail);
		lineupVO.setUpdateTime(new Date());

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", lineupVO);
		return map;
	}

	@ApiOperation(value = "客户单位查看自己公司排队整体情况", notes = "客户单位查看自己公司排队整体情况")
	@RequestMapping(value = "/StatusWithPsdLevelId", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getLineupDetailWithCompany(HttpSession session) throws Throwable {

		User user = (User) session.getAttribute("user");
		if (!"客户单位".equals(user.getUserType())) {
			throw new Exception("权限不足~");
		}

		List<LineupVO_Detail> vo_detail = null;//

		vo_detail = trunkLineupRepository.findAllStatusWithCompany(user.getCompany());

		boolean dd = false, qj = false, yj = false, yb = false, zz = false, zw = false;
		if (!CollectionUtils.isEmpty(vo_detail)) {
			for (LineupVO_Detail detail : vo_detail) {
				switch (detail.getLineup_status()) {
				case 1:
					detail.setStatusName("等待");
					dd = true;
					break;
				case 2:
					detail.setStatusName("请进");
					qj = true;
					break;
				case 3:
					detail.setStatusName("已进");
					yj = true;
					break;
				case 4:
					detail.setStatusName("预备");
					yb = true;
					break;
				case 5:
					detail.setStatusName("正装");
					zz = true;
					break;
				case 6:
					detail.setStatusName("装完");
					zw = true;
					break;
				}
			}

		}

		if (!dd) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(1);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("等待");

			vo_detail.add(0, vo);
		}
		if (!qj) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(2);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("请进");

			vo_detail.add(1, vo);
		}
		if (!yj) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(3);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("已进");
			vo_detail.add(2, vo);
		}
		if (!yb) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(4);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("预备");
			vo_detail.add(3, vo);
		}
		if (!zz) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(5);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("正装");
			vo_detail.add(4, vo);
		}
		if (!zw) {
			LineupVO_Detail vo = new LineupVO_Detail();
			vo.setLineup_status(6);
			vo.setNum(0);
			vo.setPdslevel_id(-1);
			vo.setStatusName("装完");
			vo_detail.add(5, vo);
		}

		Collections.sort(vo_detail);

		LineupVO lineupVO = new LineupVO();
		lineupVO.setLineupVO_Details(vo_detail);
		lineupVO.setUpdateTime(new Date());

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", lineupVO);
		return map;
	}

	@ApiOperation(value = "获取所有的排队情况", notes = "获取整个厂区排队情况")
	@RequestMapping(value = "/TotleLineupCount", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getTotoleLineupDetail(HttpSession session) throws Throwable {

		User user = (User) session.getAttribute("user");
		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足~");
		}
		List<LineupTotelVO> vo_detail = trunkLineupRepository.findAllLineupCount();
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", vo_detail);
		return map;
	}

	@ApiOperation(value = "获取指定车辆的排队情况", notes = "获取指定车辆的排队情况")
	@ApiImplicitParam(paramType = "path", name = "trunkId", dataType = "int", required = true, value = "车辆编号")
	@RequestMapping(value = "/{trunkId}/LineupInfo", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getLineupByCarId(@PathVariable("trunkId") int trunkId, HttpSession session)
			throws Throwable {

		User user = (User) session.getAttribute("user");
		// if (!"系统用户".equals(user.getUserType())) {
		// throw new Exception("权限不足~");
		// }

		Trunk trunk = trunkRepository.findById(trunkId);
		if (trunk == null) {
			throw new Exception("无效车辆编号");
		}

		int lineNo = 0;
		TrunkLineup trunkLineup = trunkLineupRepository.findByTrunkId(trunkId);
		if (trunkLineup == null) {
			throw new Exception("未查询到该车排队信息");
		}
		// lineNo=trunkLineupRepository.findNOByStatusAndTimes(trunkLineup.getLineup_status(),
		// trunkLineup.getUpdatetime());

		// Pdtlevel pdtlevel= pdtLevelRepository.findByID(trunkLineup.getPdslevel_id());

		// Product product= productRepository.findProductById(pdtlevel.getPdt());

		LineupSingleVO vo = new LineupSingleVO();
		// vo.setLineupNo(lineNo);
		vo.setCar_no(trunk.getCar_no());
		vo.setLine_time(trunkLineup.getLine_time());
		vo.setLineup_status(trunkLineup.getLineup_status());
		vo.setLineupNo(trunkLineup.getLine_no());
		vo.setNo_time(trunkLineup.getNo_time());
		vo.setNowTime(new Date());
		// vo.setPdslevel_name(pdtlevel.getName());
		// vo.setPdsName(product.getP_type());
		vo.setUpdatetime(trunkLineup.getUpdatetime());
		vo.setLine_no(trunkLineup.getLine_no());
		vo.setPds_id(trunkLineup.getPds_id());
		vo.setP_type(trunkLineup.getP_type());
		vo.setZzd_name(trunkLineup.getZzd_name());

		Company company = companyRepository.findById(trunkLineup.getBd_company());
		if (company != null) {
			vo.setCompany_name(company.getC_name());
		}

		switch (trunkLineup.getLineup_status()) {
		case 1:
			vo.setStatusName("等待");
			break;
		case 2:
			vo.setStatusName("请进");
			break;
		case 3:
			vo.setStatusName("已进");
			break;
		case 4:
			vo.setStatusName("预备");
			break;
		case 5:
			vo.setStatusName("正装");
			break;
		case 6:
			vo.setStatusName("装完");
			break;
		}

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", vo);
		return map;
	}

	@ApiOperation(value = "查询当前用户关联车辆的列表", notes = "查询当前用户关联车辆的列表")
	@RequestMapping(value = "/trunks", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getLineupTrunksByUserId(HttpSession session) throws Throwable {
		User user = (User) session.getAttribute("user");

		List<Trunk> trunks = new ArrayList<Trunk>();
		if ("系统用户".equals(user.getUserType())) {
			trunks = trunkLineupRepository.findAllLineupTrunks();
		}

		else if ("司机".equals(user.getUserType())) {

			trunks.add(trunkRepository.findByUser(user.getUserID()));
		} else {
			trunks = trunkLineupRepository.findLineupTrunksByCompany(user.getCompany());
		}

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", trunks);
		return map;
	}

	@ApiOperation(value = "用户关联所有车辆排队情况", notes = "客户单位所有车辆排队情况")
	@RequestMapping(value = "/LineupInfo", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getLineupByUserId(HttpSession session) throws Throwable {

		User user = (User) session.getAttribute("user");
		// if (!"系统用户".equals(user.getUserType())) {
		// throw new Exception("权限不足~");
		// }

		List<Trunk> trunks = new ArrayList<Trunk>();
		if ("系统用户".equals(user.getUserType())) {
			throw new Exception("系统用户无法查询列表");
		}

		else if ("司机".equals(user.getUserType())) {

			trunks.add(trunkRepository.findByUser(user.getUserID()));
		} else {
			trunks = trunkLineupRepository.findLineupTrunksByCompany(user.getCompany());
		}

		List<LineupSingleVO> vos = new ArrayList<LineupSingleVO>();
		for (Trunk trunk : trunks) {
			int lineNo = 0;
			TrunkLineup trunkLineup = trunkLineupRepository.findByTrunkId(trunk.getID());
			if (trunkLineup == null) {
				// throw new Exception("未查询到该车排队信息");
				continue;
			}
			// lineNo=trunkLineupRepository.findNOByStatusAndTimes(trunkLineup.getLineup_status(),
			// trunkLineup.getUpdatetime());

			// Pdtlevel pdtlevel= pdtLevelRepository.findByID(trunkLineup.getPdslevel_id());
			//
			// Product product= productRepository.findProductById(pdtlevel.getPdt());
 
			LineupSingleVO vo = new LineupSingleVO();
			vo.setLineupNo(lineNo);
			vo.setCar_no(trunk.getCar_no());
			vo.setLine_time(trunkLineup.getLine_time());
			vo.setLineup_status(trunkLineup.getLineup_status());
			vo.setLineupNo(lineNo);
			vo.setNo_time(trunkLineup.getNo_time());
			vo.setNowTime(new Date());
			// vo.setPdslevel_name(pdtlevel.getName());
			vo.setPdsName(trunkLineup.getP_type());
			vo.setUpdatetime(trunkLineup.getUpdatetime());
			vo.setLine_no(trunkLineup.getLine_no());
			vo.setZzd_name(trunkLineup.getZzd_name());

			Company company = companyRepository.findById(trunkLineup.getBd_company());
			if (company != null) {
				vo.setCompany_name(company.getC_name());
			}

			switch (trunkLineup.getLineup_status()) {
			case 1:
				vo.setStatusName("等待");
				break;
			case 2:
				vo.setStatusName("请进");
				break;
			case 3:
				vo.setStatusName("已进");
				break;
			case 4:
				vo.setStatusName("预备");
				break;
			case 5:
				vo.setStatusName("正装");
				break;
			case 6:
				vo.setStatusName("装完");
				break;
			}
			vos.add(vo);
		}
		
		//Collections.sort(list,(User a,User b)->(a.getOrder().compareTo(b.getOrder()));
		
		 Collections.sort(vos,new Comparator<LineupSingleVO>(){
	            public int compare(LineupSingleVO arg0, LineupSingleVO arg1) {
	                return String.valueOf(arg0.getLine_no()).compareTo(String.valueOf(arg1.getLine_no()));
	            }
	        });

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", vos);
		return map;
	}

}
