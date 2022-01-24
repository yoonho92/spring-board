package test.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusForSession {
    private Long accountId;
    private String name;
    private State state = State.NONE;

    public enum State{
        SIGNING,
        NONE
    }
}
