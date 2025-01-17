package com.api.dex.controller;

import com.api.dex.domain.Member;
import com.api.dex.domain.MemberRepository;
import com.api.dex.domain.MemberRole;
import com.api.dex.domain.SecurityUser;
import com.api.dex.dto.MemberDto;
import com.api.dex.service.MemberService;
import com.api.dex.utils.JsonParser;
import com.api.dex.utils.JwtTokenProvider;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    @Autowired
    private JsonParser jsonParser;

    // 회원가입
    @PostMapping("/sign")
    public ResponseEntity<String> sign(@RequestBody MemberDto memberDto) {
        Gson gson = new Gson();
        JsonObject items = new JsonObject();
        JsonObject data = new JsonObject();

        logger.info("controller sign:::" + memberDto.getAccount());

        Member member = memberService.insertMember(memberDto);

        if(member != null){
            data.addProperty("account", member.getAccount());
            data.addProperty("name", member.getName());
            items.add("items", data);
            items.addProperty("message", "sign for success!");
        }else{
            items.addProperty("message", "존재 하는 아이디 입니다.");
        }

        return new ResponseEntity<>(gson.toJson(items), HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map<String, String> user) {
        Gson gson = new Gson();
        HttpHeaders httpHeaders = new HttpHeaders();
        JsonObject items = new JsonObject();
        JsonObject data = new JsonObject();
        Member member = memberRepository.findByAccount(user.get("account"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        logger.info("controller login:::" + member.getAccount());
        String accessToken = jwtTokenProvider.createToken(member.getAccount(), member.getMemberRole(), accessTokenValidTime);
        String refreshToken = jwtTokenProvider.createToken(member.getAccount(), member.getMemberRole(), refreshTokenValidTime);

        httpHeaders.add("accessToken", accessToken);
        httpHeaders.add("refreshToken", refreshToken);

        data.addProperty("account", member.getAccount());
        data.addProperty("name", member.getName());
        items.add("items", data);

        return new ResponseEntity(gson.toJson(items), httpHeaders, HttpStatus.OK);
    }


    @PostMapping("/authority")
    public ResponseEntity<JsonObject> isValidateToken(@RequestBody String account){

        return null;
    }

    @GetMapping("/info")
    public ResponseEntity info(Authentication authentication){
        Gson gson = new Gson();
        JsonObject items = new JsonObject();
        JsonObject data = new JsonObject();

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        String account = securityUser.getMember().getAccount();
        String name = securityUser.getUsername();
        String role = securityUser.getMember().getMemberRole().getRoleName().name();

        data.addProperty("account", account);
        data.addProperty("name", name);
        data.addProperty("role", role);
        items.add("items", data);

        logger.info("controller info:::" + data.get("name"));
        logger.info("controller info:::" + items.get("items"));

        return new ResponseEntity<>(gson.toJson(items), HttpStatus.OK);
    }
}
