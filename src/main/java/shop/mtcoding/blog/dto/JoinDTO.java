package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

// 지금까지는 select, 응답을 위한 DTO를 만들었지만
// 이번에는 insert, 요청을 위한 DTO이다.
// DTO를 보고 API 문서를 작성한다.

// 회원가입을 위한 DTO
// JoinForm에서 받는 key값을 기준으로 작성한다.
// Controller에서 메소드를 실행할 때, 수 많은 매개변수를 받을 때
// 일일이 다 적기에는 매개변수가 너무 많을 수가 있기 때문에
// DTO를 만들어서 model 같이 사용한다.
// -> 관리하기 편하다.

// 백엔드는 Controller -> DTO(API문서 작성 ---> PDF 파일로 만들어 줘야 한다.)
// 프론트엔드 DTO(API 문서 확인) -> html
// Form의 action에서 URL을 작성할 때는, 자신의 컴퓨터 주소를 빼고 적기 때문에 엔드포인트만 작성해준다.

/*
 * 회원가입 API
 * 1. URL: http://localhost:8080/join
 * 2. method : POST
 * 3. 요청 body : username=값(String)&password=값(String)&email=값(String)
 * // GET은 body가 없으니까 생략
 * // 값의 타입을 적어줘야 한다.
 * 4. MIME타입 : x-www-urlencoded
 * ------------------------------------ 요청
 * 1) view 응답
 * 5. 응답 : view로 응답
 * 
 * 2) 데이터로 응답할 때는, 쿼리스트링이 필요하다. ?
 * 5. Param : page={joinForm}
 * 6. Param : 디폴트 값은 0이다
 * 7. param body 내용 적어주기 
 */

@Getter
@Setter
public class JoinDTO {
    private String username;
    private String password;
    private String email;
}

// HTTP로 받은 value 값을, 해당 변수의 값으로 받으려면 setter가 있어야 한다. 후에 받는 데이터이기 때문이다.
// setter를 하기 위해서는, new를 먼저 해야 하는데 그러기 위해서는 Getter도 있어야 한다.
