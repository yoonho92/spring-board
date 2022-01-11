package test.board.comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.board.post.Post;

import javax.persistence.*;
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
    private Long userId;
    private String author;
    private String content;
    private Boolean isDeleted = false;
    private Boolean isPresentParent = false;

    @Column(name = "PARENT_ID", insertable = false, updatable = false)
    private Long parentId;

    @Column(name = "POST_ID", insertable = false, updatable = false)
    private String postId;

    @JsonBackReference(value = "postAndComment")
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;


    @JsonManagedReference(value = "commentAndComment")
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    List<Comment> subComments = new ArrayList<>();

    @JsonBackReference(value = "commentAndComment")
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Comment parentComment;
}
