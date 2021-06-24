package com.api.dex.controller;

import com.api.dex.domain.Member;
import com.api.dex.domain.MemberRepository;
import com.api.dex.dto.MemberDto;
import com.api.dex.service.MemberService;
import com.api.dex.utils.JwtTokenProvider;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // 토큰 유효시간 30분
    private long accessTokenValidTime = 30 * 60 * 1000L;
    private long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    // 회원가입
    @PostMapping("/sign")
    public ResponseEntity sign(@RequestBody MemberDto memberDto) {
        JSONObject items = new JSONObject();
        JSONObject data = new JSONObject();

        Member member = memberService.insertMember(memberDto);

        data.put("account", member.getAccount());
        data.put("name", member.getName());
        items.put("items", data);
        items.put("message", "sign for success!");

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        JSONObject items = new JSONObject();
        JSONObject data = new JSONObject();
        Member member = memberRepository.findByAccount(user.get("account"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String accessToken = jwtTokenProvider.createToken(member.getAccount(), member.getMemberRole(), accessTokenValidTime);
        String refreshToken = jwtTokenProvider.createToken(member.getAccount(), member.getMemberRole(), refreshTokenValidTime);

        httpHeaders.add("accessToken", accessToken);
        httpHeaders.add("refreshToken", refreshToken);

        data.put("account", member.getAccount());
        data.put("name", member.getName());
        items.put("items", data);

        return new ResponseEntity<>(items, httpHeaders, HttpStatus.OK);
    }


    @PostMapping("/authority")
    public ResponseEntity<JSONObject> isValidateToken(@RequestBody String account){

        return null;
    }
}
