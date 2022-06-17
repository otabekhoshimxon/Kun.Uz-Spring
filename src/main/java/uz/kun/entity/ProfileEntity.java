package uz.kun.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "profile")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileRole role;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moderator")
    private List<ArticleEntity> articleList;

    public ProfileEntity() {
    }

    public ProfileEntity(Integer pId) {
    }
}
