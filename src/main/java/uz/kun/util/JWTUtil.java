package uz.kun.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import uz.kun.enums.ProfileRole;
import uz.kun.exception.BadRequestException;

import java.time.Instant;
import java.util.Date;

public class JWTUtil {

    private  final static String KEY_SECURITY="secret_key";
    public static String encode(Integer id)
    {

        //optional - ixtiyoriy
        JwtBuilder jwtBuilder= Jwts.builder();

        jwtBuilder.setIssuedAt(Date.from(Instant.now()));//18:58:00  //start
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis()+(60*60*1000)));//19:58:00 //end
        jwtBuilder.setIssuer("Ok");//creator //majburiy emas
        jwtBuilder.signWith(SignatureAlgorithm.HS256,KEY_SECURITY);//encode algorithm and secret key
        jwtBuilder.claim("id",id);//user id
        String compact = jwtBuilder.compact();
        return compact;

    }

    public static String encode(Long id, ProfileRole role)
    {

        //optional - ixtiyoriy
        JwtBuilder jwtBuilder= Jwts.builder();
        jwtBuilder.setIssuedAt(Date.from(Instant.now()));//18:58:00  //start
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis()+(60*60*1000)));//19:58:00 //end
        jwtBuilder.setIssuer("Ok");//creator //majburiy emas
        jwtBuilder.signWith(SignatureAlgorithm.HS256,KEY_SECURITY);//encode algorithm and secret key
        jwtBuilder.claim("id",id);//user id
        jwtBuilder.claim("role",role.name());//user id
        String compact = jwtBuilder.compact();
        return compact;

    }

    public static Long decode(String token)
    {
        Claims claims=Jwts.parser()
                .setSigningKey(KEY_SECURITY)
                .parseClaimsJws(token)
                .getBody();
        return (Long) claims.get("id");

    } public static Long decode(String token,ProfileRole role)
    {
        Claims claims=Jwts.parser()
                .setSigningKey(KEY_SECURITY)
                .parseClaimsJws(token)
                .getBody();
        Long id = (Long) claims.get("id");
        ProfileRole role1 = (ProfileRole) claims.get("role");

        if (!role.equals(role1))
        {
            throw new BadRequestException("Not Access");
        }


    }

}
