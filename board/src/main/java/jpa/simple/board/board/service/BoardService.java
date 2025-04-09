package jpa.simple.board.board.service;

import jakarta.validation.constraints.NotNull;
import jpa.simple.board.board.dto.request.CreateBoardDTO;
import jpa.simple.board.board.dto.request.UpdateBoardDTO;
import jpa.simple.board.board.dto.respose.BoardDTO;
import jpa.simple.board.board.dto.respose.ListBoardDTO;
import jpa.simple.board.board.util.BoardPageRequestDTO;
import jpa.simple.board.board.util.BoardPageResponseDTO;

public interface BoardService {
    Long createBoard(CreateBoardDTO requestDTO);

    BoardDTO readBoard(Long boardId);

    Long updateBoard(UpdateBoardDTO requestDTO);

    Long deleteBoard(Long boardId);

    BoardPageResponseDTO<ListBoardDTO> listBoard(@NotNull final BoardPageRequestDTO requestDTO);

}
