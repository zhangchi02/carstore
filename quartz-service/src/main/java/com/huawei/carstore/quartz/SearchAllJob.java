package com.huawei.carstore.quartz;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.huawei.carstore.model.Product;
import com.huawei.carstore.product.service.ProductController;
import com.huawei.carstore.user.service.UserController;

@Component
public class SearchAllJob implements Job {
	
//	private SearchAllJob(){
//		logger.info("-----------");
//	}
	
	private final Logger logger = LoggerFactory.getLogger(SearchAllJob.class);

	@RpcReference(microserviceName = "product-service", schemaId = "product")
	private static ProductController productController;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Start excute searchAll job. Current Thread Name is {}", Thread.currentThread().getName());
		productController.getItems();
//		RestTemplate restTemplate = RestTemplateBuilder.create();
//		String url = "cse://product-service/product/v0/searchAll";
//		restTemplate.getForObject(url, Product[].class);
		logger.info("excute searchAll job succeed");
	}

}
