package zkxy.repository; 
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import zkxy.model.Product;

public interface ProductRepository {
 	
 
	@Select("select * from zkxy_products") 
	List<Product> findAllProduct();
 
	@Select("select * from zkxy_products where ID=#{id}") 
	Product findProductById(@Param("id")int  id);
}
