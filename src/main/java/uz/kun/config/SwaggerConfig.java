package uz.kun.config;
//User :Lenovo
//Date :27.06.2022
//Time :17:54
//Project Name :Kun.uz

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

     @Bean
     public Docket swaggerConfiguration() {
          Set<String> consumes = new HashSet<>();
          consumes.add(MediaType.APPLICATION_JSON_VALUE);

          return new Docket(DocumentationType.SWAGGER_2)
                  .consumes(consumes)
                  .produces(consumes)
                  .select()
                  .apis(RequestHandlerSelectors.basePackage("uz.kun"))
                  .build()
                  .apiInfo(apiDetails());
     }
     private ApiInfo apiDetails() {
          return new ApiInfo("Kun.uz",
                  "Api documentation for kun.uz web site .",
                  "4.0",
                  "Beast team.",
                  new springfox.documentation.service.Contact("Kun.uz", "www.google.com", "otabekhoshimxon@gmail.com"),
                  "",
                  "",
                  Collections.emptyList()
          );
     }


}
