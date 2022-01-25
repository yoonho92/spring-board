package test.board.dto.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignIn {
    private Long id;
    private String name;
    private State state;

    public enum State{
        SIGNIN,
        NOTEXIST,
        FAIL
    }
}
