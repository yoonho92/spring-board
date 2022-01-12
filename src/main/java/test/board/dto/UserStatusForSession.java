package test.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStatusForSession {
    private Long accountId;
    private String name;
    private State state;

    public enum State{
        SIGNING,
        NONE
    }
}
