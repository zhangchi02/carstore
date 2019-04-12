package com.huawei.carstore;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;

public class EdgeApplication {

	public static void main(String[] args) throws Exception {
		System.setProperty("vertx.disableFileCPResolving", "false");
		Log4jUtils.init();
		BeanUtils.init();
	}
}
