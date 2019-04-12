package com.huawei.carstore.controller;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huawei.carstore.entity.Item;
import com.huawei.carstore.entity.User;
import com.huawei.carstore.mysql.DbManager;

import java.util.List;

@RestSchema(schemaId = "persistence")
@RequestMapping("/persistence/v0")
public class PersistanceRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistanceRestController.class);

	private DbManager manager = DbManager.getDbManager();

	@RequestMapping(value = "/persistenceError", method = RequestMethod.PUT)
	public Boolean setPersistanceError(@RequestParam(value = "isSet") Boolean isSet) {
		manager.setPersistenceError(isSet);
		return isSet;
	}

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public Item buyProduct(@PathVariable long id) {
		Item item;
		LOGGER.info("buyProduct {}.", id);
		item = manager.searchItem(id);
		LOGGER.info("item is: {}.", item);
		if (item == null) {
			LOGGER.error("didn't find product: {}.", item);
		}
		return item;
	}

	@RequestMapping(value = "/products_by_name", method = RequestMethod.GET)
	public List<Item> findProductsByName(@RequestParam(value = "name") String text) {
		List<Item> itemList = null;
		LOGGER.info("trying to find product: {}.", text);
		itemList = manager.findProductsByName(text);
		if (itemList == null || itemList.isEmpty()) {
			LOGGER.error("didn't find any product of {}.", text);
		}
		return itemList;
	}

	/**
	 * 查询所有商品
	 * @return
	 */
	@RequestMapping(path = "/products", method = RequestMethod.GET)
	public List<Item> findProducts() {
		List<Item> itemList = null;
		LOGGER.info("trying to find all products.");
		itemList = manager.findAllProducts();
		if (itemList == null || itemList.isEmpty()) {
			LOGGER.error("didn't find any product.");
		}
		return itemList;
	}

	/**
	 * 查询用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public User findUser(@RequestBody User user) {
		LOGGER.info("trying to find user: {}.", user.getName());
		List<User> users = manager.findUsers(user);
		if (users == null || users.isEmpty()) {
			LOGGER.error("didn't find user: {}.", user.getName());
		}
		User rsUser = users.get(0);
		LOGGER.info("find user: {}.", user.getName());
		return rsUser;
	}

	/**
	 * 购买商品
	 * @param userId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/payment/{userId}/{productId}", method = RequestMethod.GET)
	public Boolean addCart(@PathVariable("userId") long userId, @PathVariable("productId") long productId) {
		Boolean flag = false;
		LOGGER.info(userId + " add " + productId + " to cart.");
		flag = manager.addPayment(userId, productId);
		return flag;
	}
}