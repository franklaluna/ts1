package zkxy.repository; 
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import zkxy.model.Pdtlevel;

public interface PdtLevelRepository {
 	
 
	/*
	 * 查询料位名称列表
	 */
	@Select("SELECT a.*,(cur_hght*100/totle)  AS percentage ,b.p_type as p_type FROM zkxy_pdtlevel a,zkxy_products b "
			+ "where a.pdt=b.id") 
	List<Pdtlevel> findPdtlevel();
	
  
	/**
	 * 统计汇总
	 * @return
	 */
	@Select("SELECT * , (cur_hght*100/totle) AS  percentage FROM   " +
			"   (SELECT SUM(totle) AS totle,SUM(cur_hght)  as cur_hght FROM zkxy_pdtlevel) as A")
	 Pdtlevel findAllPdtTotle();
	
	@Select("SELECT * from  zkxy_pdtlevel where id=#{id}") 
	Pdtlevel findByID(@Param("id") int id);
	 
}
