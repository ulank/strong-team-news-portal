package kz.ulank.strongteamnewsportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ulan on 5/12/2023
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news", schema = "private")
@ToString
public class News {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "description")
    private String description;

    @Column(name = "author")
    private String author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "news_topic", schema = "private", joinColumns = {
            @JoinColumn(name = "news_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "topic_id", referencedColumnName = "id")})
    private List<Topic> topics;

    @Column(name = "url")
    private String url;

    @Column(name = "url_to_image")
    private String urlToImage;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    private Source source;

    @Column(name = "published_at", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private ZonedDateTime publishedAt;

}
