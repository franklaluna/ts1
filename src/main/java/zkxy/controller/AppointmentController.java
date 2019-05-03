package zkxy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import zkxy.model.Appointment;
import zkxy.model.AppointmentDetail;
import zkxy.model.Company;
import zkxy.model.Product;
import zkxy.model.User;
import zkxy.repository.AppointmentRepository;
import zkxy.repository.CompanyRepository;
import zkxy.repository.ProductRepository;
import zkxy.repository.UserRepository;
import zkxy.vo.AppointmentVO;

@RestController
@Api(value = "预约api", description = "预约信息API")
@RequestMapping("/Appointment")
public class AppointmentController {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private CompanyRepository companyRepository;

	@ApiOperation(value = "预约", notes = "预约")
	@ApiImplicitParam(paramType = "body", name = "appointmentVO", dataType = "AppointmentVO", required = true, value = "预约信息")
	@RequestMapping(value = "/Deploy", method = RequestMethod.POST)
	@Role("系统用户")
	public @ResponseBody Map<String, Object> depolyAppointment(@RequestBody AppointmentVO appointmentVO,
			HttpSession session) throws Throwable {

		// 输入验证
		if (appointmentVO == null) {
			throw new Exception("预约信息无效");
		}
		if (appointmentVO.getA_count() <= 0) {
			throw new Exception("预约需求量应该大于0");
		}
		if (new Date().after(appointmentVO.getA_datetime())) {
			throw new Exception("预约日期不准确");
		}

		Product product = productRepository.findProductById(appointmentVO.getProducts());

		if (product == null) {
			throw new Exception("无效的产品编号");
		}

		User user = (User) session.getAttribute("user");

		if ("系统用户".equals(user.getUserType())||"司机".equals(user.getUserType())) {
			throw new Exception("系统用户或者司机无法提交预约信息");
		}
		

		{
			Appointment appointment = new Appointment();
			appointment.setA_count(appointmentVO.getA_count());
			appointment.setA_datetime(appointmentVO.getA_datetime());
			appointment.setA_status(2);
			appointment.setProducts(appointmentVO.getProducts());
			appointment.setUser_id(user.getUserID());
			appointmentRepository.add(appointment);

		}

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "提交成功!");
		return map;
	}

	@ApiOperation(value = "撤销预约", notes = "预约撤销")
	@ApiImplicitParam(paramType = "path", name = "appointmentId", dataType = "int", required = true, value = "预约编号")
	@RequestMapping(value = "/{appointmentId}/Delete", method = RequestMethod.DELETE)
	@Role
	public @ResponseBody Map<String, Object> deleteAppointment(@PathVariable("appointmentId") int appointmentId,
			HttpSession session) throws Throwable {
		User user = (User) session.getAttribute("user");

		if ("系统用户".equals(user.getUserType())) {
			throw new Exception("系统用户无法提交预约信息");
		}

		Appointment appointment = appointmentRepository.SelectById(appointmentId);

		if (appointment == null) {
			throw new Exception("无效的预约编号");
		}

		if (appointment.getA_status() != 2) {
			throw new Exception("当前状态下的预约无法取消!");
		}

		appointmentRepository.deleteById(appointmentId);

		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", "撤销成功!");
		return map;
	}

	@ApiOperation(value = "预约列表查询", notes = "预约列表查询")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "start_time", dataType = "String", required = true, value = "查询开始时间"),
			@ApiImplicitParam(paramType = "path", name = "end_time", required = true, dataType = "String", value = "统计结束时间") })
	@RequestMapping(value = "/{start_time}/{end_time}/List/", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getAppointmentList(@PathVariable("start_time") String start_time,
			@PathVariable("end_time") String end_time, HttpSession session) throws Throwable {

		List<Appointment> appointments = null;
		User user = (User) session.getAttribute("user");
		switch (user.getUserType()) {
		case "司机":
		case "客户单位":
			appointments = appointmentRepository.SelectByCompanyIdWithDate(user.getCompany(), start_time, end_time);
			break;
		case "系统用户":
			appointments = appointmentRepository.SelectAllWithDate(start_time, end_time);
			break;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", appointments);
		return map;
	}

	@ApiOperation(value = "通过状态查询预约列表", notes = "通过状态查询预约列表")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "status", dataType = "status", required = true, value = "预约状态") })
	@RequestMapping(value = "/{status}/List", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getAppointmentListWithStatus(@PathVariable("status") int status,
			HttpSession session) throws Throwable {

		List<Appointment> appointments = null;
		User user = (User) session.getAttribute("user");
		switch (user.getUserType()) {
		case "司机":
		case "客户单位":
			appointments = appointmentRepository.SelectByUserIdAndStatus(user.getUserID(), status);
			break;
		case "系统用户":
			appointments = appointmentRepository.SelectAllWithStatus(status);
			break;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", appointments);
		return map;
	}

	@ApiOperation(value = "预约列表查询", notes = "预约列表查询")
	@RequestMapping(value = "/List", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getAppointmentList(HttpSession session) throws Throwable {

		List<Appointment> appointments = null;
		User user = (User) session.getAttribute("user");
		switch (user.getUserType()) {
		case "司机":
		case "客户单位":
			appointments = appointmentRepository.SelectByUserId(user.getUserID());
			break;
		case "系统用户":
			appointments = appointmentRepository.SelectAll();
			break;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", appointments);
		return map;
	}

	@ApiOperation(value = "查询预约详情", notes = "查询预约详情")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "appointment_id", dataType = "int", required = true, value = "预约编号") })
	@RequestMapping(value = "/{appointment_id}/Detail", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getAppointmentDetails(@PathVariable("appointment_id") int appointment_id,
			HttpSession session) throws Throwable {
 
		Appointment appointment = appointmentRepository.SelectById(appointment_id);
		AppointmentDetail vo = null;

		if (appointment != null) {
			User user = userRepository.getUserById(appointment.getUser_id());

			Company company = companyRepository.findById(user.getCompany());

			vo = new AppointmentDetail();

			vo.setA_count(appointment.getA_count());
			vo.setA_datetime(appointment.getA_datetime());
			vo.setA_status(appointment.getA_status());
			vo.setID(appointment.getID());
			vo.setP_type(appointment.getP_type());
			vo.setProducts(appointment.getProducts());
			vo.setremark(appointment.getremark());
			vo.setUser_id(appointment.getUser_id());
			if (user != null) {
				vo.setUserName(user.getUserName());
			}
			if (company != null) {
				vo.setCompany(company.getC_name());
			}

		}
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", vo);
		return map;
	}

}
