package kz.ulank.strongteamnewsportal.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ulan on 5/12/2023
 */
@Getter
@Setter
public class SigninRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;

}
