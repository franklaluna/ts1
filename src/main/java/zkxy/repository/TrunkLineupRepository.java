package zkxy.repository; 
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import zkxy.model.Trunk;
import zkxy.model.TrunkLineup;
import zkxy.vo.LineupTotelVO;
import zkxy.vo.LineupVO_Detail;

public interface TrunkLineupRepository { 

	/**
	 * 通过选择料库编号，返回当前库中整体排队情况!
	 * @param pdslevel_id
	 * @return
	 */
	@Select("SELECT lineup_status,COUNT(pdslevel_id)  AS num,pdslevel_id FROM trunk_lineup WHERE pdslevel_id=#{pdslevel_id} GROUP BY lineup_status,pdslevel_id;") 
	List<LineupVO_Detail> findStatusByPsdLevelId(@Param("pdslevel_id") int pdslevel_id);
 
	/**
	 * 通过用户筛查排队车辆
	 * @return
	 */
	@Select("SELECT b.* FROM trunk_lineup a,zkxy_trunk b WHERE a.bd_company=#{bd_company} AND a.trunk_id=b.ID") 
	List<Trunk> findLineupTrunksByCompany(@Param("bd_company") int bd_company);
	
	 
 
	@Select("SELECT b.* FROM trunk_lineup a,zkxy_trunk b WHERE  a.trunk_id=b.ID") 
	List<Trunk> findAllLineupTrunks();
	
	/**
	 *返回当前所有库整体排队情况! 
	 * @return
	 */
	@Select("SELECT lineup_status,COUNT(id)  AS num  FROM trunk_lineup GROUP BY lineup_status;") 
	List<LineupVO_Detail> findAllStatus();
	
	/**
	 *返回指定单位的总体排队情况 
	 * @return
	 */
	@Select("SELECT lineup_status,COUNT(id)  AS num  FROM trunk_lineup where bd_company=#{bd_company} GROUP BY lineup_status;") 
	List<LineupVO_Detail> findAllStatusWithCompany(@Param("bd_company") int bd_company);
	
	//
	@Select("SELECT COUNT(a.zzd_name) AS lineup_count,CONCAT(b.name,'-',a.zzd_name) AS zzd_name  "
			+ "FROM trunk_lineup a,zkxy_pdtlevel b  WHERE a.pdslevel_id=b.id"
			+ "  AND a.lineup_status<5 GROUP BY zzd_name,b.name  " )
	List<LineupTotelVO> findAllLineupCount();
	
	 
	@Select("SELECT a.* ,b.p_type FROM trunk_lineup a,zkxy_products b WHERE trunk_id=#{trunk_id} AND a.pds_id =b.id") 
	TrunkLineup findByTrunkId(@Param("trunk_id") int trunk_id);
	
	/**
	 * 返回指定状态和指定时间前的排队车辆的数量
	 * @param lineup_status
	 * @param updatetime
	 * @return
	 */
	@Select("SELECT COUNT(id) FROM trunk_lineup WHERE lineup_status=#{lineup_status} AND updatetime<#{updatetime}")
	int findNOByStatusAndTimes(@Param("lineup_status") int lineup_status,@Param("updatetime")  Date  updatetime);
	 
}
