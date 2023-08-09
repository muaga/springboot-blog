package shop.mtcoding.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.BoardDetailDTO;
import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Reply;

@Repository
public class ReplyRepository {

    @Autowired
    private EntityManager em;

    // 댓글 등록
    @Transactional
    public void save(ReplyWriteDTO replyWriteDTO, Integer id) {
        Query query = em.createNativeQuery(
                "INSERT INTO REPLY_TB (comment, board_id, user_id) VALUES (:comment, :boardId, :userId)");
        query.setParameter("comment", replyWriteDTO.getComment());
        query.setParameter("boardId", replyWriteDTO.getBoardId());
        query.setParameter("userId", id);
        query.executeUpdate();
    }

    // 댓글 findByBoardId
    public List<Reply> findByBoardId(Integer boardId) {
        Query query = em.createNativeQuery("select * from reply_tb where board_id = :boardId", Reply.class);
        // ORM을 통해서, 자동으로 user_id와 board_id가 생성되고, user_id로 username을 가지고 올 수 있다.
        query.setParameter("boardId", boardId);
        return query.getResultList();
    }
    // join쿼리와 ORM을 통해 나온 결과가 똑같아도, join쿼리가 더 좋다.
    // 하지만 ORM이 조금 더 간편해서 join을 할 수 있으면 사용해도 좋다.

    // 댓글 findByUserId
    public List<Reply> findById(Integer userId) {
        Query query = em.createNativeQuery("select * from reply_tb where user_id = :userId",
                Reply.class);
        // ORM을 통해서, 자동으로 user_id와 board_id가 생성되고, user_id로 username을 가지고 올 수 있다.
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    // 2번째 방법
    // 댓글 findById
    // public Reply findById(Integer id) {
    // Query query = em.createNativeQuery("select * from reply_tb where id = :id",
    // Reply.class);
    // query.setParameter("id", id);
    // return (Reply) query.getsingleResult();
    // }
    // 해당 댓글의 주인에 대한 행을 가져와서 Controller에서 해당 주인의 UserId를 찾으면 된다.

    // 댓글 삭제
    @Transactional
    public void deleteById(Integer id) {
        Query query = em.createNativeQuery("delete from reply_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}