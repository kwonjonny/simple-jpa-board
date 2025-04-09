package jpa.simple.board.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false, name = "create_date")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "use_yn")
    private String useYn = "Y";

    public void updateUnActive() {
        this.useYn = "N";
    }

    public boolean isActive() {
        return "Y".equals(this.useYn);
    }
}
