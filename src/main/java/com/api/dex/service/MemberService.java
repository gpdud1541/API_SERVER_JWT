package com.api.dex.service;

import com.api.dex.domain.Member;
import com.api.dex.domain.MemberRepository;
import com.api.dex.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    public Member save(MemberDto memberDto){
        Member member = Member.builder()
                .account(memberDto.getAccount())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .name(memberDto.getName())
                .token(memberDto.getToken())
                .memberRole(memberDto.getMemberRole())
                .build();
        return member;
    }

    public Member insertMember(MemberDto memberDto){
        memberRepository.findByAccount(memberDto.getAccount())
                .orElseThrow(() -> new IllegalArgumentException("이미 가입 되어 있는 계정입니다."));

        return save(memberDto);
    }

    public Member getMember(MemberDto memberDto){

        return null;
    }

    public Member updateMember(MemberDto memberDto){

        return null;
    }


}
