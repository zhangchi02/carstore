package com.huawei.carstore.quartz;

import java.util.ArrayList;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.huawei.carstore.product.service.ProductController;

@Component
public class SearchJob implements Job {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RpcReference(microserviceName = "product-service", schemaId = "product")
	private static ProductController productController;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Start excute search job. Current Thread Name is {}", Thread.currentThread().getName());
//		RestTemplate restTemplate = RestTemplateBuilder.create();
//		String url = "cse://product-service/product/v0/search?productName=Bugatti Chiron";
//		restTemplate.getForObject(url, ArrayList.class);
		productController.getItemsByName("Bugatti Chiron");
		logger.info("excute search job succeed");
	}

}
