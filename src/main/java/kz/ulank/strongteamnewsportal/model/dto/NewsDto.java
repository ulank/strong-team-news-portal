package kz.ulank.strongteamnewsportal.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Ulan on 5/12/2023
 */

@Getter
@Setter
@NoArgsConstructor
public class NewsDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String description;

    @NotBlank
    private String author;

    private String url;

    private String urlToImage;

    private List<TopicDto> topics;

    private SourceDto source;

}
