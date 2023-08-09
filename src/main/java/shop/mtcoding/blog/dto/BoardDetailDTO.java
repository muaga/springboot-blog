package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoardDetailDTO {
    private Integer boardId;
    private String boardContent;
    private String boardTitle;
    private Integer boardUserId;

    private Integer replyId;
    private String replyComment;
    private Integer replyUserId;
    private String replyUserUsername;
    private boolean replyOwner;

    // QLRM은 생성자와 @NoArgsConstructor가 있어야 데이터를 받아올 수 있다.
    public BoardDetailDTO(Integer boardId, String boardContent, String boardTitle, Integer boardUserId,
            Integer replyId, String replyComment, Integer replyUserId, String replyUserUsername, boolean replyOwner) {
        this.boardId = boardId;
        this.boardContent = boardContent;
        this.boardTitle = boardTitle;
        this.boardUserId = boardUserId;
        this.replyId = replyId;
        this.replyComment = replyComment;
        this.replyUserId = replyUserId;
        this.replyUserUsername = replyUserUsername;
        this.replyOwner = replyOwner;
    }
}
