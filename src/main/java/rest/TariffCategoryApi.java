package rest;

import domain.TariffCategory;
import interfaces.service.ITariffCategoryService;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/tariffcategory")
@Stateless
public class TariffCategoryApi {

	@Inject
	ITariffCategoryService tariffCategoryService;

	@POST
	@Path("/")
	@Consumes(APPLICATION_JSON)
	public Response createTariffCategory(TariffCategory tariffCategory) {
		try {
			tariffCategoryService.createTariffCategory(tariffCategory);
			return Response.ok().build();
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}

	@GET
	@Path("/{name}")
	@Produces(APPLICATION_JSON)
	public Response getTariffCategory(@PathParam("name") String name) {
		try {
			System.out.println(name);
			return Response.ok(tariffCategoryService.getTariffCategory(name)).build();
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build());
		}
	}

	@GET
	@Path("/")
	@Produces(APPLICATION_JSON)
	public Response getTariffCategories() {
		try {
			return Response.ok(tariffCategoryService.getTariffCategories()).build();
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build());
		}
	}

	@GET
	@Path("/vehicleId/{id}")
	@Produces(APPLICATION_JSON)
	public Response getTariffCategoryByVehicleId(@PathParam("id") long id) {
		try {
			return Response.ok(tariffCategoryService.getTariffCategoryByVehicleId(id)).build();
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build());
		}
	}
}
