package zkxy.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zkxy.model.Amount;

public interface AmountRepository {

	/**
	 * 查询指定购货单位在各个场内单位中的余额信息。
	 * 
	 * @return
	 */
	@Select("SELECT a.* ,b.c_name FROM zkxy_amount a,zkxy_companys b WHERE gou_id=#{gou_id} and a.gong_id=b.ID")
	List<Amount> findAmountByGouId(@Param("gou_id") int gou_id);
	
	
	/**
	 * 查询指定供货单位关联的所有客户单位的余额信息。
	 * 
	 * @return
	 */
	@Select("SELECT a.* ,b.c_name FROM zkxy_amount a,zkxy_companys b WHERE  gong_id=#{gong_id} and a.gou_id=b.ID")
	List<Amount> findAmountByGongId(@Param("gong_id") int gong_id);
	
	/**
	 * 根据供货单位编号和购货单位编号，查询余额信息
	 * @param gou_id
	 * @param gong_id
	 * @return
	 */
	@Select("select * from zkxy_Amount where  gou_id=#{gou_id} and gong_id=#{gong_id};")
	Amount findAmountByGouIdAndGongId(@Param("gou_id") int gou_id,@Param("gong_id") int gong_id);
	
	 /**
	 * 充值  单位（元）
	 */
	@Update("UPDATE zkxy_Amount SET c_balance=c_balance+#{amount},remark=#{remark},update_time=GETDATE() WHERE gou_id=#{gou_id} and gong_id=#{gong_id};")
	void recharge (@Param("gou_id") int gou_id,@Param("gong_id") int gong_id,@Param("amount") double amount,@Param("remark") String remark);


}
