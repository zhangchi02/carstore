package com.huawei.carstore.model;

import java.util.Map;

public class AutotestConf {
	private Map<String, AutoTestInfo> confMap;

	public Map<String, AutoTestInfo> getConfMap() {
		return confMap;
	}

	public void setConfMap(Map<String, AutoTestInfo> confMap) {
		this.confMap = confMap;
	}

	@Override
	public String toString() {
		return "AutotestConf [confMap=" + confMap + "]";
	}
}
