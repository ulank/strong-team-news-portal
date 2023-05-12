package kz.ulank.strongteamnewsportal.util.model;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * Created by Ulan on 5/13/2023
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Pagination {

    @Size(min = 1)
    private int page;
    @Size(max = 100)
    private int pageSize;
    private List<?> articles;
    private int totalResults;
}
