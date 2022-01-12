package test.board.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStatus {
    private Long id;
    private String name;
    private State state;

    public enum State{
        SIGNUP,
        SIGNING,
        EXIST,
        NOTEXIST,
        NONE,
        FAIL
    }
}
