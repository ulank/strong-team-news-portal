package kz.ulank.strongteamnewsportal.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Ulan on 5/12/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SourceDto {
    @NotNull
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String url;
}
