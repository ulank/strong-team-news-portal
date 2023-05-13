package kz.ulank.strongteamnewsportal.entity;

import jakarta.persistence.*;
import lombok.*;


/**
 * Created by Ulan on 5/12/2023
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "source", schema = "private")
public class Source {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

}
