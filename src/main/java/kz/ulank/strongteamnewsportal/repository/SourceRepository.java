package kz.ulank.strongteamnewsportal.repository;

import kz.ulank.strongteamnewsportal.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SourceRepository extends JpaRepository<Source, String> {
    Optional<Source> findSourceByNameStartsWith(String name);
}