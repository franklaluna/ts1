package zkxy.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zkxy.model.Trunk;

public interface TrunkRepository {

	@Select("SELECT a.* FROM zkxy_trunk a ,zkxy_user b WHERE b.car_id=a.ID  and  b.UserID=#{userId};")
	Trunk findByUser(@Param("userId") int userId);
	
	@Select("SELECT *  from  zkxy_trunk ")
	List<Trunk> findAll();
	
	@Select("select * from zkxy_trunk where ID=#{id}")
	Trunk findById(@Param("id") int id);

	@Update("update  zkxy_trunk set xpd=#{trunk.xpd},sbzz=#{trunk.sbzz},sbpd=#{trunk.sbpd} where ID=#{trunk.ID}")
	void updateTrunkStatus(@Param("trunk")Trunk trunk);
}
