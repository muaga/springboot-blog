package shop.mtcoding.blog.dto;

import javax.persistence.SecondaryTable;

import lombok.Getter;
import lombok.Setter;

/*
 * 회원정보수정 API
 * 1. URL: http://localhost:8080//user/update
 * 2. method : POST
 * 3. 요청 body : password=값(String)
 * 4. MIME타입 : x-www-urlencoded
 * ------------------------------------ 요청
 * 1) view 응답
 * 5. 응답 : view(html)를 응답함. index page
 */

@Getter
@Setter
public class UserUpdateDTO {
    private String password;

    public UserUpdateDTO(String password) {
        this.password = password;
    }
}
