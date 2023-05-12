package kz.ulank.strongteamnewsportal.integration.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Ulan on 5/13/2023
 */
@Getter
@Setter
public class EverythingResponse {
    private String status;
    private int totalResults;
    private List<Articles> articles;

    @Getter
    @Setter
    public static class Articles {
        private Source source;
        private String author;
        private String title;
        private String description;
        private String content;
        private String url;
        private String urlToImage;
        private String publishedAt;

        @Getter
        @Setter
        public static class Source {
            private String id;
            private String name;
        }
    }

}
