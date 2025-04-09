package jpa.simple.board.board.util;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardPageRequestDTO {

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    private String type;
    private String keyword;
    private String link;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public void setPage(Integer page) {
        if(page == null || page <= 0) {
            this.page = 1;
        }
        else {
            this.page = page;
        }
    }

    public void setSize(Integer size) {
        if(size < 0 || size > 100) {
            this.size = 10;
        }
        else {
            this.size = size;
        }
    }

    public Integer getSkip() {
        return (this.page - 1) * this.size;
    }

    public Integer getCountEnd() {
        return ((int) Math.ceil(this.page / 5.0) * (5 * this.size)) + 1;
    }

    public String[] getTypes() {
        if (this.type == null || this.type.isEmpty()) {
            return null;
        }
        return this.type.split(",");
    }

    public String getLink() {
        if (link == null) {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("page=").append(this.page);
            strBuilder.append("&size=").append(this.size);
            if (type != null && !type.isEmpty()) {
                strBuilder.append("&type=").append(this.type);
            }
            if (keyword != null && !keyword.isEmpty()) {
                strBuilder.append("&keyword=").append(URLEncoder.encode(keyword, StandardCharsets.UTF_8));
            }
            if (startDate != null) {
                strBuilder.append("&startDate=").append(startDate.toString());
            }
            if (endDate != null) {
                strBuilder.append("&endDate=").append(endDate.toString());
            }
            link = strBuilder.toString();
        }
        return link;
    }
}
