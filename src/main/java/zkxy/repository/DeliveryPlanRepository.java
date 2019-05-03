package zkxy.repository;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zkxy.model.DeliveryPlan;

import java.util.List;

/**
 * 发货计划Repository
 *
 * @author xqy251
 */
public interface DeliveryPlanRepository {

    @Select("select  * from zkxy_delivery_plan where ${where}  order by start_time  ")
    List<DeliveryPlan> findWithPage(Pagination page, @Param("where") String where);


    @Select("select  * from zkxy_delivery_plan where ID= #{Id}")
    DeliveryPlan selectById(@Param("Id") int Id);


    @Update("update zkxy_delivery_plan set products=#{deliveryPlan.products},products_name=#{deliveryPlan.products_name} " +
            ",plan_type=#{deliveryPlan.plan_type} ,plan_day=#{deliveryPlan.plan_day} ,start_time=#{deliveryPlan.start_time} " +
            ",end_time=#{deliveryPlan.end_time},plan_number=#{deliveryPlan.plan_number},deleted=#{deliveryPlan.deleted} " +
            " where ID=#{deliveryPlan.id} ")
    int updateById(@Param("deliveryPlan") DeliveryPlan deliveryPlan);

//    @Insert("insert into zkxy_delivery_plan_update_log (plan_ID ,update_time,gou_id,gou_name,products," +
//            "products_name,plan_type,plan_day,start_time,end_time,plan_number,yz_number,gp_number," +
//            "pd_number,operator,deleted,sync) " +
//            "values (#{plan_ID},GETDATE(),#{gou_id},#{gou_name},#{products}," +
//            "#{products_name},#{plan_type},#{plan_day},#{start_time},#{end_time},#{plan_number},#{yz_number},#{gp_number}," +
//            "#{pd_number},#{operator},#{deleted},#{sync})")
//    void insertIntoLog(@Param("deliveryPlan") DeliveryPlan deliveryPlan);

}
