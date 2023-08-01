package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.BoardRepository;

@Controller
public class BoardController {

    // 로그인을 한 후, 글쓰기를 할 수 있도록 session 데이터 가지고 오기
    @Autowired
    private HttpSession session;

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping({ "/", "/board" })
    public String index() {
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        // 부가로직 ; ★ 인증페이지
        // 로그인 인증이 되면, saveForm을 실행할 수 있다.
        // 브라우저에 이동 버튼이 없어도, URL만 알다면 접근할 수 있다.(우회로 URL 접근 제한)
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }
        // 핵심로직
        return "board/saveForm";
    }

    @PostMapping("/board/save")
    public String save(WriteDTO writeDTO) {
        // 부가로직
        // 1.로그인 인증 : 글쓰기 페이지에서 막아도, 또 POSTMAN 등으로 우회접근이 있기 때문에 모두 막는다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }
        // 2. 공백, null 제한(우회 접근 제한)
        if (writeDTO.getTitle() == null || writeDTO.getTitle().isEmpty()) {
            return "redirct:/40x";
        }
        if (writeDTO.getContent() == null || writeDTO.getContent().isEmpty()) {
            return "redirct:/40x";
        }
        // 핵심로직
        boardRepository.save(writeDTO, sessionUser.getId());
        return "redirect:/";
    }

    @GetMapping("/board/1")
    public String detailForm() {
        return "board/detailForm";
    }

}
