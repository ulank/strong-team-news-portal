package kz.ulank.strongteamnewsportal.integration.service;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import kz.ulank.strongteamnewsportal.integration.client.NewsApiClient;
import kz.ulank.strongteamnewsportal.integration.config.NewsApiConfig;
import kz.ulank.strongteamnewsportal.integration.enums.EverythingLang;
import kz.ulank.strongteamnewsportal.integration.response.EverythingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by Ulan on 5/13/2023
 */
@Service
@RequiredArgsConstructor
public class NewsApiService {

    private final NewsApiConfig newsApiConfig;

    public EverythingResponse getEverythingBySlug(String slug) {
        NewsApiClient newsApiResource = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(NewsApiClient.class, newsApiConfig.getNewsApiProperties().getUrl());
        return newsApiResource.everyThing(slug, newsApiConfig.getNewsApiProperties().getApiKey());
    }


}
