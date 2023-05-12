package kz.ulank.strongteamnewsportal.integration.config;

import kz.ulank.strongteamnewsportal.integration.properties.NewsApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ulan on 5/13/2023
 */
@Configuration
@EnableConfigurationProperties(NewsApiProperties.class)
public class NewsApiConfig {
    private final NewsApiProperties newsApiProperties;

    public NewsApiConfig(NewsApiProperties newsApiProperties) {
        this.newsApiProperties = newsApiProperties;
    }

    public NewsApiProperties getNewsApiProperties() {
        return newsApiProperties;
    }
}
