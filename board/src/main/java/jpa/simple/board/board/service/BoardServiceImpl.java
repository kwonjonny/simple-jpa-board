package jpa.simple.board.board.service;

import jakarta.validation.constraints.NotNull;
import jpa.simple.board.board.dto.request.CreateBoardDTO;
import jpa.simple.board.board.dto.request.UpdateBoardDTO;
import jpa.simple.board.board.dto.respose.BoardDTO;
import jpa.simple.board.board.dto.respose.ListBoardDTO;
import jpa.simple.board.board.entity.Board;
import jpa.simple.board.board.exception.error_enum.BoardErrorEnum;
import jpa.simple.board.board.repository.BoardRepository;
import jpa.simple.board.board.util.BoardPageRequestDTO;
import jpa.simple.board.board.util.BoardPageResponseDTO;
import jpa.simple.board.exception.service_exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public Long createBoard(@NotNull final CreateBoardDTO requestDTO) {
        final Board board = this.boardRepository.save(requestDTO.toEntity());
        return board.getBoardId();
    }

    @Override
    @Transactional(readOnly = true)
    public BoardDTO readBoard(@NotNull final Long boardId) {
        final Board board = this.boardRepository.findActiveById(boardId)
                .orElseThrow(() -> new ServiceException(BoardErrorEnum.NOT_FOUND_BOARD));
        return BoardDTO.fromEntity(board);
    }

    @Override
    @Transactional
    public Long updateBoard(@NotNull final UpdateBoardDTO requestDTO) {
        final Board board = this.boardRepository.findActiveById(requestDTO.getBoardId())
                .orElseThrow(() -> new ServiceException(BoardErrorEnum.NOT_FOUND_BOARD));
        board.updateBoard(requestDTO);
        return board.getBoardId();
    }

    @Override
    @Transactional
    public Long deleteBoard(@NotNull final Long boardId) {
        final Board board = this.boardRepository.findActiveById(boardId)
                .orElseThrow(() -> new ServiceException(BoardErrorEnum.NOT_FOUND_BOARD));
        board.updateUnActive();
        return board.getBoardId();
    }

    @Override
    @Transactional(readOnly = true)
    public BoardPageResponseDTO<ListBoardDTO> listBoard(@NotNull final BoardPageRequestDTO requestDTO) {
        return boardRepository.findAllByPage(requestDTO);
    }
}
