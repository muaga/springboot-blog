package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query; // 이거를 써야한다.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository; // 이거를 써야한다.
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.model.User;

@Repository
// DB와
// 알아서 new해준다. 즉, IoC컨테이너에 띄어준다.
// IoC컨테이너 속 데이터
// BoardCotroller, UserController, UserRepository - 내가 직접 띄움
// EntityManager, HttpSession - 스프링이 띄어줌
public class UserRepository {

    @Autowired
    private EntityManager em;

    public User findByUsernameAndPassword(LoginDTO loginDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username = :username and password = :password",
                User.class);
        // loginDTO랑 User클래스랑 같아서, User로 받을 수 있다.
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("password", loginDTO.getPassword());
        return (User) query.getSingleResult();
    }

    @Transactional // 롤백과 커밋을 자동으로 해준다.
    public void save(JoinDTO joinDTO) {
        Query query = em.createNativeQuery(
                "insert into user_tb(username, password, email) values(:username, :password, :email)");
        query.setParameter("username", joinDTO.getUsername());
        query.setParameter("password", joinDTO.getPassword());
        query.setParameter("email", joinDTO.getEmail());
        query.executeUpdate();
    }

}
