package test.board.comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import test.board.auth.Account;
import test.board.post.Post;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Comment_ID")
    private Long id;
    private String author;
    private String content;
    private Boolean isDeleted = false;
    private Boolean isPresentParent = false;

    @Column(name = "CREATED_AT")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(name = "ACCOUNT_ID", insertable = false, updatable = false)
    private Long accountId;

    @JsonBackReference(value = "postAndComment")
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column(name = "POST_ID", insertable = false, updatable = false)
    private Long postId;

    @JsonManagedReference(value = "commentAndComment")
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.REMOVE)
    List<Comment> subComments = new ArrayList<>();

    @JsonBackReference(value = "commentAndComment")
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Comment parentComment;

    @Column(name = "PARENT_ID", insertable = false, updatable = false)
    private Long parentId;

}
