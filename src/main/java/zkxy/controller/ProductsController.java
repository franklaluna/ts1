package zkxy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import zkxy.Role;
import zkxy.model.Product;
import zkxy.repository.ProductRepository;

@RestController
@Api(value = "产品api",description= "产品")
@RequestMapping("/Products")
public class ProductsController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@ApiOperation(value = "获取所有产品列表", notes = "获取所有产品列表") 
	@RequestMapping(value = "/List", method = RequestMethod.GET)
	@Role
	public @ResponseBody Map<String, Object> getProduct() throws Throwable {
		List<Product> products = productRepository.findAllProduct();
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("message", products);
		return map;
	}
}
