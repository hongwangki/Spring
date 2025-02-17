package jpabook.jpashop.service;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //lombok 적용
public class MemberService {
    private final MemberRepository memberRepository;

    //회원가입
    @Transactional //ex) 회원가입이 잘 이루어 지지 않으면 정보 저장 메서드에도 이루어 지지 않아야 하기에 사용
    public Long join(Member member){
        validuplicateMember(member); //중복회원 로직
        memberRepository.save(member);
        return member.getId();
    }

    public void validuplicateMember(Member member) {
        //예외 터뜨림
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }


}
