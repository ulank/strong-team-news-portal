package kz.ulank.strongteamnewsportal.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Ulan on 5/12/2023
 */
@Getter
@Setter
@NoArgsConstructor
public class TopicDto {
    @NotNull
    @NotBlank
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
