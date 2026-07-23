package NewProject.example.hospital.management.system.Security.PatientSecurity;


import NewProject.example.hospital.management.system.Entity.Patient;
import NewProject.example.hospital.management.system.Entity.Type.Roles;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Getter
@Setter
public class JWTService {

    private final SecretKey key;
    private final  long accessTelSeconds;
    private final long refreshTtlSeconds;
    private final String issuer;

    public JWTService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-ttl-seconds}") long accessTelSeconds,
            @Value("${security.jwt.refresh-ttl-seconds}")long refreshTtlSeconds,
            @Value("${security.jwt.issuer}") String issuer) {

        if(secret==null || secret.length()<64){
            throw  new IllegalArgumentException("Invalid secret");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTelSeconds = accessTelSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
        this.issuer = issuer;
    }
    public String generateAccessToken(Patient patient){
        Instant now = Instant.now();
        List<String> roles = patient.getRoles() == null
                ? List.of()
                : patient.getRoles()
                .stream()
                .map(Roles::getName)
                .toList();

        return Jwts.builder()

                .subject(patient.getId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessTelSeconds)))
                .claims(Map.of(
                        "email",patient.getEmail(),
                        "roles",roles,
                        "typ","access"
                ))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(Patient patient, String jti){
        Instant now = Instant.now();
        return Jwts.builder()
                .id(jti)
                .subject(patient.getId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(refreshTtlSeconds)))
                .claim("typ", "refresh")
                .signWith(key, SignatureAlgorithm.HS512) // Corrected from ES512 to HS512
                .compact();

    }

    public Jws<Claims> parse(String token){
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        }catch (JwtException e){
            throw e;
        }
    }

    public boolean issAccessToken(String token){
        Claims c = parse(token).getPayload();
        return "access".equals(c.get("typ")); // Corrected claim key to "typ"
    }

    public boolean isRefreshToken(String token){
        Claims c = parse(token).getPayload();
        return "refresh".equals(c.get("typ"));
    }

    public Long getUserId(String token) {
        Claims claims = parse(token).getPayload();
        return Long.parseLong(claims.getSubject());
    }

    public String getJti(String token){
        return parse(token).getPayload().getId();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token){
        Claims c = parse(token).getPayload();
        return (List<String>) c.get("roles");
    }
}
