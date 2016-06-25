package com.electrocucaracha.apps.cdp.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.electrocucaracha.apps.cdp.entities.CategoryEntity;
import com.electrocucaracha.apps.cdp.errorhandling.InvalidInputException;
import com.electrocucaracha.apps.cdp.models.CategoryModel;
import com.electrocucaracha.apps.cdp.services.CategoryService;

@Path("/categories")
public class CategoryResourceImpl implements CategoryResource{

	@Autowired
	private CategoryService categoryService;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") long id) {
		if (id < 0) {
			return Response.serverError().entity("ID cannot be negative").build();
		}
		CategoryEntity entity = categoryService.get(id);

		if (entity == null)
			return Response.status(Response.Status.NOT_FOUND).entity("Category not found for ID: " + id).build();
		CategoryModel model = new CategoryModel(entity);
		return Response.ok(model.toJson(), MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response create(CategoryEntity category) {

		long createCategoryId;
		try {
			createCategoryId = categoryService.create(category);
		} catch (InvalidInputException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.status(Response.Status.CREATED).entity("A new category has been created")
				.header("Location", "http://localhost:8080/api/categories/" + String.valueOf(createCategoryId)).build();
	}
}
