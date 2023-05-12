package kz.ulank.strongteamnewsportal.repository;

import kz.ulank.strongteamnewsportal.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findTopicByNameStartsWith(String name);

}