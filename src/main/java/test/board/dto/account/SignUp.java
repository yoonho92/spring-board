package test.board.dto.account;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class SignUp {
    private Long accountId;
    private String name;
    private State state;
    private OffsetDateTime date;

    public enum State{
        SIGNUP,
        EXIST
    }
}
