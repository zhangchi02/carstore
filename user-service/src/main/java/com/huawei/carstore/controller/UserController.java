package com.huawei.carstore.controller;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.*;

import com.huawei.carstore.common.Workload;
import com.huawei.carstore.dao.service.PersistenceRestController;
import com.huawei.carstore.entity.User;

@RestSchema(schemaId = "user")
@RequestMapping("/user/v0")
public class UserController {

	@RpcReference(microserviceName = "dao-service", schemaId = "persistence")
	private PersistenceRestController persistence;

	private Workload works = new Workload();

	@PostMapping("/login")
	public User login(@RequestBody User user, @RequestParam(value = "workload", required = false) Integer workload) {
		if (null == workload) {
			works.doSomeWork();
		} else {
			works.doSomeWork(workload);
		}
		User validateUser = persistence.findUser(user);
		return validateUser;
	}

	@PostMapping("/validate")
	public User validate(@RequestBody User user) {
		return login(user, 1);
	}
}
