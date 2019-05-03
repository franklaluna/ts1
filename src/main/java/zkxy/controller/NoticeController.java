package zkxy.controller;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mysql.jdbc.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zkxy.Role;
import zkxy.model.Notice;
import zkxy.model.User;
import zkxy.repository.NoticeRepository;
import zkxy.vo.DeployNoticeVO;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "公告api", description = "公告API")
@RequestMapping("/Notice")
public class NoticeController {

	@Autowired
	private NoticeRepository noticeRepository;

	@ApiOperation(value = "公告发布", notes = "公告发布")
	@ApiImplicitParam(paramType = "body", name = "deployNoticeVO", dataType = "DeployNoticeVO", required = true, value = "发布公告信息")
	@RequestMapping(value = "/Deploy", method = RequestMethod.POST)
	@Role("系统用户")
	public @ResponseBody Map<String, Object> depolyNotice(User user, @RequestBody DeployNoticeVO deployNoticeVO,
			HttpSession session) throws Throwable {
		boolean requestSuccess = false;
		Object message = "";

		// 输入验证
		if (deployNoticeVO == null || StringUtils.isNullOrEmpty(deployNoticeVO.getTitle())
				|| StringUtils.isNullOrEmpty(deployNoticeVO.getDetails())) {
			message = "信息不足";
		} else {
			Notice notice = new Notice();
			notice.setTitle(deployNoticeVO.getTitle());
			notice.setDetails(deployNoticeVO.getDetails());
			if (notice.getDetails().length() > 32) {
				notice.setShortDetails(deployNoticeVO.getDetails().substring(0, 32));
			} else {
				notice.setShortDetails(deployNoticeVO.getDetails());
			}
			noticeRepository.add(notice);
			requestSuccess = true;
			message = "发布成功！";
		}

		Map<String, Object> map = new HashMap<>();
		map.put("success", requestSuccess);
		map.put("message", message);
		return map;
	}

	@ApiOperation(value = "公告列表", notes = "公告最新列表")
	@ApiImplicitParam(paramType = "path", name = "count", required = true, value = "查询条数")
	@RequestMapping(value = "/{count}/List", method = RequestMethod.GET)
	@Role("系统用户")
	public @ResponseBody Map<String, Object> TopList(User user, @PathVariable int count) throws Throwable {
		boolean requestSuccess = false;
		Object message = "";
		// 输入验证
		if (count <= 0) {
			throw new Exception("公告数量应该>0");
		} else {
			List<Notice> notices = noticeRepository.find(count);
			requestSuccess = true;
			message = notices;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("success", requestSuccess);
		map.put("message", message);
		return map;
	}

	@ApiOperation(value = "公告列表", notes = "公告最新列表")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "path", name = "current", required = true, value = "当前页"),
			@ApiImplicitParam(paramType = "path", name = "size", required = true, value = "显示数量") })
	@RequestMapping(value = "/{current}/{size}/List", method = RequestMethod.GET)
	@Role("系统用户")
	public @ResponseBody Map<String, Object> TopList(User user, @PathVariable int current, @PathVariable int size)
			throws Throwable {
		com.baomidou.mybatisplus.plugins.pagination.Pagination pg;
		boolean requestSuccess = false;
		Object message = "";
		// 输入验证
		if (size <= 0) {
			throw new Exception("公告数量应该>0");
		} else {
			pg = new Pagination(current, size);
			List<Notice> notices = noticeRepository.findWithPage(pg);
			requestSuccess = true;
			message = notices;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("success", requestSuccess);
		map.put("message", message);
		return map;
	}

	@ApiOperation(value = "公告详情", notes = "查看公告详情")
	@ApiImplicitParam(paramType = "path", name = " id", required = true, value = "查询条数")
	@RequestMapping(value = "/{id}/Detail", method = RequestMethod.GET)
	@Role("系统用户")
	public @ResponseBody Map<String, Object> detail(User user, @PathVariable int id) throws Throwable {
		boolean requestSuccess = false;
		Object message = "";
		Notice notice = noticeRepository.findById(id);
		requestSuccess = true;
		message = notice;

		Map<String, Object> map = new HashMap<>();
		map.put("success", requestSuccess);
		map.put("message", message);
		return map;
	}

}
