package jpa.simple.board.board.controller;

import jakarta.validation.Valid;
import jpa.simple.board.board.dto.request.CreateBoardDTO;
import jpa.simple.board.board.dto.request.UpdateBoardDTO;
import jpa.simple.board.board.dto.respose.BoardDTO;
import jpa.simple.board.board.dto.respose.ListBoardDTO;
import jpa.simple.board.board.service.BoardService;
import jpa.simple.board.board.util.BoardPageRequestDTO;
import jpa.simple.board.board.util.BoardPageResponseDTO;
import jpa.simple.board.exception.handler.APIResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("api/board/")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<APIResponseDTO<String>> createBoard(@Valid @RequestBody final CreateBoardDTO requestDTO) {
        final Long createResult = boardService.createBoard(requestDTO);
        return APIResponseDTO.ofSuccessCreateResponse(createResult > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("{boardId}")
    public ResponseEntity<APIResponseDTO<BoardDTO>> readBoard(@PathVariable("boardId") final Long boardId) {
        final BoardDTO readResult = boardService.readBoard(boardId);
        return APIResponseDTO.ofSuccessResponse(readResult, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<APIResponseDTO<String>> updateBoard(@Valid @RequestBody final UpdateBoardDTO requestDTO) {
        final Long updateResult = boardService.updateBoard(requestDTO);
        return APIResponseDTO.ofSuccessUpdateResponse(updateResult > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{boardId}")
    public ResponseEntity<APIResponseDTO<String>> deleteBoard(@PathVariable("boardId") final Long boardId) {
        final Long deleteResult = boardService.deleteBoard(boardId);
        return APIResponseDTO.ofSuccessDeleteResponse(deleteResult > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<APIResponseDTO<BoardPageResponseDTO<ListBoardDTO>>> listBoard(@ModelAttribute final BoardPageRequestDTO requestDTO) {
        final BoardPageResponseDTO<ListBoardDTO> listResult = boardService.listBoard(requestDTO);
        return APIResponseDTO.ofSuccessResponse(listResult, HttpStatus.OK);
    }
}
