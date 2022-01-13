package test.board.post;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import test.board.auth.Account;
import test.board.comment.Comment;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "POST")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @Column(name = "ACCOUNT_ID", insertable = false, updatable = false)
    private Long accountId;

    private String title;
    private String author;
    private String content;

    @Column(name = "CREATED_AT")
    private OffsetDateTime date = OffsetDateTime.now();

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @JsonManagedReference(value = "postAndComment")
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

}