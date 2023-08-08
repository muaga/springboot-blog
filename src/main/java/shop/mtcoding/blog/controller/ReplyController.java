package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.BoardRepository;
import shop.mtcoding.blog.repository.ReplyRepository;

@Controller
public class ReplyController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    // 댓글등록
    @PostMapping("/reply/save")
    public String save(ReplyWriteDTO replyWriteDTO) {
        // 1. 로그인 인증 검사
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        // 2. comment 유효성 검사
        // 공백, null 체크(우회접근 제한)
        if (replyWriteDTO.getBoardId() == null) {
            return "redirct:/40x";
        }
        if (replyWriteDTO.getComment() == null || replyWriteDTO.getComment().isEmpty()) {
            return "redirct:/40x";
        }
        // 3. 댓글 쓰기
        replyRepository.save(replyWriteDTO, sessionUser.getId());

        // 4. 상세보기로 이동
        return "redirect:/board/" + replyWriteDTO.getBoardId();

    }

    // 댓글삭제
    @PostMapping("/reply/{replyId}/delete")
    public String delete(@PathVariable Integer replyId, Reply reply) {
        System.out.println("테스트 : replyId : " + replyId);
        System.out.println("테스트 : boardId : " + reply.getBoard().getId());

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; // 401
        }

        Reply reply2 = replyRepository.findById(reply.getUser().getId());
        if (sessionUser.getId() != reply2.getUser().getId()) {
            return "redirect:/40x"; // 403 권한없음
        }

        replyRepository.delete(replyId);
        return "redirect:/board/" + reply.getBoard().getId();
    }

}
