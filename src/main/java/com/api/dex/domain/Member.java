package com.api.dex.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="member")
public class Member extends BaseEntity implements Serializable  {

    private static final long serialVersionUID = -6430942442101448953L;

    private MemberRole memberRole;

    @Column(unique = true, length = 100)
    private String account;

    @Column(length = 500)
    private String password;

    @Column(length = 100)
    private String name;

    @Column(length = 1000)
    private String token;

    @Builder
    public Member(MemberRole memberRole, String account, String password, String name, String token) {
        this.memberRole = memberRole;
        this.account = account;
        this.password = password;
        this.name = name;
        this.token = token;

    }

}
