package com.api.dex.utils;

import com.api.dex.domain.Member;
import com.api.dex.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonParser {

    public MemberDto memberDtoParser(Member member){
        MemberDto memberDto = new MemberDto();
        memberDto.setAccount(member.getAccount());
        memberDto.setName(member.getName());
        memberDto.setToken(member.getToken());
        memberDto.setMemberRole(member.getMemberRole());

        return memberDto;
    }
}
