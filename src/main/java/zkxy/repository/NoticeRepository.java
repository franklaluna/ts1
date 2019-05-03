package zkxy.repository; 
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import zkxy.model.Notice;

public interface NoticeRepository {
 	
	@Insert("insert into zkxy_notice ( title,details ,short_details,remark) values (#{Title},#{Details},#{ShortDetails},#{remark})")
	void add(Notice notice);
	
	@Select("select  Top ${topCount} * from zkxy_notice order by create_time desc ")
	@Results(value = {  
	            @Result(property = "shortDetails", column = "short_details"),  
	            @Result(property = "createTime", column = "create_time") })  
	List<Notice> find(@Param("topCount") int topCount);
	
	@Select("select  * from zkxy_notice order by create_time desc ")
	@Results(value = {  
	            @Result(property = "shortDetails", column = "short_details"),  
	            @Result(property = "createTime", column = "create_time") })  
	List<Notice> findWithPage(Pagination page);
	
	@Select("select * from zkxy_notice where ID=${id}")
	@Results(value = {  
            @Result(property = "shortDetails", column = "short_details"),  
            @Result(property = "createTime", column = "create_time") })  
	Notice findById(@Param("id") int id);
}
