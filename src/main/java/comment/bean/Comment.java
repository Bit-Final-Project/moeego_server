package comment.bean;

import article.bean.Article;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import member.bean.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long commentNo; // 댓글 ID

    @ManyToOne
    @JoinColumn(name = "article_no", nullable = false)
    private Article article; // 게시글 참조

    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member member; // 작성자 참조

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @ToString.Exclude // 순환 참조 방지
    @JsonIgnore
    private Comment parent; // 부모 댓글 참조

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // 순환 참조 방지
    @JsonIgnore
    private List<Comment> children = new ArrayList<>(); // 대댓글 리스트

    @Column(length = 5000, nullable = false)
    private String content; // 댓글 내용

    @Column(name = "write_date", nullable = false)
    private LocalDateTime writeDate;

    @Column(name = "comment_status")
    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus = CommentStatus.DEFAULT;

    @PrePersist
    public void prePersist() {
        this.writeDate = LocalDateTime.now();
    }

}
