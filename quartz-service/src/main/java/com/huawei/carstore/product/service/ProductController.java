package com.huawei.carstore.product.service;

import java.util.List;

import com.huawei.carstore.model.Cart;
import com.huawei.carstore.model.Product;

/**
* @author zhangchi02
* @date 2019年4月11日
*/
public interface ProductController {

	public List<Product> getItemsByName(String productName);

	public List<Product> getItems();

	public Boolean buyItem(Cart cart);
}
