package util.jwt;

import io.jsonwebtoken.Jwts;
import util.KeyGenerator;

import java.security.Key;

public class JWTUtility {
    /**
     * Get the subject of the JWT token
     * @param token JWT token
     * @return subject of the token
     */
    public static String getSubject(String token) {
        Key key = KeyGenerator.getInstance().getKey();
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }
}
