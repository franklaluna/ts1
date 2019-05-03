package zkxy.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zkxy.model.Company;

public interface CompanyRepository {

	/**
	 * 查询所有非场内单位
	 * 
	 * @return
	 */
	@Select("select * from zkxy_companys where c_type <>'厂内单位'")
	List<Company> findAllCustom();

	/**
	 * 查询所有场内单位
	 * 
	 * @return
	 */
	@Select("select * from zkxy_companys where c_type ='厂内单位'")
	List<Company> findAllAdmin();

	@Select("SELECT * FROM zkxy_companys WHERE id=#{id}")
	Company findById(@Param("id") int id);

	
}
