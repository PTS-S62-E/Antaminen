package rest;



import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("test")
public class TestApi {

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String HelloWorld() {
        return "Hello, World!";
    }

}
