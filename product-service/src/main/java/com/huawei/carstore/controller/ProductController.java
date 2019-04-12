package com.huawei.carstore.controller;

import java.util.List;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huawei.carstore.dao.service.PersistenceRestController;
import com.huawei.carstore.entity.Cart;
import com.huawei.carstore.entity.Item;
import com.huawei.carstore.entity.Product;
import com.huawei.carstore.entity.User;
import com.huawei.carstore.user.service.UserController;
import com.huawei.carstore.utils.ProductUtils;

@RestSchema(schemaId = "product")
@RequestMapping("/product/v0")
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@RpcReference(microserviceName = "dao-service", schemaId = "persistence")
	private PersistenceRestController persistence;
	
	@RpcReference(microserviceName = "user-service", schemaId = "user")
	private UserController userService;

	private ProductUtils productUtils = new ProductUtils();

	@GetMapping("/search")
	public List<Product> getItemsByName(@RequestParam String productName) {
		List<Item> itemList = persistence.findProductsByName(productName);
		List<Product> productList = productUtils.itemConverttoProduct(itemList);
		return productList;
	}

	@GetMapping("/searchAll")
	public List<Product> getItems() {
		List<Item> itemList = persistence.findProducts();
		List<Product> productList = productUtils.itemConverttoProduct(itemList);
		return productList;
	}

	@PostMapping("/buy")
	public Boolean buyItem(@RequestBody Cart cart) {
		Boolean result = false;
		Long[] list = cart.getProductidList();
		User user = cart.getUser();
		LOGGER.info("productList is: " + list);
		User validateuser = userService.validate(user);
		if (null != validateuser) {
			Long userId = user.getId();
			for (Long productId : list) {
				result = persistence.addCart(userId, productId);
				LOGGER.info(user.getName() + " bought " + productId + " result: " + result);
			}
			return result;
		}
		LOGGER.error("unvalidate user!!");
		return result;
	}
}
