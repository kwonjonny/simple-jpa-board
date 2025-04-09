package jpa.simple.board.board.repository;

import static jpa.simple.board.board.entity.QBoard.board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpa.simple.board.board.dto.respose.ListBoardDTO;
import jpa.simple.board.board.entity.Board;
import jpa.simple.board.board.util.BoardPageRequestDTO;
import jpa.simple.board.board.util.BoardPageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import com.querydsl.core.BooleanBuilder;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Board> findActiveById(final @NotNull Long boardId) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(board)
                        .where(board.boardId.eq(boardId), board.useYn.eq("Y"))
                        .fetchOne()
        );
    }

    @Override
    public BoardPageResponseDTO<ListBoardDTO> findAllByPage(final @NotNull BoardPageRequestDTO requestDTO) {
        int page = requestDTO.getPage();
        int size = requestDTO.getSize();
        int offset = requestDTO.getSkip();
        final BooleanBuilder condition = getSearch(requestDTO);

        final List<Board> resultList = jpaQueryFactory
                .selectFrom(board)
                .where(condition)
                .orderBy(board.boardId.desc())
                .offset(offset)
                .limit(size)
                .fetch();

        final List<ListBoardDTO> dtoList = resultList.stream()
                .map(ListBoardDTO::fromEntity)
                .toList();

        final Long total = jpaQueryFactory
                .select(board.count())
                .from(board)
                .where(board.useYn.eq("Y"))
                .fetchOne();

        return BoardPageResponseDTO.<ListBoardDTO>boardWithAll()
                .list(dtoList)
                .total(total != null ? total.intValue() : 0)
                .requestDTO(requestDTO)
                .totalList(total != null ? total.intValue() : 0)
                .build();
    }

    private @NotNull BooleanBuilder getSearch(final @NotNull BoardPageRequestDTO requestDTO) {
        final BooleanBuilder builder = new BooleanBuilder();

        final String[] types = requestDTO.getTypes();
        final String keyword = requestDTO.getKeyword();

        if (types != null && keyword != null && !keyword.isEmpty()) {
            final BooleanBuilder condition = new BooleanBuilder();

            for (String type : types) {
                switch (type) {
                    case "title" -> condition.or(board.title.containsIgnoreCase(keyword));
                    case "content" -> condition.or(board.content.containsIgnoreCase(keyword));
                    case "writer" -> condition.or(board.writer.containsIgnoreCase(keyword));
                }
            }
            builder.and(condition);
        }

        builder.and(board.useYn.eq("Y"));

        return builder;
    }

}
