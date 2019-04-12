package com.huawei.carstore;

import javax.ws.rs.core.Response.Status;

import org.apache.servicecomb.edge.core.AbstractEdgeDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * @author zhangchi02
 * @date 2019年4月8日
 */
public class WebPageDispatcher extends AbstractEdgeDispatcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebPageDispatcher.class);

	@Override
	public int getOrder() {
		return 2;
	}

	@Override
	public void init(Router router) {
		router.route("/*").failureHandler(this::onFailure)
				.handler(StaticHandler.create("static").setDefaultContentEncoding("UTF-8"));
	}

	protected void onFailure(RoutingContext context) {
		LOGGER.error("Route  failed,pls check the path : {}", context.normalisedPath());
		HttpServerResponse response = context.response();
		response.setStatusCode(Status.BAD_GATEWAY.getStatusCode());
		response.setStatusMessage(Status.BAD_GATEWAY.getReasonPhrase());
		response.end();
	}
}
