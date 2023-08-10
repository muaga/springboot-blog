package shop.mtcoding.blog;

import org.mindrot.jbcrypt.BCrypt;

// 비밀번호를 BCrypt로 암호화-신뢰성 검사하기
public class BCryptTest {

	public static void main(String[] args) {

		String encPassword = BCrypt.hashpw("1234", BCrypt.gensalt());
		// String 타입의 password를 입력해야 한다.
		System.out.println("encPassword : " + encPassword);
		// encPassword : 해시코드
		System.out.println(encPassword.length());
		// 해시코드는 고정길이가 있다.

		boolean isValid = BCrypt.checkpw("1134", encPassword);
		System.out.println(isValid);
		// plaintext가 같다면 true, 다르다면 false가 나온다.
		// 해시코드는 매번 다르게 나온다. 솔트때문에
	}
}
