package Project.User_Login.Service;

import Project.User_Login.domain.Member;
import Project.User_Login.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository  memberRepository;
    private final PasswordEncoder passwordEncoder;  // BCryptPasswordEncoder를 주입받음

    /**
     * 회원 가입 기능
     */
    public Long join(Member member) {
        List<Member> findMembers = memberRepository.findAll();

        // 중복 회원 검사
        for (Member m : findMembers) {
            if (m.getName().equals(member.getName()) || m.getEmail().equals(member.getEmail())) {
                throw new IllegalStateException("이름이나 이메일이 이미 존재하는 회원입니다.");
            }
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword); // 암호화된 비밀번호 설정

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 모든 멤버 조회 함수
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /**
     * 멤버 조회 함수
     */
    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }


    /**
     * 로그인 기능
     */
    public Member login(String name, String password) {
        Optional<Member> member = memberRepository.findByName(name);

        if (member.isPresent() && passwordEncoder.matches(password, member.get().getPassword())) {
            return member.get(); // 로그인 성공
        }

        return null; // 로그인 실패
    }
}
