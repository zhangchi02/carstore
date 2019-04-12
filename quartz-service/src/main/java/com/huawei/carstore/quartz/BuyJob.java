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

import com.huawei.carstore.model.Buy;
import com.huawei.carstore.model.Cart;
import com.huawei.carstore.model.User;
import com.huawei.carstore.product.service.ProductController;

@Component
public class BuyJob implements Job {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RpcReference(microserviceName = "product-service", schemaId = "product")
	private static ProductController productController;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Start excute Buy job. Current Thread Name is {}", Thread.currentThread().getName());
//		RestTemplate restTemplate = RestTemplateBuilder.create();
		User user = new User(0, "apm", "123456");
		Long[] productidList = new Long[] { 34211223411L };
		Cart cart = new Cart(productidList, user);

//		HttpHeaders headers = new HttpHeaders();
//		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//		headers.setContentType(type);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//
//		HttpEntity<Cart> req = new HttpEntity<Cart>(cart, headers);
//		String url = "cse://product-service/product/v0/buy";
//		restTemplate.postForObject(url, req, Cart.class);
		
		productController.buyItem(cart);
		logger.info("excute Buy job succeed");
	}

}
