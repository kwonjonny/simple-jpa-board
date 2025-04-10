package jpa.simple.board.board.service;

import jpa.simple.board.board.dto.request.CreateBoardDTO;
import jpa.simple.board.board.dto.request.UpdateBoardDTO;
import jpa.simple.board.board.dto.respose.BoardDTO;
import jpa.simple.board.board.dto.respose.ListBoardDTO;
import jpa.simple.board.board.entity.Board;
import jpa.simple.board.board.util.BoardPageRequestDTO;
import jpa.simple.board.board.util.BoardPageResponseDTO;
import jpa.simple.board.exception.service_exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

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
            CreateBoardDTO createBoardDTO = CreateBoardDTO.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .writer("Writer " + (i % 3))
                    .build();
            boardService.createBoard(createBoardDTO);
        }

        BoardPageRequestDTO requestDTO = BoardPageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        BoardPageResponseDTO<ListBoardDTO> responseDTO = boardService.listBoard(requestDTO);
        Assertions.assertNotNull(responseDTO);
        log.info("페이징 결과 리스트: {}", responseDTO.getList());
        log.info("총 게시글 수: {}", responseDTO.getTotal());
    }


    @Test
    @Transactional
    void createBoardTest() {
        final Long createResult = boardService.createBoard(createBoardDTO);

        Assertions.assertNotNull(createResult);
        log.info("createBoardId: {}", createResult);
    }


    @Test
    @Transactional
    void readBoardTest() {
        final Long createBoardId = boardService.createBoard(createBoardDTO);
        Assertions.assertNotNull(createBoardId);
        final BoardDTO boardDTO = boardService.readBoard(createBoardId);
        Assertions.assertNotNull(boardDTO);
        log.info("readBoard: {}", boardDTO);
    }


    @Test
    @Transactional
    void updateBoardTest() {
        final Long createBoardId = boardService.createBoard(createBoardDTO);
        Assertions.assertNotNull(createBoardId);

        updateBoardDTO.setBoardId(createBoardId);
        final Long updateBoardId = boardService.updateBoard(updateBoardDTO);
        Assertions.assertNotNull(updateBoardId);

        final BoardDTO boardDTO = boardService.readBoard(updateBoardId);
        Assertions.assertNotNull(boardDTO);
        log.info("updateBoard: {}", boardDTO);
    }


    @Test
    @Transactional
    void deleteBoardTest() {
        final Long createBoardId = boardService.createBoard(createBoardDTO);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createBoardId)
        );

        final Long deleteBoardId = boardService.deleteBoard(createBoardId);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(deleteBoardId)
        );

        Assertions.assertThrows(ServiceException.class, () -> boardService.readBoard(deleteBoardId));
    }
}

