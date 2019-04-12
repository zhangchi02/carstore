package com.huawei.carstore.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.carstore.entity.Item;
import com.huawei.carstore.entity.Product;

/**
 * @author zhangchi02
 * @date 2019年4月8日
 */
public class ProductUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductUtils.class);

	private static final int DISCOUNT = 2000000;

	public List<Product> itemConverttoProduct(List<Item> itemList) {
		if (null == itemList) {
			return null;
		}
		LOGGER.info("itemList is: " + itemList);
		List<Product> productList = new ArrayList<Product>();
		for (Item item : itemList) {
			Product product = new Product(item.getId(), item.getName(), item.getPrice(), DISCOUNT, 0);
			productList.add(product);
		}
		return productList;
	}

}
