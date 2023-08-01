package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/40x")
    public String ex40x() {
        return "error/ex40x";
    }
    // 우회 접근

    @GetMapping("/50x")
    public String ex50x() {
        return "error/ex50x";
    }
    // unique 위반

    @GetMapping("/exLogin")
    public String exLogin() {
        return "error/exLogin";
    }
    // 동일한 유저네임, 비밀번호

}
