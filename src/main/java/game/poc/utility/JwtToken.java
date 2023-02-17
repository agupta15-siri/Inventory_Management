package game.poc.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtToken {


    // Key is hardcoded here for simplicity.
    // Ideally this will get loaded from env configuration/secret vault
    String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
            SignatureAlgorithm.HS256.getJcaName());

    public String generateToken(String userName, String email,String accountId) {
        return Jwts.builder()
                .claim("name", userName)
                .claim("email", email)
                .claim("accountId", accountId)
                .setSubject(email)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(3, ChronoUnit.MINUTES.MINUTES)))
                .signWith(hmacKey)
                .compact();
    }

    public void parseToken(String jwtString) {

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(jwtString);

    }
}
