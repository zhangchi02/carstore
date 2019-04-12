package com.huawei.carstore.user.service;

import com.huawei.carstore.model.User;

/**
 * @author zhangchi02
 * @date 2019年4月11日
 */
public interface UserController {

	public User login(User user, Integer workload);

	public User validate(User user);
}
