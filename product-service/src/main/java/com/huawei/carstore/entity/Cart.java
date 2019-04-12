package com.huawei.carstore.entity;

/**
 * @author zhangchi02
 * @date 2019年4月8日
 */
public class Cart {

	private Long[] productidList;
	private User user;

	public Long[] getProductidList() {
		return productidList;
	}

	public void setProductidList(Long[] productidList) {
		this.productidList = productidList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Cart(Long[] productidList, User user) {
		super();
		this.productidList = productidList;
		this.user = user;
	}

	public Cart() {
		super();
	}
}
