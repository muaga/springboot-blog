package shop.mtcoding.blog.controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.UserRepository;

@Controller
// view 잘 받고, 잘 return하기
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session; // request는 가방, session 서랍
    // 로그인이 되면 session에 저장해야 한다.

    @GetMapping("/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {

        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {

        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    // 4. 실무 -> 이 방법을 사용해야 한다.
    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {

        // 유효성검사 = validation check
        if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
            return "redirct:/40x";
        }
        // 현재 view로 통해서는 여기까지 접근할 수 없다. 왜냐하면 required로 막아놨기 때문에
        // 하지만 우회해서 접근하는 postman 같은 접근으로는 공격, 즉 접근이 가능하다.
        // 그래서 막아야 한다.
        // 만약 우회 접근을 하면 아예 상관없는 곳으로 가버리게 return으로 다른 사이트 또는 에러사이트로 돌린다.
        if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
            return "redirct:/40x";
        }
        if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
            return "redirct:/40x";
        }
        // 조건에 null, 공백 등 받지 말아야 되는 데이터를 생각하고 막아야 한다.

        try {
            userRepository.save(joinDTO);
            // 핵심기능
            // 회원가입시 unique 위반으로, 여기서 오류가 터진다.
        } catch (Exception e) {
            return "redirect:/50x";
        }

        return "redirect:/loginForm";
    }

    @PostMapping("/login")
    public String login(LoginDTO loginDTO) {
        // 유효성검사 = validation check
        // 공백, null 체크(우회접근 제한)
        if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }

        // 핵심기능
        try {
            User user = userRepository.findByUsernameAndPassword(loginDTO);
            session.setAttribute("sessionUser", user);
            // 서버 측에 sessionUser가 남겨져 있다.
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/exLogin";
        }

    }

    // ★ 핵심기능

    // // 3. 정상
    // // DS가 파싱, 메소드 찾기
    // @PostMapping("/join")
    // public String join(String username, String password, String email) {
    // System.out.println("username : " + username);
    // System.out.println("password : " + password);
    // System.out.println("email : " + email);
    // return "redirect:/loginForm";
    // }

    // // 2. 약간 정상
    // // DS(Controller 메소드 찾기, 바디 데이터 파싱)
    // // DS가 바디데이터를 파싱 안하고, Controller 메소드만 찾는 상황
    // // 1차 자동, 2차 파싱 내가 직접
    // @PostMapping("/join")
    // public String join(HttpServletRequest request) {
    // String username = request.getParameter("username");
    // String password = request.getParameter("password");
    // String email = request.getParameter("email");
    // return "redirect:/loginForm";
    // }
    // // HTTP로 받는 통신을 받기 위해서는 위의 코드로 작성해야 한다.
    // // request.getParameter -> X-www.urlencoded로 들어온 모든 데이터를 파싱한다.

    // // 1. 비정상
    // // 직접 bufferReader로 받고
    // // 1차, 2차 파싱 내가 직접
    // // 2차 파싱은 getParameter로 하기엔 바디가 이미 소비되어버려, split해야한다......
    // @PostMapping("/join")
    // public String join(HttpServletRequest request) throws IOException {
    // // username=ssar&password=1234&email=ssar@nate.com
    // BufferedReader br = request.getReader();
    // // 버퍼에서 헤드는 읽고 지우고, 바디는 읽고 지우지 않는다. 그래서 바디만 남아있다.
    // // 그래서 바디에 남은 데이터만 request 객체로 1차파싱한다.
    // String body = br.readLine();
    // // ★ 버퍼를 읽어서 소비가 되었다.

    // String username = request.getParameter("username");
    // // X, ★ 이미 바디 버퍼가 소비되어 나올 수가 없다.

    // System.out.println("body : " + body);
    // // 출력은 body = username=ssar&password=1234&email=ssar@nate.com 이렇게 출력된다.
    // // x-www-urlencoded로 출력된다.
    // System.out.println("username : " + username);
    // // 출력은 username = null
    // // 버퍼가 비어서 빈 값이다.

    // return "redirect:/loginForm";
    // }
}
