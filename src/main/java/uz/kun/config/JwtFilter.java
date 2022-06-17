package uz.kun.config;

import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import uz.kun.dto.JwtDTO;
import uz.kun.util.JWTUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//User :Lenovo
//Date :15.06.2022
//Time :16:34
//Project Name :Kun.uz
@Component

public class JwtFilter  extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;//so'rov
        final HttpServletResponse response = (HttpServletResponse) servletResponse;//javob

       final String authorization = request.getHeader("Authorization");

       if (authorization.isEmpty() || authorization.isBlank() || !authorization.startsWith("Bearer"))
       {
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           response.setHeader("Message","Not found token");
           return;
       }


        try {



            String[] jwtArray = authorization.split(" ");
            if (jwtArray.length!=2)
            {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("Message","Not found token");
                return;
            }
            String jwt = jwtArray[1];

            JwtDTO jwtDTO = JWTUtil.decodeJwtDTO(jwt);//tokendan dto olindi
            request.setAttribute("jwtDTO",jwtDTO);
            filterChain.doFilter(request,response);
        } catch (JwtException e) {
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           e.printStackTrace();
           return;
        }

    }
}
