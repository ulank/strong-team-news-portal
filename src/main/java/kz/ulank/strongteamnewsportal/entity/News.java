package kz.ulank.strongteamnewsportal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Ulan on 5/12/2023
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news", schema = "private")
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "news_topic", schema = "private", joinColumns = {
            @JoinColumn(name = "news_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "topic_id", referencedColumnName = "id")})
    private List<Topic> topics;

    @Column(name = "url")
    private String url;

    @Column(name = "url_to_image")
    private String urlToImage;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Source source;

    @Column(name = "published", columnDefinition = "default false")
    private boolean published;

    @Column(name = "published_at", columnDefinition = "TIMESTAMP")
    private ZonedDateTime publishedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        News news = (News) o;
        return getId() != null && Objects.equals(getId(), news.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
