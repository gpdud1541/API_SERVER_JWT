package com.api.dex.service;

import com.api.dex.domain.Member;
import com.api.dex.domain.MemberRepository;
import com.api.dex.domain.MemberRole;
import com.api.dex.dto.MemberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        return memberRepository.save(member);
    }

    public Member insertMember(MemberDto memberDto){
        logger.info("insert member:::" + memberDto.getAccount());
        if(memberRepository.findByAccount(memberDto.getAccount()).get() == null){
            if(memberDto.getMemberRole() == null) memberDto.setMemberRole(new MemberRole(MemberRole.RoleType.ROLE_USER));
            return save(memberDto);
        }else{
            return null;
        }
    }

    public Member getMember(MemberDto memberDto){

        return null;
    }

    public Member updateMember(MemberDto memberDto){

        return null;
    }


}
