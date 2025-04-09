package jpa.simple.board.board.repository;

import jpa.simple.board.board.dto.request.CreateBoardDTO;
import jpa.simple.board.board.dto.request.UpdateBoardDTO;
import jpa.simple.board.board.dto.respose.BoardDTO;
import jpa.simple.board.board.dto.respose.ListBoardDTO;
import jpa.simple.board.board.entity.Board;
import jpa.simple.board.board.util.BoardPageRequestDTO;
import jpa.simple.board.board.util.BoardPageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@SpringBootTest
public class BoardRepositoryImplTest {

    @Autowired(required = false)
    private BoardRepository boardRepository;

    private CreateBoardDTO createBoardDTO;
    private UpdateBoardDTO updateBoardDTO;

    @BeforeEach
    public void setUp() {
        createBoardDTO = CreateBoardDTO.builder()
                .title("title")
                .writer("writer")
                .content("content")
                .build();

        updateBoardDTO = UpdateBoardDTO.builder()
                .title("updateTitle")
                .writer("updateWriter")
                .content("updateContent")
                .build();
    }

    @Test
    @Transactional
    void pagingTest() {
        for (int i = 1; i <= 20; i++) {
            Board board = Board.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .writer("Writer " + (i % 3))
                    .build();
            boardRepository.save(board);
        }

        BoardPageRequestDTO requestDTO = BoardPageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("title,content")
                .keyword("Title") // 이 키워드 포함된 데이터 검색
                .build();

        BoardPageResponseDTO<ListBoardDTO> responseDTO = boardRepository.findAllByPage(requestDTO);
        Assertions.assertNotNull(responseDTO);
        log.info("페이징 결과 리스트: {}", responseDTO.getList());
        log.info("총 게시글 수: {}", responseDTO.getTotal());
    }


    @Test
    @Transactional
    void createBoardTest() {
        final Board board = boardRepository.save(createBoardDTO.toEntity());

        Assertions.assertAll(
                () -> Assertions.assertNotNull(board.getBoardId()),
                () -> Assertions.assertNotNull(board.getTitle()),
                () -> Assertions.assertNotNull(board.getContent()),
                () -> Assertions.assertNotNull(board.getWriter())
        );

        log.info("createBoard: {}", board);
    }


    @Test
    @Transactional
    void readBoardTest() {
        final Board board = boardRepository.save(createBoardDTO.toEntity());

        Assertions.assertAll(
                () -> Assertions.assertNotNull(board.getBoardId()),
                () -> Assertions.assertNotNull(board.getTitle()),
                () -> Assertions.assertNotNull(board.getContent()),
                () -> Assertions.assertNotNull(board.getWriter())
        );

        final Board readBoard = boardRepository.findActiveById(board.getBoardId())
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        final BoardDTO boardDTO = BoardDTO.fromEntity(readBoard);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(boardDTO)
        );
        log.info("readBoard: {}", boardDTO);
    }


    @Test
    @Transactional
    void updateBoardTest() {
        final Board board = boardRepository.save(createBoardDTO.toEntity());

        Assertions.assertAll(
                () -> Assertions.assertNotNull(board.getBoardId()),
                () -> Assertions.assertNotNull(board.getTitle()),
                () -> Assertions.assertNotNull(board.getContent()),
                () -> Assertions.assertNotNull(board.getWriter())
        );

        updateBoardDTO.setBoardId(board.getBoardId());
        board.updateBoard(updateBoardDTO);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(updateBoardDTO.getBoardId()),
                () -> Assertions.assertNotNull(updateBoardDTO.getTitle()),
                () -> Assertions.assertNotNull(updateBoardDTO.getContent()),
                () -> Assertions.assertNotNull(updateBoardDTO.getWriter())
        );
        log.info("updateBoard: {}", updateBoardDTO);
    }


    @Test
    @Transactional
    void deleteBoardTest() {
        final Board board = boardRepository.save(createBoardDTO.toEntity());
        board.updateUnActive();
        Assertions.assertAll(
                () -> Assertions.assertEquals(board.getUseYn(), "N")
        );
        log.info("deleteBoard: {}", board);
    }
}
