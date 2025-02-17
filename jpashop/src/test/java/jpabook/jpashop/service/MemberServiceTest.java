package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    //@Rollback(false)
    public void 회원가입() throws Exception{
        Member member=new Member();
        member.setName("kim");

        Long saveId = memberService.join(member);

        assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1=new Member();
        Member member2=new Member();
        member1.setName("kim");
        member2.setName("kim");

        //when
        assertThrows(IllegalStateException.class,()-> {
                    memberService.join(member1);
                    memberService.join(member2);//여기서 예외
                });
    }

}