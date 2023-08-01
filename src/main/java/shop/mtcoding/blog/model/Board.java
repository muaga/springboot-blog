package shop.mtcoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto : create
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = true, length = 10000)
    private String content;
    private Timestamp createdAt;
    // at : 생성된 그 시점

    @ManyToOne
    private User user;

    // 만약, userId에 not null을 하지 않은 상태에서 회원정보를 탈퇴(삭제)하면,
    // userId는 null 상태로 게시물은 존재한다.
    // 탈퇴시 게시물도 삭제하려면, not null 해야 한다.
}
