package zkxy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
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
import zkxy.model.Bid;
import zkxy.model.BidDetail;
import zkxy.model.BidPds;
import zkxy.model.User;
import zkxy.repository.BidRepository;
import zkxy.vo.BidDeployVO;
import zkxy.vo.BidDetailVO;

@RestController
@Api(value = "竞标api", description = "竞标API")
@RequestMapping("/Bid")
public class BidController {

	@Autowired
	private BidRepository bidRepository;

	@ApiOperation(value = "招标", notes = "招标")
	@ApiImplicitParam(paramType = "body", name = "bidDeployVO", dataType = "BidDeployVO", required = true, value = "招标信息")
	@RequestMapping(value = "/Deploy", method = RequestMethod.POST)
	@Role
	public @ResponseBody Map<String, Object> depolyBid(@RequestBody BidDeployVO bidDeployVO, HttpSession session)
			throws Throwable {

		User user = (User) session.getAttribute("user");

		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足");
		}
		// 输入验证
		if (bidDeployVO == null) {
			throw new Exception("招标信息无效");
		}
		if (StringUtils.isEmpty(bidDeployVO.getBid_name())) {
			throw new Exception("招标主题信息不能为空");
		}
		if (new Date().after(bidDeployVO.getBid_time())) {
			throw new Exception("开标日期不准确");

		}
		if (CollectionUtils.isEmpty(bidDeployVO.getBidPdsInfos())) {
			throw new Exception("发布产品信息不能为空");
		}

