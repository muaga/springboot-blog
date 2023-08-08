package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReplyWriteDTO {
    private Integer boardId;
    private String comment;
}
