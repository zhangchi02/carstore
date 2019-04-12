package com.huawei.carstore.quartz;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.huawei.carstore.model.User;
import com.huawei.carstore.user.service.UserController;

@Component
public class LoginJob implements Job {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RpcReference(microserviceName = "user-service", schemaId = "user")
	private static UserController userService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Start excute Login job. Current Thread Name is {}", Thread.currentThread().getName());
//		RestTemplate restTemplate = RestTemplateBuilder.create();
		User user = new User(0, "apm", "123456");
		
		userService.login(user,1);
//		HttpHeaders headers = new HttpHeaders();
//		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//		headers.setContentType(type);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//
//		HttpEntity<User> req = new HttpEntity<User>(user, headers);
//		String url = "cse://user-service/user/v0/login?workload=1000";
//
//		restTemplate.postForObject(url, req, User.class);

		logger.info("excute Login job succeed.");
	}

}
