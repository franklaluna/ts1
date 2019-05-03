package zkxy.controller;

import com.mysql.jdbc.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zkxy.Role;
import zkxy.model.Company;
import zkxy.model.Trunk;
import zkxy.model.User;
import zkxy.repository.CompanyRepository;
import zkxy.repository.TrunkRepository;
import zkxy.repository.UserRepository;
import zkxy.vo.DefaultPsdChangeVO;
import zkxy.vo.LoginUserVO;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "用户相关api", description = "用户相关API")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TrunkRepository trunkRepository;

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParam(paramType = "body", name = "loginVO", dataType = "LoginUserVO", required = true, value = "登陆提交信息,优先取用户名，如果为空则取手机号！")
    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> loginPost(@RequestBody LoginUserVO loginVO, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User user = null;
        boolean requestSuccess = false;
        Object message = "用户名密码不正确！";
        try {
            if (loginVO == null) {
                message = "登录信息不足";
            } else if (!StringUtils.isNullOrEmpty(loginVO.getLoginName())
                    && !StringUtils.isNullOrEmpty(loginVO.getLoginPassword())) {
                user = userRepository.getUserByLogin(loginVO.getLoginName(), loginVO.getLoginPassword());
            } else if (null != loginVO.getLoginPhone() && !StringUtils.isNullOrEmpty(loginVO.getLoginPassword())) {
                user = userRepository.getUserByPhoneLogin(loginVO.getLoginPhone(), loginVO.getLoginPassword());
            } else {
                message = "登录信息不足";
            }

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date endDate = null;
            try {
                endDate = df.parse("2018-07-18");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (endDate.getTime() > new Date().getTime()) {
                if (null != user) {
                    requestSuccess = true;
                    Trunk trunk = trunkRepository.findByUser(user.getUserID());
                    if (trunk != null) {
                        user.setCar_no(trunk.getCar_no());
                    }
                    message = user;
                    // 设置session
                    session.setAttribute("user", user);
                }
            } else {
                LoginOut(session);
                message = "预览版已过期,请联系运营商。";
            }


        } catch (Exception e) {
            requestSuccess = false;
            message = "登录失败！";
        }


        map.put("success", requestSuccess);
        map.put("message", message);


        return map;
    }


    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParam(paramType = "body", name = "loginVO", dataType = "LoginUserVO", required = true, value = "登陆提交信息,优先取用户名，如果为空则取手机号！")
    @RequestMapping(value = "v2/Login", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> loginPostNew(@RequestBody LoginUserVO loginVO, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User user = null;
        boolean requestSuccess = false;
        Object message = "用户名密码不正确！";
        try {
            if (loginVO == null) {
                message = "登录信息不足";
            } else if (!StringUtils.isNullOrEmpty(loginVO.getLoginName())
                    && !StringUtils.isNullOrEmpty(loginVO.getLoginPassword())) {
                user = userRepository.getUserByLogin(loginVO.getLoginName(), loginVO.getLoginPassword());
            } else if (null != loginVO.getLoginPhone() && !StringUtils.isNullOrEmpty(loginVO.getLoginPassword())) {
                user = userRepository.getUserByPhoneLogin(loginVO.getLoginPhone(), loginVO.getLoginPassword());
            } else {
                message = "登录信息不足";
            }
            if (null != user) {
                requestSuccess = true;
                Trunk trunk = trunkRepository.findByUser(user.getUserID());
                if (trunk != null) {
                    user.setCar_no(trunk.getCar_no());
                }
                message = user;
                // 设置session
                session.setAttribute("user", user);
            }

        } catch (Exception e) {
            requestSuccess = false;
            message = "登录失败！";
        }


        map.put("success", requestSuccess);
        map.put("message", message);


        return map;
    }

    @ApiOperation(value = "修改密码", notes = "用户修改密码")
    @ApiImplicitParam(paramType = "body", name = "vo", dataType = "DefaultPsdChangeVO", required = true, value = "密码修改信息")
    @RequestMapping(value = "/DefaultPassword", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> defaultPasswordChange(@RequestBody DefaultPsdChangeVO vo) {
        Map<String, Object> map = new HashMap<>();
        User user = null;
        boolean requestSuccess = false;
        Object message = "原始用户名密码不正确！";
        try {
            if (vo == null || StringUtils.isNullOrEmpty(vo.getNewPsd()) || StringUtils.isNullOrEmpty(vo.getNewPsd2())
                    || StringUtils.isNullOrEmpty(vo.getUserName()) || StringUtils.isNullOrEmpty(vo.getDefaultPsd())) {
                message = "修改密码信息不足";
            } else if (!vo.getNewPsd().equals(vo.getNewPsd2())) {
                message = "两次新密码输入不一致！";
            } else {
                // 登录验证
                user = userRepository.getUserByLogin(vo.getUserName(), vo.getDefaultPsd());
                if (null != user) {
                    // 验证通过，修改密码。
                    userRepository.updatePassword(vo.getNewPsd(), user.getUserName());
                    requestSuccess = true;
                    message = "密码修改成功!";
                } else {
                    message = "原始用户名密码不正确！";
                }
            }

        } catch (Exception e) {
            requestSuccess = false;
            message = "密码修改失败！";
        }
        map.put("success", requestSuccess);
        map.put("message", message);
        return map;
    }

    @ApiOperation(value = "获取当前用户单位信息", notes = "获取当前用户单位信息")
    @RequestMapping(value = "/companys", method = RequestMethod.GET)
    @Role
    public @ResponseBody
    Map<String, Object> getCompanyInfo(HttpSession session) throws Throwable {

        User user = (User) session.getAttribute("user");

        int companyId = user.getCompany();

        Company company = companyRepository.findById(companyId);

        // company.setC_end_time(new Date());

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", company);
        return map;
    }

    @ApiOperation(value = "检查用户是否登录", notes = "检查用户是否登录")
    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> checkLogin(HttpSession session) {
        boolean result = false;
        User user = null;

        user = (User) session.getAttribute("user");
        if (user != null) {
            result = true;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("success", result);
        if (result) {
            map.put("message", user);
        } else {
            map.put("message", "未登录");
        }
        return map;
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @RequestMapping(value = "/LoginOut", method = RequestMethod.DELETE)
    public @ResponseBody
    Map<String, Object> LoginOut(HttpSession session) {
        session.setAttribute("user", null);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "已退出登录");
        return map;
    }

}
