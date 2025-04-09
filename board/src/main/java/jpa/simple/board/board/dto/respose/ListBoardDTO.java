package jpa.simple.board.board.dto.respose;

import jpa.simple.board.board.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ListBoardDTO {
    private Long boardId;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static ListBoardDTO fromEntity(Board board) {
        return ListBoardDTO.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .build();
    }
}
