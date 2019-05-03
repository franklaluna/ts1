package zkxy.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import zkxy.Role;
import zkxy.model.DeliveryPlan;
import zkxy.model.Product;
import zkxy.model.User;
import zkxy.repository.DeliveryPlanRepository;
import zkxy.repository.ProductRepository;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "发货计划api", description = "发货计划")
@RequestMapping("/DeliveryPlan")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
public class DeliveryPlanController {

    @Autowired
    private DeliveryPlanRepository deliveryPlanRepository;

    @Autowired
    private ProductRepository productRepository;

    @ApiOperation(value = "发货计划", notes = "发货计划最新列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "current", required = true, value = "当前页"),
            @ApiImplicitParam(paramType = "path", name = "size", required = true, value = "显示数量"),
            @ApiImplicitParam(paramType = "path", name = "planType", required = true, value = "计划类型", example = "-1:全部,0无计划，1车数，2数量"),
            @ApiImplicitParam(paramType = "path", name = "pdtId", required = true, value = "产品ID", example = "-1:全部"),
            @ApiImplicitParam(paramType = "path", name = "gouId", required = true, value = "客户单位Id", example = "-1:全部"),
            @ApiImplicitParam(paramType = "path", name = "startTime", required = true, value = "开始时间"),
            @ApiImplicitParam(paramType = "path", name = "endTime", required = true, value = "结束时间"),
            @ApiImplicitParam(paramType = "path", name = "planYN", required = true, value = "已计划和有计划", example = "0已计划,1有计划")})
    @RequestMapping(value = "/{current}/{size}/{planType}/{pdtId}/{gouId}/{startTime}/{endTime}/{planYN}/List", method = RequestMethod.GET)
    @Role
    public @ResponseBody
    Map<String, Object> TopList(@PathVariable int current, @PathVariable int size,
                                @PathVariable int planType, @PathVariable int pdtId, @PathVariable int gouId, @PathVariable String startTime,
                                @PathVariable String endTime, @PathVariable int planYN, HttpSession session) throws Throwable {
        User user = (User) session.getAttribute("user");
        boolean requestSuccess = false;
        Object message = "";
        List<DeliveryPlan> deliveryPlan = null;
        // 输入验证
        if (size <= 0) {
            throw new Exception("发货计划数量应该>0");
        } else {
            EntityWrapper<DeliveryPlan> whereCondition = new EntityWrapper<DeliveryPlan>();
            String where = "";
            // 有效数据
            where += " deleted=0 ";
            // -1查询全部
            if (planType != -1) {
                where += " and plan_type=" + String.valueOf(planType);
                // whereCondition.eq("planType", planType);
            }
            // -1查询全部
            if (pdtId != -1) {
                where += " and products=" + String.valueOf(pdtId);
                //whereCondition.eq("products", pdtId);
            }

            if (planYN == 0) {
                // 已计划
                where += " and plan_number >=0 ";
                //whereCondition.gt("plan_number", 0);
            } else if (planYN == 1) {
                // 有计划
                where += " and plan_number >0 ";
                //whereCondition.ge("plan_number", 0);
            }

            //有交集就查询

            where += " and (start_time between '" + startTime + "' and '" + endTime + "' or  end_time between '" + startTime + "' and '" + endTime + "')";

//            whereCondition.between("startTime", startTime, endTime).or("endTime between {0} and {1}", startTime,
//                    endTime);

            Pagination pg = new Pagination(current, size);

            if ("系统用户".equals(user.getUserType())) {
                // 查询所有客户单位的发货计划
                if (gouId != -1) {
                    where += " and gou_id= " + gouId;
                }
            } else if ("客户单位".equals(user.getUserType())) {
                where += " and gou_id= " + user.getCompany();
            } else {
                throw new Exception("该用户无权查看发货计划");
            }
            deliveryPlan = deliveryPlanRepository.findWithPage(pg, where);
            requestSuccess = true;
            message = deliveryPlan;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", requestSuccess);
        map.put("message", message);
        return map;
    }

    @ApiOperation(value = "删除计划", notes = "删除计划")
    @ApiImplicitParam(paramType = "path", name = "planId", dataType = "int", required = true, value = "计划编号")
    @RequestMapping(value = "/{planId}/Delete", method = RequestMethod.DELETE)
    @Role
    public @ResponseBody
    Map<String, Object> deleteDeliveryPlan(@PathVariable("planId") int planId,
                                           HttpSession session) throws Throwable {
        User user = (User) session.getAttribute("user");

        if (!"系统用户".equals(user.getUserType())) {
            throw new Exception("权限不足");
        }

        DeliveryPlan deliveryPlan = deliveryPlanRepository.selectById(planId);

        if (deliveryPlan == null) {
            throw new Exception("无效的计划编号");
        }

        if (deliveryPlan.getStart_time().before(new Date())) {
            throw new Exception("计划已经开始执行，无法取消!");
        }

        //逻辑删除
        deliveryPlan.setDeleted(true);
        deliveryPlanRepository.updateById(deliveryPlan);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "删除成功!");
        return map;
    }

    @ApiOperation(value = "更新发货计划", notes = "更新发货计划")
    @ApiImplicitParam(paramType = "body", name = "deliveryPlan", dataType = "DeliveryPlan", required = true, value = "发货计划")
    @RequestMapping(value = "/Update", method = RequestMethod.POST)
    @Role("系统用户")
    public @ResponseBody
    Map<String, Object> updateDeliveryPlan(@RequestBody DeliveryPlan deliveryPlan,
                                           HttpSession session) throws Throwable {

        User user = (User) session.getAttribute("user");
        if (!"系统用户".equals(user.getUserType())) {
            throw new Exception("权限不足");
        }
        // 输入验证
        if (deliveryPlan == null) {
            throw new Exception("信息无效");
        }
        DeliveryPlan queryDeliveryPlan = deliveryPlanRepository.selectById(deliveryPlan.getId());
        if (queryDeliveryPlan == null || queryDeliveryPlan.isDeleted()) {
            throw new Exception("信息无效");
        }

        if (queryDeliveryPlan.getStart_time().before(new Date())) {
            throw new Exception("计划已经开始执行，无法修改!");
        }

        if (deliveryPlan.getStart_time().before(new Date())) {
            throw new Exception("计划时间应设置在未来一个时间点");
        }

        Product product = productRepository.findProductById(deliveryPlan.getProducts());
        if (product == null) {
            throw new Exception("无效的产品编号");
        }

        //更新主表--触发器出发日志
        deliveryPlanRepository.updateById(deliveryPlan);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", "提交成功!");
        return map;
    }


}
