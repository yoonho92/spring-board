package test.board.auth.dto;

import lombok.Data;

@Data
public class ReqForSign {
    private String name;
    private String secret;
}
