package com.huawei.carstore.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Workload {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private long delay = 10;

	public void updateDelay(long delay) {
		this.delay = delay;
		logger.info("delay changed to " + delay);
	}

	public void doSomeWork() {
		try {
			if (delay != 0) {
				logger.info("Throttler ->The work takes " + delay + "  millisec");
			}
			Thread.sleep(delay);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	public void doSomeWork(Integer num) {
		try {
			if (num > 0) {
				logger.info("Throttler ->The work takes " + num + "  millisec");
			}
			Thread.sleep(num);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
}
