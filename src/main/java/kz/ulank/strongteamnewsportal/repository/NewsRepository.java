package kz.ulank.strongteamnewsportal.repository;

import kz.ulank.strongteamnewsportal.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
}