package uz.kun.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import uz.kun.enums.ProfileRole;
import uz.kun.exps.BadRequestException;

import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "someKeyWord";

    public static String encode(Integer id) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date()); // 18:58:00
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + ((60 * 60 * 1000)+12))); // 19:58:00
        jwtBuilder.setIssuer("Mazgi production");
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.claim("id", id);

        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public static Integer decode(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        Integer id = (Integer) claims.get("id");
        return id;
    }

    public static String encode(Integer id, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date()); // 18:58:00
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + ((60 * 60 * 1000)+12))); // 19:58:00
        jwtBuilder.setIssuer("Mazgi production");
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.claim("id", id);
        jwtBuilder.claim("role", role.name());

        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public static Integer decode(String token, ProfileRole requiredRole) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        Integer id = (Integer) claims.get("id");
        String role = (String) claims.get("role");

        if (!requiredRole.equals(ProfileRole.valueOf(role))) {
            throw new BadRequestException("Not Access");
        }
        return id;
    }

}
