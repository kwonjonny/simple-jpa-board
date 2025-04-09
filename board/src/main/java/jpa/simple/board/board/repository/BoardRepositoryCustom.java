package jpa.simple.board.board.repository;

import jpa.simple.board.board.dto.respose.ListBoardDTO;
import jpa.simple.board.board.entity.Board;
import jpa.simple.board.board.util.BoardPageRequestDTO;
import jpa.simple.board.board.util.BoardPageResponseDTO;

import java.util.Optional;

public interface BoardRepositoryCustom {

    Optional<Board> findActiveById(Long boardId);

    BoardPageResponseDTO<ListBoardDTO> findAllByPage(BoardPageRequestDTO requestDTO);

}
