package rest;

import com.pts62.common.finland.util.JsonExceptionMapper;
import domain.TariffCategory;
import interfaces.service.ITariffCategoryService;
import io.sentry.Sentry;
import service.TariffCategoryService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/tariffcategory")
@Stateless
public class TariffCategoryApi {

	@EJB
	TariffCategoryService tariffCategoryService;

	@POST
	@Path("/")
	@Consumes(APPLICATION_JSON)
	public Response createTariffCategory(TariffCategory tariffCategory) {
		try {
			tariffCategoryService.checkAndCreateTariffCategory(tariffCategory);
			return Response.ok().build();
		} catch (Exception e) {
			Sentry.capture(e);
			throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
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
			Sentry.capture(e);
			throw JsonExceptionMapper.mapException(Response.Status.NOT_ACCEPTABLE, e.getMessage());
		}
	}

	@GET
	@Path("/")
	@Produces(APPLICATION_JSON)
	public Response getTariffCategories() {
		try {
			return Response.ok(tariffCategoryService.getTariffCategories()).build();
		} catch (Exception e) {
			Sentry.capture(e);
			throw JsonExceptionMapper.mapException(Response.Status.NOT_ACCEPTABLE, e.getMessage());
		}
	}

	@GET
	@Path("/vehicleId/{id}")
	@Produces(APPLICATION_JSON)
	public Response getTariffCategoryByVehicleId(@PathParam("id") long id) {
		try {
			return Response.ok(tariffCategoryService.getTariffCategoryByVehicleId(id)).build();
		} catch (Exception e) {
			Sentry.capture(e);
			throw JsonExceptionMapper.mapException(Response.Status.NOT_ACCEPTABLE, e.getMessage());
		}
	}
}
