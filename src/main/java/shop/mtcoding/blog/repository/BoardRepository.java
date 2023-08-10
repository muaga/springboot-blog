package shop.mtcoding.blog.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.BoardDetailDTO;
import shop.mtcoding.blog.dto.UpdateDTO;
import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;

@Repository
public class BoardRepository {

    @Autowired
    private EntityManager em;

    // save
    @Transactional
    public void save(WriteDTO writeDTO, Integer userId) {
        // userId는 session에서 꺼내올 것이다.
        Query query = em.createNativeQuery(
                "insert into board_tb(title, content, user_id, created_at) values(:title, :content, :userId, now())");
        query.setParameter("title", writeDTO.getTitle());
        query.setParameter("content", writeDTO.getContent());
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    // findByAll
    // localhost:8080?page=0
    // paging 쿼리
    public List<Board> findByAll(int page) {
        final int SIZE = 3;
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit :page, :size ", Board.class);
        // limit :page, 3 -> 1페이지당 3개의 row
        // 0, 3 : 인덱스 0(id 1)부터 3개 / 3, 3 : 인덱스 3(id 4)부터 3개
        // order by id desc -> 오름차순
        query.setParameter("page", page * SIZE);
        // page는 3의 배수가 되도록 *3해야 한다.
        query.setParameter("size", SIZE);
        return query.getResultList();
    }

    // findByAll
    // localhost:8080?page=0 / ?keyword=""
    // 검색 후 게시물 paging 쿼리
    public List<Board> findByAll(int page, String keyword) {
        final int SIZE = 3;
        Query query = em.createNativeQuery(
                "select * from board_tb where title like :keyword order by id desc limit :page, :size ", Board.class);

        query.setParameter("keyword", "%" + keyword + "%");
        query.setParameter("page", page * SIZE);
        query.setParameter("size", SIZE);
        return query.getResultList();
    }

    // 총 게시글 수
    public List<Board> findByAllBoard() {
        Query query = em.createNativeQuery("select * from board_tb", Board.class);
        return query.getResultList();
    }

    // 검색 결과 게시글 수
    public List<Board> findByAllBoard(String keyword) {
        Query query = em.createNativeQuery("select * from board_tb where title like :keyword", Board.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }

    // 총 게시글 수
    // select id, title from board_tb
    // resultClass 안 붙이고, 직접 파싱하려면
    // Object[] 로 리턴이 되어, object[0] = 1, object[1] = 제목 1 이렇게 나온다.
    // BigInteger는 숫자 범위가 엄청 커서, count는 데이터의 갯수가 엄청 많을 수 있기 때문에
    // Integer로 받아지지 않고, Long이나 BigInteger 타입으로 받을 수 있다.
    public int count() {
        // 원래는 Object 배열로 return을 받는다. Object 배열은 column의 연속이다.
        // 그룹 함수를 써서, 하나의 column을 조회하면 Object로 return이 된다.
        Query query = em.createNativeQuery("select count(*) from board_tb where id = :id");
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue();
        // intValue()
        // 해당 숫자 타입의 값을 int로 변환해서 반환한다.
        // BigInteger로 "8"을 받고, 온전히 "8"라는 문자열을 int로 변환한다고 생각하면 될 듯 하다.
    }

    // 댓글주인, 게시물 주인 확인
    public List<BoardDetailDTO> findByIdJoinReply(Integer boardId, Integer sessionUserId) {
        // 동적쿼리 -> DB에서 다 처리하고 가지고 오기
        String sql = "select ";
        sql += "b.id board_id, ";
        sql += "b.content board_content, ";
        sql += "b.title board_title, ";
        sql += "b.user_id board_user_id, ";
        sql += "r.id reply_id, ";
        sql += "r.comment reply_comment, ";
        sql += "r.user_id reply_user_id, ";
        sql += "ru.username reply_user_username, ";
        if (sessionUserId == null) {
            sql += "false reply_owner ";
        } else {
            sql += "case when r.user_id = :sessionUserId then true else false end reply_owner ";
        }
        // 현재 로그인이 되어 있는 유저 중 댓글 유저 id와 로그인 중 id가 일치하면 댓글을 작성한 유저이다.
        sql += "from board_tb b left outer join reply_tb r ";
        sql += "on b.id = r.board_id ";
        sql += "left outer join user_tb ru ";
        sql += "on r.user_id = ru.id ";
        sql += "where b.id = :boardId ";
        sql += "order by r.id desc";

        Query query = em.createNativeQuery(sql);
        // QLRM
        // model에 받지 않고, qlrm을 통해서 DTO에 데이터를 받아야 하는 경우
        // 매핑 클래스를 createNativeQuery에 작성하지 않는다.

        query.setParameter("boardId", boardId);
        // :boardId = name:"boardId"

        // sessionUser가 있다면 받는 데이터
        if (sessionUserId != null) {
            query.setParameter("sessionUserId", sessionUserId);
        }

        // 직접 오브젝트 매핑하기
        JpaResultMapper mapper = new JpaResultMapper();
        List<BoardDetailDTO> dtos = mapper.list(query, BoardDetailDTO.class);
        // 1 row = uniqueResult
        // 여러 row = list

        return dtos;
    }

    // findById(글번호)
    public Board findById(Integer id) {
        Query query = em.createNativeQuery("select * from board_tb where id = :id", Board.class);
        query.setParameter("id", id);
        return (Board) query.getSingleResult();
    }

    // delete
    @Transactional
    public void deleteById(Integer id) {
        Query query = em.createNativeQuery("delete from board_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // update
    @Transactional
    public void update(UpdateDTO updateDTO, Integer id) {
        Query query = em.createNativeQuery("update board_tb set title = :title, content = :content where id = :id");
        query.setParameter("title", updateDTO.getTitle());
        query.setParameter("content", updateDTO.getContent());
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
