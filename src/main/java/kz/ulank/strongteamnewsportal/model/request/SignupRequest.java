package kz.ulank.strongteamnewsportal.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Created by Ulan on 5/12/2023
 */
@Getter
@Setter
@Builder
public class SignupRequest  {
    @NotNull
    @Size(min = 4, max = 24)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotNull
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;

    @NotNull
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @Email
    @NotNull
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank
    @NotNull
    private String password;
}
