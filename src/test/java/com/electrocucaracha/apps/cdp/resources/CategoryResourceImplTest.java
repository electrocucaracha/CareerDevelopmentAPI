package com.electrocucaracha.apps.cdp.resources;

import static com.jayway.restassured.RestAssured.expect;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CategoryResourceImplTest {
	private Server server;

	@Before
	public void setUp() throws Exception {
		server = new Server(8080);

		WebAppContext context = new WebAppContext();
		context.setDescriptor("/webapp/WEB-INF/web.xml");
		context.setResourceBase("/src/main/java");
		context.setContextPath("/");
		context.setParentLoaderPriority(true);
		server.setHandler(context);

		server.start();
	}

	@After
	public void tearDown() throws Exception {
		if (server != null && server.isStarted()) {
			server.stop();
		}
	}

	@Test
	public void testUserNotFound() {
		expect().statusCode(404).when().get("/categories/666");
	}
}
