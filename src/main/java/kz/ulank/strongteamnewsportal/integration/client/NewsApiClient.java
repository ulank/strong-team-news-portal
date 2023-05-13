package kz.ulank.strongteamnewsportal.integration.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import kz.ulank.strongteamnewsportal.integration.response.EverythingResponse;
import kz.ulank.strongteamnewsportal.integration.response.TopHeadlinesResponse;


/**
 * Created by Ulan on 5/13/2023
 */
public interface NewsApiClient {

    @RequestLine("GET /everything?q={slug}&apiKey={apiKey}")
    @Headers("Content-Type: application/json")
    EverythingResponse everyThing(@Param String slug, @Param String apiKey);


    @RequestLine("GET /top-headlines?country={country}&apiKey={apiKey}")
    @Headers("Content-Type: application/json")
    TopHeadlinesResponse topHeadlines(@Param String country, @Param String apiKey);
}
