package com.huawei.carstore.dao.service;

import java.util.List;

import com.huawei.carstore.entity.Item;
import com.huawei.carstore.entity.User;

/**
 * @author zhangchi02
 * @date 2019年3月15日
 */
public interface PersistenceRestController {
	Boolean setPersistanceError(Boolean isSet);

	Item buyProduct(long id);

	List<Item> findProductsByName(String text);

	List<Item> findProducts();

	User findUser(User user);

	Boolean addCart(long userId, long productId);
}
