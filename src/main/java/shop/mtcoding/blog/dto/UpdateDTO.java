package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

// 글수정 받을 것만 DTO로 받기

/*
 * 글수정 API
 * 1. URL: http://localhost:8080/board/{id}/update
 * 2. method : POST
 * 3. 요청 body : title=값(String)&content=값(String)
 * 4. MIME타입 : x-www-urlencoded
 * ------------------------------------ 요청
 * 1) view 응답
 * 5. 응답 : view(html)를 응답함. detail page
 */

@Getter
@Setter
public class UpdateDTO {
    private String title;
    private String content;
}
