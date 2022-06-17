package uz.kun.util;
//User :Lenovo
//Date :15.06.2022
//Time :17:17
//Project Name :Kun.uz

import lombok.Data;
import uz.kun.dto.JwtDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.exps.NotPermissionException;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


public class HttpHeaderUtil {

    public static Integer getId(HttpServletRequest request, ProfileRole requiredRole)
    {
        JwtDTO jwtDTO = (JwtDTO) request.getAttribute("jwtDTO");
        Integer id = jwtDTO.getId();
        ProfileRole role = jwtDTO.getRole();
        if (Objects.isNull(id) && !requiredRole.equals(role))
        {
            throw new NotPermissionException("Not access");
        }

        return id;
    } public static Integer getId(HttpServletRequest request)
    {
       return getId(request,null);
    }




}