		Bid bid = new Bid();
		bid.setBid_name(bidDeployVO.getBid_name());
		bid.setBid_time(bidDeployVO.getBid_time());
		bid.setRemark(bidDeployVO.getRemark());
		bid.setBid_status(10);
		bidRepository.AddBidInfo(bid);
		List<BidPds> bidPdsInfos = bidDeployVO.getBidPdsInfos();
		for (BidPds info : bidPdsInfos) {
			info.setBid_id(bid.getID());
		}
		bidRepository.AddBidPdsInfo(bidPdsInfos);
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "发布成功!");
		return map;
	}

	@ApiOperation(value = "修改招标信息", notes = "修改招标信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "body", name = "bidDeployVO", dataType = "BidDeployVO", required = true, value = "招标信息"),
			@ApiImplicitParam(paramType = "path", name = "bid_id", dataType = "int", required = true, value = "招标编号") })
	@RequestMapping(value = "/{bid_id}/Update", method = RequestMethod.POST)
	@Role
	public @ResponseBody Map<String, Object> updateBid(@PathVariable("bid_id") int bid_id,
			@RequestBody BidDeployVO bidDeployVO, HttpSession session) throws Throwable {

		User user = (User) session.getAttribute("user");

		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足");
		}
		// 输入验证
		if (bidDeployVO == null) {
			throw new Exception("招标信息无效");
		}
		if (StringUtils.isEmpty(bidDeployVO.getBid_name())) {
			throw new Exception("招标主题信息不能为空");
		}
		if (new Date().after(bidDeployVO.getBid_time())) {
			throw new Exception("开标日期不准确");

		}
		if (CollectionUtils.isEmpty(bidDeployVO.getBidPdsInfos())) {
			throw new Exception("发布产品信息不能为空");
		}

		Bid oldBid = bidRepository.FindBidById(bid_id);
		if (oldBid == null) {
			throw new Exception("无效的招标编号");
		}

		// 一小时内且没有单位投标的
		if (!CollectionUtils.isEmpty(bidRepository.FindBidDetailByBidId(bid_id))) {
			throw new Exception("已有用户投标，无法修改当前招标信息");
		}

		long l = (new Date()).getTime() - oldBid.getCreate_time().getTime();// 当前时间与给定时间差的毫秒数
		long days = l / (24 * 60 * 60 * 1000);// 这个时间相差的天数整数，大于1天为前天的时间了，小于24小时则为昨天和今天的时间
		long hours = (l / (60 * 60 * 1000) - days * 24);// 这个时间相差的减去天数的小时数
		if (hours > 1) {
			throw new Exception("投标信息发布已经超过1小时，无法修改");
		}

		// 更新招标信息
		Bid bid = new Bid();
		bid.setBid_name(bidDeployVO.getBid_name());
		bid.setBid_time(bidDeployVO.getBid_time());
		bid.setRemark(bidDeployVO.getRemark());
		bid.setBid_status(oldBid.getBid_status());
		bid.setID(oldBid.getID());
		bidRepository.UpdateBid(bid);// (bid);

		// 更新招标產品信息
		List<BidPds> bidPdsInfos = bidDeployVO.getBidPdsInfos();
		bidRepository.cancelBidPds(bid_id);
		bidRepository.AddBidPdsInfo(bidPdsInfos);

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "修改成功!");
		return map;
	}

	@ApiOperation(value = "撤销招标", notes = "撤销招标")
	@ApiImplicitParam(paramType = "path", name = "bid_id", dataType = "int", required = true, value = "招标编号")
	@RequestMapping(value = "/{bid_id}/Delete", method = RequestMethod.DELETE)
	@Role
	public @ResponseBody Map<String, Object> deleteBid(@PathVariable("bid_id") int bid_id, HttpSession session)
			throws Throwable {
		User user = (User) session.getAttribute("user");

		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足");
		}

		Bid oldBid = bidRepository.FindBidById(bid_id);
		if (oldBid == null) {
			throw new Exception("无效的招标编号");
		}

		// 一小时内且没有单位投标的
		if (!CollectionUtils.isEmpty(bidRepository.FindBidDetailByBidId(bid_id))) {
			throw new Exception("已有用户投标，无法撤销当前招标信息");
		}

		long l = (new Date()).getTime() - oldBid.getCreate_time().getTime();// 当前时间与给定时间差的毫秒数
		long days = l / (24 * 60 * 60 * 1000);// 这个时间相差的天数整数，大于1天为前天的时间了，小于24小时则为昨天和今天的时间
		long hours = (l / (60 * 60 * 1000) - days * 24);// 这个时间相差的减去天数的小时数
		if (hours > 1) {
			throw new Exception("投标信息发布已经超过1小时，无法撤销");
		}

		if (oldBid.getBid_status() < 10 || oldBid.getBid_status() > 20) {
			throw new Exception("当前状态下的招标信息无法撤销!");
		}
		// 10 < status && status <= 20
		bidRepository.cancelBid(bid_id);
		bidRepository.cancelBidPds(bid_id);

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "撤销成功!");
		return map;
	}

	@ApiOperation(value = "投标", notes = "投标")
	@ApiImplicitParam(paramType = "body", name = "bidDetailVO", dataType = "BidDetailVO", required = true, value = "")
	@RequestMapping(value = "/Apply", method = RequestMethod.POST)
	@Role
	public @ResponseBody Map<String, Object> applyBid(@RequestBody List<BidDetailVO> bidDetailVOs, HttpSession session)
			throws Throwable {

		User user = (User) session.getAttribute("user");

		if ("系统用户".equals(user.getUserType())) {
			throw new Exception("系统用户无权竞标");
		}
		// 输入验证
		if (bidDetailVOs == null) {
			throw new Exception("投标信息无效");
		}
		Bid bid=null;
		for (BidDetailVO bidDetailVO : bidDetailVOs) {
			if (bidDetailVO.getPrice() <= 0) {
				throw new Exception("无效的投标价格");
			}

			if (bidDetailVO.getRequirement() <= 0) {
				throw new Exception("无效的竞拍数量");
			}
			BidPds bidPds = bidRepository.FindBidPdsById(bidDetailVO.getBid_pds_id());
			if (bidPds == null) {
				throw new Exception("投标信息无效");
			}

			if (bidDetailVO.getPrice() < bidPds.getPrice()) {
				throw new Exception("投标价过低");
			}
			bid = bidRepository.FindBidById(bidPds.getBid_id());
			if (bid == null) {
				throw new Exception("投标信息无效");
			}

			if (bid.getBid_status() > 30) {
				throw new Exception("投标期限已过，无法投标");
			}
		}
		
		for (BidDetailVO bidDetailVO : bidDetailVOs) {
			BidPds bidPds = bidRepository.FindBidPdsById(bidDetailVO.getBid_pds_id());
			 
			List<BidDetail> details=	bidRepository.FindBidDetailByPdsId(user.getUserID(), bidPds.getBid_id(),bidPds.getPds_id());
			if(details !=null &&details.size()>0) {
				throw new Exception("产品<"+bidPds.getP_type()+">已投标,不能重复投标!");
			} 
		}
		
		for (BidDetailVO bidDetailVO : bidDetailVOs) {
			BidPds bidPds = bidRepository.FindBidPdsById(bidDetailVO.getBid_pds_id());
			BidDetail bidDetail = new BidDetail();
			bidDetail.setUserID(user.getUserID());
			bidDetail.setBid_id(bidPds.getBid_id());
			bidDetail.setBid_status(1);
			bidDetail.setPds_id(bidPds.getPds_id());
			bidDetail.setPrice(bidDetailVO.getPrice());
			bidDetail.setRemark(bidDetailVO.getRemark());
			bidDetail.setRequirement(bidDetailVO.getRequirement());
			
			
			
			bidRepository.AddBidDetailInfo(bidDetail);
			
		}
		// 更新招标信息状态--投标中
		bidRepository.UpdateBidStatus(bid.getID(), 30);
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "投标成功!");
		return map;
	}

	@ApiOperation(value = "撤销投标", notes = "撤销投标")
	@ApiImplicitParam(paramType = "path", name = "bid_Detail_id", dataType = "int", required = true, value = "投标编号")
	@RequestMapping(value = "zhaobiao/{bid_Detail_id}/Delete", method = RequestMethod.DELETE)
	@Role
	public @ResponseBody Map<String, Object> deleteBidDetail(@PathVariable("bid_Detail_id") int bid_Detail_id,
			HttpSession session) throws Throwable {
		User user = (User) session.getAttribute("user");

		if (!"客户单位".equals(user.getUserType())) {
			throw new Exception("权限不足");
		}

		BidDetail bidDetail = bidRepository.FindBidDetailById(bid_Detail_id);
		// Bid oldBid = bidRepository.FindBidById(bid_Detail_id);
		if (bidDetail == null) {
			throw new Exception("无效的投标编号");
		}

		Bid bid = bidRepository.FindBidById(bidDetail.getBid_id());
		if (bid == null) {
			throw new Exception("无效的投标编号");
		}

		if (bid.getBid_status() < 10 || bid.getBid_status() > 40) {
			throw new Exception("当前状态下的投标信息无法撤销!");
		}
		// 10 < status && status <= 20
		bidRepository.cancelBidDetail(bid_Detail_id);

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "撤销成功!");
		return map;
	}

	@ApiOperation(value = "修改开标时间", notes = "修改开标时间")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "bid_id", dataType = "int", required = true, value = "招标编号"),
			@ApiImplicitParam(paramType = "path", name = "bid_time", dataType = "String", required = true, value = "开标时间") })
	@RequestMapping(value = "/{bid_id}/{bid_time}/Update", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> updateBidTime(@PathVariable("bid_id") int bid_id,
			@PathVariable("bid_time") String bid_time, HttpSession session) throws Throwable {
		User user = (User) session.getAttribute("user");

		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足");
		}

		Bid bid = bidRepository.FindBidById(bid_id);

		if (bid == null) {
			throw new Exception("无效的招标编号");
		}

		// if (new Date().after(Date(bid_time))) {
		// throw new Exception("开标日期不准确");
		//
		// }

		if (bid.getBid_status() < 10 || bid.getBid_status() > 20) {
			throw new Exception("当前状态下不能修改开标时间");
		}
		// Date.parse(bid_time);
		String bidName = bid_time + "招标信息";
		// 10 < status && status <= 20
		bidRepository.UpdateBidTime(bidName, bid_id, bid_time);

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "修改成功!");
		return map;
	}

	@ApiOperation(value = "查询招标列表", notes = "查询招标列表")
	@RequestMapping(value = "/List", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> FindBidList(HttpSession session) throws Throwable {

		User user = (User) session.getAttribute("user");

		if ("司机".equals(user.getUserType())) {
			throw new Exception("权限不足");
		}

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", bidRepository.FindAllBid());
		return map;
	}

	@ApiOperation(value = "查询招标详情", notes = "查询招标详情")
	@ApiImplicitParam(paramType = "path", name = "bid_id", dataType = "int", required = true, value = "")
	@RequestMapping(value = "/{bid_id}/PdsList", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> FindBidPds(@PathVariable("bid_id") int bid_id, HttpSession session)
			throws Throwable {

		User user = (User) session.getAttribute("user");

		if ("司机".equals(user.getUserType())) {
			throw new Exception("权限不足");
		}

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", bidRepository.FindBidPdsByBidId(bid_id));
		return map;
	}

	@ApiOperation(value = "查询投标详情", notes = "查询投标详情")
	@ApiImplicitParam(paramType = "path", name = "bid_id", dataType = "int", required = true, value = "")
	@RequestMapping(value = "/{bid_id}/Tbid/List", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> FindTBidList(@PathVariable("bid_id") int bid_id, HttpSession session)
			throws Throwable {

		User user = (User) session.getAttribute("user");
		List<BidDetail> details = null;
		if ("司机".equals(user.getUserType())) {
			throw new Exception("权限不足");
		} else if ("客户单位".equals(user.getUserType())) {
			details = bidRepository.FindBidDetailByBidAndUserId(user.getUserID(), bid_id);
		} else {
			details = bidRepository.FindBidDetailByBidId(bid_id);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", details);
		return map;
	}

	/**
	 * 将到达开标日期的 招标状态 置为等待评标...
	 */

	public final static long ONE_Minute = 60 * 1000;

	@Scheduled(fixedDelay = ONE_Minute)
	public void pingbiaoBid() {

		bidRepository.UpdateBidStatusToWatingPingBiao();
		// update zkxy_bid set bid_status=50 where bid_time<#{now time}
	}

	@ApiOperation(value = "开标", notes = "开标")

	@ApiImplicitParam(paramType = "path", name = "bid_id", dataType = "int", required = true, value = "")
	@RequestMapping(value = "/{bid_id}/PingBiao", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> pingbiaoBid(@PathVariable("bid_id") int bid_id, HttpSession session)
			throws Throwable {

		// bidRepository.UpdateBidStatusToPingBiao();
		User user = (User) session.getAttribute("user");

		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足");
		}
		Bid bid = bidRepository.FindBidById(bid_id);

		if (bid == null) {
			throw new Exception("无效的招标编号");
		}

		if (bid.getBid_status() != 41) {
			throw new Exception("当前状态不能开标");
		}

		bidRepository.UpdateBidStatus(bid_id, 50);

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "开标成功！");
		return map;
	}

	@ApiOperation(value = "定标", notes = "定标")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "bid_id", dataType = "int", required = true, value = ""),
			@ApiImplicitParam(paramType = "path", name = "bid_status", dataType = "int", required = true, value = "") })
	@RequestMapping(value = "/{bid_id}/{bid_status}/DingBiao", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> SetBidDingBiao(@PathVariable("bid_id") int bid_id,
			@PathVariable("bid_status") int bid_status, HttpSession session) throws Throwable {

		User user = (User) session.getAttribute("user");

		if (!"系统用户".equals(user.getUserType())) {
			throw new Exception("权限不足");
		}
		Bid bid = bidRepository.FindBidById(bid_id);

		if (bid == null) {
			throw new Exception("无效的招标编号");
		}

		if (bid.getBid_status() < 50 || bid.getBid_status() > 60) {
			throw new Exception("当前状态不能定标");
		}

		// if (bid.getBid_status() == 60) {
		// throw new Exception("本次招标已定标，不能重复定标");
		// }
		bidRepository.UpdateBidStatus(bid_id, bid_status);
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "定标成功！");
		return map;
	}

}
