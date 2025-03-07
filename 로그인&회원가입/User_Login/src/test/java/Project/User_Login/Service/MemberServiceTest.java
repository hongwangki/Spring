package Project.User_Login.Service;

import Project.User_Login.domain.Address;
import Project.User_Login.domain.Member;
import Project.User_Login.domain.MemberType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입() {
        Member member = new Member();
        member.setName("홍왕기");
        member.setPassword("123456");
        member.setMemberType(MemberType.ADMIN);
        member.setEmail("admin@gmail.com");
        member.setAddress(new Address("진건오남로","12040","동부"));
        Long saveId = memberService.join(member);
        System.out.println(saveId);
        System.out.println(member.getId());

        assertEquals(member.getId(), saveId);
    }

    @Test
    public void 중복회원_검사() {
        // Given
        Member member = new Member();
        member.setName("홍왕기");
        member.setPassword("123456");
        member.setEmail("admin@gmail.com");

        Member member1 = new Member();
        member1.setName("홍왕기");
        member1.setPassword("123456");
        member1.setEmail("admin@gmail.com");

        // When & Then
        memberService.join(member);

        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1); // 여기서 예외 발생해야 성공
        });
    }
}