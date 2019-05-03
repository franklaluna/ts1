package zkxy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zkxy.Role;
import zkxy.model.Trunk;
import zkxy.model.User;
import zkxy.model.WeightSheet;
import zkxy.repository.TrunkRepository;
import zkxy.repository.WeightSheetRepository;
import zkxy.vo.WeightSheetStatisticsVO;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "订单统计api", description = "订单统计")
@RequestMapping("/WeightSheet")
public class WeightSheetController {

    @Autowired
    private WeightSheetRepository weightSheetRepository;

    @Autowired
    private TrunkRepository trunkRepository;

    @ApiOperation(value = "统计详情页面", notes = "按时间段统计当前用户关联的所有发货情况")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "start_time", dataType = "String", required = true, value = "统计开始时间"),
            @ApiImplicitParam(paramType = "path", name = "end_time", required = true, dataType = "String", value = "统计结束时间")})
    @RequestMapping(value = "/{start_time}/{end_time}/List", method = RequestMethod.GET)
    @Role
    public @ResponseBody
    Map<String, Object> getWeightSheetList(@PathVariable("start_time") String start_time,
                                           @PathVariable("end_time") String end_time, HttpSession session) throws Throwable {
        List<WeightSheet> weightSheets = null;
        User user = (User) session.getAttribute("user");
        switch (user.getUserType()) {
            case "司机":
                //查询车辆..
                Trunk trunk = trunkRepository.findByUser(user.getUserID());
                if (trunk != null) {
                    List<String> carNos = new ArrayList<String>();
//			     for (Trunk trunk : trunkList) { 
                    carNos.add(trunk.getCar_no());
//		         }  
                    weightSheets = weightSheetRepository.findAllByCarNOsAndDateRange(carNos, start_time, end_time);
                }
                break;
            case "客户单位":
                weightSheets = weightSheetRepository.findAllByGouIDAndDateRange(user.getCompany(), start_time, end_time);
                break;
            case "系统用户":
//                weightSheets = weightSheetRepository.findAllByGongIdDateRange(user.getCompany(), start_time, end_time);
                weightSheets = weightSheetRepository.findAllDateRange(start_time, end_time);
                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", weightSheets);
        return map;
    }


    @ApiOperation(value = "统计详情页面", notes = "按时间段统计当前用户关联的所有发货情况")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "start_time", dataType = "String", required = true, value = "查询开始时间"),
            @ApiImplicitParam(paramType = "path", name = "end_time", required = true, dataType = "String", value = "查询结束时间"),
            @ApiImplicitParam(paramType = "path", name = "pds_id", required = true, dataType = "int", value = "产品")})
    @RequestMapping(value = "/{start_time}/{end_time}/{pds_id}/List/", method = RequestMethod.GET)
    @Role
    public @ResponseBody
    Map<String, Object> getWeightSheetList2(@PathVariable("start_time") String start_time,
                                            @PathVariable("end_time") String end_time, @PathVariable("pds_id") int pds_id, HttpSession session) throws Throwable {
        List<WeightSheet> weightSheets = null;
        User user = (User) session.getAttribute("user");
        switch (user.getUserType()) {
            case "司机":
                //查询车辆..
                Trunk trunk = trunkRepository.findByUser(user.getUserID());
                if (trunk != null) {
                    List<String> carNos = new ArrayList<String>();
//				     for (Trunk trunk : trunkList) { 
                    carNos.add(trunk.getCar_no());
//			         }  
                    weightSheets = weightSheetRepository.findAllByCarNOsAndDateRangeWithPds(carNos, start_time, end_time, pds_id);
                }
                break;
            case "客户单位":
                weightSheets = weightSheetRepository.findAllByGouIDAndDateRangeWithPds(user.getCompany(), start_time, end_time, pds_id);
                break;
            case "系统用户":
//                weightSheets = weightSheetRepository.findAllByGongIdDateRangeWithPds(user.getCompany(), start_time, end_time, pds_id);
                weightSheets = weightSheetRepository.findAllDateRangeWithPds(start_time, end_time, pds_id);
                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", weightSheets);
        return map;
    }


    @ApiOperation(value = "统计", notes = "按时间段汇总统计发货情况")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "start_time", dataType = "String", required = true, value = "统计开始时间"),
            @ApiImplicitParam(paramType = "path", name = "end_time", required = true, dataType = "String", value = "统计结束时间"),
            @ApiImplicitParam(paramType = "path", name = "gou_id", required = true, value = "购灰单位")})
    @RequestMapping(value = "/{start_time}/{end_time}/{gou_id}/statistics/", method = RequestMethod.GET)
    @Role
    public @ResponseBody
    Map<String, Object> getWeightSheetStatistics(@PathVariable("start_time") String start_time,
                                                 @PathVariable("end_time") String end_time, @PathVariable("gou_id") int gou_id, HttpSession session) throws Throwable {
        List<WeightSheetStatisticsVO> weightSheets = null;
        User user = (User) session.getAttribute("user");
        switch (user.getUserType()) {
            case "司机":
                //查询车辆..
                Trunk trunk = trunkRepository.findByUser(user.getUserID());
                if (trunk != null) {
                    List<String> carNos = new ArrayList<String>();

                    carNos.add(trunk.getCar_no());

//                    if (gou_id == -1) {
                    weightSheets = weightSheetRepository.statisticsByCarNOsAndDateRange(carNos, start_time, end_time);
//                    } else {
//                        weightSheets = weightSheetRepository.statisticsByCarNOsAndDateRangeWithPdt(carNos, start_time, end_time, pdt_id);
//                    }
                }
                break;
            case "客户单位":

//                if (gou_id == -1) {
                weightSheets = weightSheetRepository.statisticsByGouIDAndDateRange(user.getCompany(), start_time, end_time);
//                } else {
//                    weightSheets = weightSheetRepository.statisticsByGouIDAndDateRangeWithPdt(user.getCompany(), start_time, end_time, pdt_id);
//                }


                break;
            case "系统用户":
                if (gou_id == -1) {
//                    weightSheets = weightSheetRepository.statisticsAllByGongIdDateRange(user.getCompany(), start_time, end_time);
                    weightSheets = weightSheetRepository.statisticsAllDateRange(start_time, end_time);
                } else {
//                    weightSheets = weightSheetRepository.statisticsAllByGongIdDateRangeWithGouId(user.getCompany(), start_time, end_time, gou_id);
                    weightSheets = weightSheetRepository.statisticsAllDateRangeWithGouId(start_time, end_time, gou_id);
                }

                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", weightSheets);
        return map;
    }


}
