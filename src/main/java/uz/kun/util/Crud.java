package uz.kun.util;//User :Lenovo
//Date :10.06.2022
//Time :4:45
//Project Name :Kun.uz

import org.springframework.http.ResponseEntity;

public abstract class Crud <T,K,L>{

    public abstract ResponseEntity<?> create(K token,T dto);
     public abstract ResponseEntity<?> update(K token,T dto);
     public abstract ResponseEntity<?> read();
     public abstract ResponseEntity<?> delete(K token,L id);

}
