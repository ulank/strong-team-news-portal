package kz.ulank.strongteamnewsportal.repository;

import jakarta.validation.constraints.NotNull;
import kz.ulank.strongteamnewsportal.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.code = ?1")
    List<Role> findRolesByCode(@NotNull Role.RoleEnum code);

}