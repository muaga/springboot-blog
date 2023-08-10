package shop.mtcoding.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "user_tb") // DB가 User라고 그대로 대문자 구분해서 인식하기 때문에, 헷갈릴 수 있다.
@Entity // ddl-auto : create

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 60)
    // DB의 데이터 길이를 위와 같이 조절할 수 있다.
    // 길이는 필요한 만큼 적용한다.
    private String password;

    @Column(nullable = false, length = 20)
    private String email;
}

/*
 * IDENTITY -> 각 DB의 전략에 맞춰서 사용하겠다.
 * 번호를 증가하는 전략이 다양한데,
 * 1. 위의 행 번호를 기준으로 ++ -> 오토 인클라인먼트 - sql
 * 2. 함수에 맞춰서 ++ -> 시퀀스 전략 - oracle
 * 3. 미리 만들어 놓은 테이블에서 숫자를 하나씩 가지고 오는 것 -> 테이블 전략
 */
