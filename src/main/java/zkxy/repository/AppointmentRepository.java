package zkxy.repository; 
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import zkxy.model.Appointment;

public interface AppointmentRepository {
//	----------  ------------  ---------------  ------  ------  -------  ------  -------------------------------  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------
//	ID           int(11)       (NULL)           NO      PRI     (NULL)           select,insert,update,references                                                                                                                                                                        
//	user_id      int(11)       (NULL)           NO              (NULL)           select,insert,update,references                                                                                                                                                                        
//	products     int(11)       (NULL)           NO              (NULL)           select,insert,update,references                                                                                                                                                                        
//	a_count      int(11)       (NULL)           NO              (NULL)           select,insert,update,references                                                                                                                                                                        
//	a_datetime   datetime      (NULL)           NO              (NULL)           select,insert,update,references                                                                                                                                                                        
//	create_time  datetime      (NULL)           NO              (NULL)           select,insert,update,references                                                                                                                                                                        
//	a_status     int(11)       (NULL)           NO              (NULL)           select,insert,update,references  0、申请成功、                                                                                                                                                   
//	                                                                                                                          1、申请失败、                                                                                                                                       
//	             
	//通过任务对象添加任务对象
	@Insert("insert into zkxy_appointment (user_id ,products,a_count,a_datetime,a_status) "
			+ "values (#{user_id},#{products},#{a_count},#{a_datetime},#{a_status})")
	void add(Appointment appointment);
	//通过id删除对应对象
	@Delete("delete from zkxy_appointment where id=#{id}")
	void deleteById(@Param("id")int id);
	//通过id查询任务
	@Select("select a.*,b.p_type from zkxy_appointment a,zkxy_products b  where  a.products=b.id and a.id=#{id}")
	Appointment SelectById(@Param("id")int id);
	//查找某个用户某个时间段的任务信息
	@Select("select a.*,b.p_type from zkxy_appointment a,zkxy_products b where  a.products=b.id and user_id=#{userId} AND create_time>=startTime AND create_time<=endTime order by create_time desc")
	List<Appointment> SelectByUserIdWithDate(@Param("userId")int userId,@Param("startTime") String startTime,@Param("endTime") String endTime);
	//查找某个公司某个时间段的所有任务信息
//IN ( SELECT userId FROM zkxy_user WHERE companys =2) 
	@Select("select a.*,b.p_type from zkxy_appointment a,zkxy_products b where  a.products=b.id and user_id  "
			+ " in ( SELECT userId FROM zkxy_user WHERE companys =#{companyId})  AND create_time>=startTime "
			+ "AND create_time<=endTime order by create_time desc")
	List<Appointment> SelectByCompanyIdWithDate(@Param("companyId")int companyId,@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	//查找某个用户的全部任务信息
    @Select("select a.*,b.p_type  from zkxy_appointment a,zkxy_products b where a.products=b.id and user_id=#{userId} order by create_time desc")
	List<Appointment> SelectByUserId(@Param("userId")int userId);
	//查询某种用户某种状态的任务信息
	@Select("select a.*,b.p_type  from zkxy_appointment a,zkxy_products b "
			+ "where a.products=b.id and user_id=#{userId} and a.a_status=#{status} order by create_time desc")
	List<Appointment> SelectByUserIdAndStatus(@Param("userId")int userId,@Param("status")int status);

	//SELECT  a.*,b.p_type ,c.c_name FROM zkxy_appointment a,zkxy_products b,zkxy_companys c ,zkxy_user d WHERE a.products=b.id  
	 //AND a.user_id=d.UserID AND d.Companys=c.ID
		//	 ORDER BY a_datetime

	@Select("select  a.*,b.p_type,c.c_name as company from zkxy_appointment a,zkxy_products b, zkxy_companys c ,zkxy_user d  "
			+ "where a.products=b.id AND a.user_id=d.UserID AND d.Companys=c.ID and "
			+ " create_time>=startTime AND create_time<=endTime order by create_time desc")
	List<Appointment> SelectAllWithDate(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	@Select("select a.*,b.p_type,c.c_name as company from zkxy_appointment a,zkxy_products b, zkxy_companys c ,zkxy_user d "
			+ "where a.products=b.id and a.a_status=#{status} AND a.user_id=d.UserID AND d.Companys=c.ID order by create_time desc")
	List<Appointment> SelectAllWithStatus(@Param("status")int status);
	
	@Select("select a.*,b.p_type ,c.c_name as company from zkxy_appointment a,zkxy_products b , zkxy_companys c ,zkxy_user d "
			+ " where a.products=b.id AND a.user_id=d.UserID AND d.Companys=c.ID order by create_time desc")
	List<Appointment> SelectAll();
	
}
