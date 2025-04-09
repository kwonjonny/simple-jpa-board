package jpa.simple.board.board.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardPageResponseDTO<E> {
    private List<E> list;
    private int total;
    private Integer page;
    private Integer size;
    private int startNum;
    private int endNum;
    private boolean prevBtn;
    private boolean nextBtn;
    private int lastPage;

    @Builder(builderMethodName = "boardWithAll")
    public BoardPageResponseDTO(List<E> list, int total, BoardPageRequestDTO requestDTO, int totalList) {
        this.list = list;
        this.total = total;
        this.page = requestDTO.getPage();
        this.size = requestDTO.getSize();
        this.startNum = ((this.page - 1) / 5) * 5 + 1;
        this.endNum = Math.min(this.startNum + 4, (int) Math.ceil(total / (double) this.size));
        int last = (int) (Math.ceil((total / (double) size)));
        this.endNum = Math.min(endNum, last);
        this.prevBtn = this.startNum > 1;
        this.nextBtn = total > this.endNum * this.size;
        this.lastPage = (int) Math.ceil((double) totalList / this.size);
    }
}
