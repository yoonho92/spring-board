package test.board.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResForSignIn {
    private Long id;
    private String name;
    private State state;

    public enum State{
        SIGNIN,
        NOTEXIST,
        FAIL
    }
}
