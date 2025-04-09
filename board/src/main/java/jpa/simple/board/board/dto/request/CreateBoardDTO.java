package jpa.simple.board.board.dto.request;

import jpa.simple.board.board.entity.Board;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardDTO {
    private Long boardId;
    private String title;
    private String content;
    private String writer;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }
}
