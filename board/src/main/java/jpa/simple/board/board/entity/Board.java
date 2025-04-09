package jpa.simple.board.board.entity;

import jakarta.persistence.*;
import jpa.simple.board.board.dto.request.UpdateBoardDTO;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Board extends BaseEntity {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "writer")
    private String writer;

    public void updateBoard(UpdateBoardDTO requestDTO) {
        this.title = requestDTO.getTitle();
        this.content = requestDTO.getContent();
        this.writer = requestDTO.getWriter();
    }
}
