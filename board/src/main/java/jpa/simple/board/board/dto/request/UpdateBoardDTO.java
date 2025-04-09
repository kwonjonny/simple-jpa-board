package jpa.simple.board.board.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardDTO {
    private Long boardId;
    private String title;
    private String content;
    private String writer;
}
