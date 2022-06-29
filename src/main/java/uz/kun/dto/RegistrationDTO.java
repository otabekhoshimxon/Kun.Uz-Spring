package uz.kun.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class RegistrationDTO {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Surname is required")
    @NotBlank(message = "Surname is required")
    private String surname;
    @Email(message = "Email is required")
    private String email;
   @Email(message = "Wrong phone number ",regexp = "^+998[0-9]{2}[0-9]{7}")
    private String phone;
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    private String password;

}