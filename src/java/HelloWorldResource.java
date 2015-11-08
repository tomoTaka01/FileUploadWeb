
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/helloworld")
public class HelloWorldResource {

    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    public String getMessage(){
        return "Hello World";
    }
}
