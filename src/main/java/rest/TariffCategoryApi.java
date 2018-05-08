package rest;

import com.fasterxml.jackson.databind.JsonNode;
import domain.Account;
import domain.Owner;
import domain.Ownership;
import domain.TariffCategory;
import exceptions.AccountException;
import interfaces.dao.ITariffCategoryDao;
import interfaces.service.ITariffCategoryService;
import io.sentry.Sentry;
import util.HashUtility;
import util.jwt.JWTUtility;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

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
	public Response getVehicleOwnerships(@PathParam("name") String name) {
		try {
			return Response.ok(tariffCategoryService.getTariffCategory(name)).build();
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build());
		}

	}

	@GET
	@Path("/")
	@Produces(APPLICATION_JSON)
	public Response getVehicleOwnerships() {
		try {
			return Response.ok(tariffCategoryService.getTariffCategories()).build();
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build());
		}

	}
}
