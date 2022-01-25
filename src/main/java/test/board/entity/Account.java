package test.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    public Long id;

    private String name;
    private String secret;

    @Column(name = "CREATED_AT")
    private OffsetDateTime createdAt = OffsetDateTime.now();
}