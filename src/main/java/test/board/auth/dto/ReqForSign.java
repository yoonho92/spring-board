package test.board.auth.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ReqForSign {
    private String name;
    private String secret;
    private OffsetDateTime date = OffsetDateTime.now();
}
