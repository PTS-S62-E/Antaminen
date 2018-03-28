package rest;



import io.sentry.Sentry;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Stateless
@Path("test")
public class TestApi {

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String HelloWorld() {
        return "Hello, World!";
    }

    @GET
    @Path("/error")
    @Produces(MediaType.TEXT_PLAIN)
    public String generateError() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("Hello, Error");

        try {
            return temp.get(2);
        } catch(IndexOutOfBoundsException ex) {
            Sentry.capture(ex);
        }

        return "Hello";
    }

}
