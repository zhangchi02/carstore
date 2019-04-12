package com.huawei.carstore.controller;

import java.util.List;
import java.util.Map;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.huawei.carstore.model.AutoTestInfo;
import com.huawei.carstore.model.AutotestConf;
import com.huawei.carstore.model.Product;
import com.huawei.carstore.model.UserAutoTestConf;
import com.huawei.carstore.product.service.ProductController;
import com.huawei.carstore.quartz.BuyJob;
import com.huawei.carstore.quartz.LoginJob;
import com.huawei.carstore.quartz.QuartzManager;
import com.huawei.carstore.quartz.SearchAllJob;
import com.huawei.carstore.quartz.SearchJob;

@RestSchema(schemaId = "quartz")
@RequestMapping("/quartz/v0")
public class QuartzController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@RpcReference(microserviceName = "product-service", schemaId = "product")
	private ProductController productController;
	
	@RequestMapping(value = "/autotest/set", method = RequestMethod.POST)
	public Boolean setAutoTest(@RequestBody AutotestConf autotestConf) {
		LOGGER.info("{}", autotestConf);
		UserAutoTestConf.instance().setUrlSwitch(autotestConf);

		QuartzManager.shutdownJobs();
		Map<String, AutoTestInfo> map = UserAutoTestConf.instance().getUrlSwitch();

		AutoTestInfo loginInfo = map.get("/user/login");
		AutoTestInfo BuyInfo = map.get("/product/buy");
		AutoTestInfo searchInfo = map.get("/product/search");
		AutoTestInfo searchAllInfo = map.get("/product/searchAll");

		if (1 == loginInfo.getTestSwitch()) {
			QuartzManager.addJob("login", "login", "login", "login", LoginJob.class,
					"0/" + loginInfo.getPeriod() + " * * * * ?");
		}
		if (1 == BuyInfo.getTestSwitch()) {
			QuartzManager.addJob("buy", "buy", "buy", "buy", BuyJob.class, "0/" + BuyInfo.getPeriod() + " * * * * ?");
		}
		if (1 == searchInfo.getTestSwitch()) {
			QuartzManager.addJob("search", "search", "search", "search", SearchJob.class,
					"0/" + searchInfo.getPeriod() + " * * * * ?");
		}
		if (1 == searchAllInfo.getTestSwitch()) {
			QuartzManager.addJob("searchAll", "searchAll", "searchAll", "searchAll", SearchAllJob.class,
					"0/" + searchAllInfo.getPeriod() + " * * * * ?");
		}
		return true;
	}

	@RequestMapping(value = "/autotest/get", method = RequestMethod.GET)
	public Map<String, AutoTestInfo> getAutoTest() {
		return UserAutoTestConf.instance().getUrlSwitch();
	}
}
