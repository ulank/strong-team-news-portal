package kz.ulank.strongteamnewsportal.repository;

import kz.ulank.strongteamnewsportal.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
    Optional<News> findByTitleStartsWith(String title);

    List<News> findNewsBySourceId(String sourceId);

    List<News> findNewsBySourceName(String name);
}