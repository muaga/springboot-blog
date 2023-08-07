package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query; // 이거를 써야한다.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository; // 이거를 써야한다.
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.dto.UpdateDTO;
import shop.mtcoding.blog.dto.UserUpdateDTO;
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

    // 안전한 코드 작성 실습(오류가 터질 것은 try-catch가 아닌 코드로 터지지 않게 막는다.)
    public User findByUsername(String username) {
        try {
            Query query = em.createNativeQuery("select * from user_tb where username = :username",
                    User.class);
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    // 로그인 기능
    public User findByUsernameAndPassword(LoginDTO loginDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username = :username and password = :password",
                User.class);
        // loginDTO랑 User클래스랑 같아서, User로 받을 수 있다.
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("password", loginDTO.getPassword());
        return (User) query.getSingleResult();
    }

    // 회원가입 기능
    @Transactional // 롤백과 커밋을 자동으로 해준다.
    // => 프레임워크에서 꼭 필요한 이유?
    // 무결성의 유지, 격리성을 확보하기 위해서
    // - 동시에 실행되는 것을 막기 위해 순서대로(독립적으로), 제약조건 내에서 실행이 될 수 있도록
    // 오류가 발생하기 전에 아예 미리 막는 것
    public void save(JoinDTO joinDTO) {
        System.out.println("테스트 : " + 1);

        Query query = em.createNativeQuery(
                "insert into user_tb(username, password, email) values(:username, :password, :email)");
        System.out.println("테스트 : " + 2);

        query.setParameter("username", joinDTO.getUsername());
        query.setParameter("password", joinDTO.getPassword());
        query.setParameter("email", joinDTO.getEmail());
        System.out.println("테스트 : " + 3);

        query.executeUpdate(); // 쿼리를 전송(DBMS)
        System.out.println("테스트 : " + 4);

    }

    // 회원정보 수정기능
    @Transactional
    public void update(UserUpdateDTO userUpdateDTO, Integer id) {
        Query query = em.createNativeQuery("update user_tb set password = :password where id = :id");
        query.setParameter("password", userUpdateDTO.getPassword());
        query.setParameter("id", id);
        query.executeUpdate();
    }

    // findById
    public User findById(Integer id) {
        Query query = em.createNativeQuery("select * from user_tb where id = :id", User.class);
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }

}
