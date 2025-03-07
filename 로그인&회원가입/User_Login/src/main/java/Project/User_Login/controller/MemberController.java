package Project.User_Login.controller;

import Project.User_Login.Service.MemberService;
import Project.User_Login.domain.Address;
import Project.User_Login.domain.Member;
import Project.User_Login.domain.MemberType;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    //로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("name") String name,
                        @RequestParam("password") String password,
                        Model model) {
        Member member = memberService.login(name, password);

        if (member == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");  // 로그인 실패 시 에러 메시지 전달
            return "home";  // 로그인 실패 시 home.html로 돌아갑니다.
        }

        return "login";  // 로그인 성공 시 login.html로 이동
    }

    @GetMapping("/member/join")
    public String joinForm() {
        return "joinForm";
    }

    // 회원 가입 처리
    @PostMapping("/member/join")
    public String join(@RequestParam("name") String name,
                       @RequestParam("email") String email,
                       @RequestParam("password") String password,
                       @RequestParam("street") String street,
                       @RequestParam("city") String city,
                       @RequestParam("zipcode") String zipcode,
                       Model model, RedirectAttributes redirectAttributes) {

        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);
        member.setMemberType(MemberType.USER);
        member.setAddress(new Address(street, city, zipcode)); // 주소 설정

        try {
            memberService.join(member);
            redirectAttributes.addFlashAttribute("successMessage", "회원가입이 성공적으로 완료되었습니다!"); // 성공 메시지 전달
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
            return "joinForm";  // 회원가입 폼으로 돌아갑니다.
        }

        return "redirect:/"; // 가입 후 홈 화면으로 이동
    }



    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }


}
