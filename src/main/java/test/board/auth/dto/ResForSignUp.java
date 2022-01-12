package test.board.auth.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ResForSignUp {
    private Long accountId;
    private String name;
    private State state;
    private OffsetDateTime date;

    public enum State{
        SIGNUP,
        EXIST
    }
}
