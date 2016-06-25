package com.electrocucaracha.apps.cdp.resources;

import javax.ws.rs.core.Response;

public interface BaseResource<T> {
	public Response get(long id);
	
	public Response create(T entity);
}
