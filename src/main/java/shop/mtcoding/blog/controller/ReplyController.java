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

import shop.mtcoding.blog.dto.BoardDetailDTO;
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
    @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable Integer id, BoardDetailDTO boardDetailDTO) {
        System.out.println("테스트 : replyId : " + id);
        System.out.println("테스트 : boardId : " + boardDetailDTO.getBoardId());

        // 부가로직

        // * 게시물 id 유효성 검사
        if (boardDetailDTO.getBoardId() == null) {
            return "redirect:/40x"; // 403 권한없음
        }

        // 1. 로그인 유무
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; // 401
        }

        // 2. 본인의 댓글 권한
        List<Reply> reply = replyRepository.findById(boardDetailDTO.getReplyUserId());
        if (sessionUser.getId() != reply.get(0).getUser().getId()) {
            return "redirect:/40x"; // 403 권한없음
        }
        // @PathVariable id를 하면 안되는 이유, 댓글 id ≠ 댓글유저id

        // 2번째방법
        // Reply reply = replyRepository.findById(id); -> 댓글 id로 1건 찾아오기
        // if (reply.getUser().getId() != sessionUser.getId()) { -> 해당 댓글에 대한 userId
        // return "redirect:/40x"; // 403
        // }
        // @PathVariable id를 해도 되는 이유, 댓글의 {id}로 댓글을 찾은 후 해당 댓글의 user를 찾으니까

        // 핵심로직
        replyRepository.deleteById(id);
        return "redirect:/board/" + boardDetailDTO.getBoardId();
        // redirect를 게시물상세보기 엔드포인트로 해줘야 제자리에서 댓글만 삭제된 것 처럼 나온다.
    }

}