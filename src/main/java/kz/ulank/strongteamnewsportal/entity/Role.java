package kz.ulank.strongteamnewsportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by Ulan on 5/12/2023
 */
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "role", schema = "private")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "code", length = 15)
    private RoleEnum code;

    @Column(name = "description")
    private String description;

    public enum RoleEnum {
        ROLE_USER
    }

}
