package kz.ulank.strongteamnewsportal.integration.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Ulan on 5/13/2023
 */
@Getter
@Setter
public class TopHeadlinesResponse {
    private String status;
    private int totalResults;
    private List<Articles> articles;


}
