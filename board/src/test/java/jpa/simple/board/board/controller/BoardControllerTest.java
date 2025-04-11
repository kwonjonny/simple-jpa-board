package jpa.simple.board.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpa.simple.board.board.dto.request.CreateBoardDTO;
import jpa.simple.board.board.dto.request.UpdateBoardDTO;
import jpa.simple.board.board.dto.respose.BoardDTO;
import jpa.simple.board.board.dto.respose.ListBoardDTO;
import jpa.simple.board.board.service.BoardService;
import jpa.simple.board.board.util.BoardPageRequestDTO;
import jpa.simple.board.board.util.BoardPageResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BoardService boardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBoardTest() throws Exception {
        // GIVEN
        CreateBoardDTO requestDTO = CreateBoardDTO.builder()
                .title("test title")
                .content("test content")
                .writer("test writer")
                .build();

        // WHEN
        Mockito.when(boardService.createBoard(Mockito.any(CreateBoardDTO.class)))
                .thenReturn(1L);

        // THEN
        mockMvc.perform(post("/api/board/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }


    @Test
    void readBoardTest() throws Exception {
        // GIVEN
        Long boardId = 1L;
        BoardDTO mockResponse = BoardDTO.builder()
                .boardId(boardId)
                .title("test title")
                .content("test content")
                .writer("test writer")
                .build();

        // WHEN
        Mockito.when(boardService.readBoard(boardId))
                .thenReturn(mockResponse);

        // THEN
        mockMvc.perform(get("/api/board/{boardId}", boardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.boardId").value(boardId))
                .andExpect(jsonPath("$.data.title").value("test title"))
                .andExpect(jsonPath("$.data.writer").value("test writer"));
    }


    @Test
    void updateBoardTest() throws Exception {
        // GIVEN
        UpdateBoardDTO updateDTO = UpdateBoardDTO.builder()
                .boardId(1L)
                .title("updated title")
                .content("updated content")
                .writer("updated writer")
                .build();

        // WHEN
        Mockito.when(boardService.updateBoard(Mockito.any(UpdateBoardDTO.class)))
                .thenReturn(1L); // 성공 시 1L 반환 가정

        // THEN
        mockMvc.perform(put("/api/board/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("게시글 삭제 요청 테스트")
    void deleteBoardTest() throws Exception {
        // GIVEN
        Long boardId = 1L;

        // WHEN
        Mockito.when(boardService.deleteBoard(boardId)).thenReturn(1L);

        // THEN
        mockMvc.perform(delete("/api/board/{boardId}", boardId))
                .andExpect(status().isOk());
    }
}