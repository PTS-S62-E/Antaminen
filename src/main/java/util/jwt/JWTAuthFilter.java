package util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import util.KeyGenerator;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.util.logging.Logger;

@Provider
@JWTRequired
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String token = containerRequestContext.getHeaderString("Authorization");
        if(token == null || token.isEmpty()) {
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

        try {
            // Try to validate the token
            Key key = KeyGenerator.getInstance().getKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);

        } catch (SignatureException | NullPointerException | IllegalArgumentException e) {
            // Token not valid
            e.printStackTrace();
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }



    }
}
