package kz.ulank.strongteamnewsportal.integration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Created by Ulan on 5/13/2023
 */
@Validated
@ConfigurationProperties(prefix = "integration.newsapi")
public class NewsApiProperties {

    String apiKey;

    String url;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
