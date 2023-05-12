package kz.ulank.strongteamnewsportal.repository;

import kz.ulank.strongteamnewsportal.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository extends JpaRepository<Source, String> {
}