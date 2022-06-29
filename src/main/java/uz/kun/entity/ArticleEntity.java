package uz.kun.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import uz.kun.enums.ArticleStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@ToString
@Table(name = "article")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;


    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = true, name = "view_count")
    private Integer viewCount;

    @Column(nullable = true, name = "shared_count")
    private Integer sharedCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatus status=ArticleStatus.NOT_PUBLISHED;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = true, name = "publish_date")
    private LocalDateTime publishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id")
    private ProfileEntity moderator;

    @JoinColumn(name = "publisher_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProfileEntity publisher;

    @JoinColumn(name = "region_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RegionEntity region;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;


    @JoinColumn(name = "attach_id")
    @OneToOne
    private AttachEntity image;

    public ArticleEntity() {
    }

    public ArticleEntity(String articleId) {
        this.id=articleId;
    }

    public ArticleEntity(String id, String title, String description, LocalDateTime publishDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
    }
}
